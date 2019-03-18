package app.com.android.paybills.login

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import app.com.android.paybills.home.activity.MainActivity
import app.com.android.paybills.R
import app.com.android.paybills.util.providers.PreferencesHelper.customPreference
import app.com.android.paybills.util.providers.PreferencesHelper.username
import app.com.android.paybills.util.providers.PreferencesHelper.userid
import app.com.android.paybills.util.services.RetrofitClient
import app.com.android.paybills.util.volley.ApiController
import app.com.android.paybills.util.volley.EndPoint
import app.com.android.paybills.util.volley.ServiceVolley
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.security.MessageDigest


class LoginActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    val CUSTOM_PREF_NAME = "UserName"
    val CUSTOM_PREF_ID = "UserID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsername.clearFocus()
        txtPassword.clearFocus()
        loginBody.requestFocus()

        progressBar = findViewById(R.id.progressBarLogin)

        txtUsername.setOnFocusChangeListener {
            v, hasFocus ->

            if(hasFocus) {
                appbarLogin.setExpanded(false, true)
            }
            else {
                appbarLogin.setExpanded(true, true)
            }
        }

        txtPassword.setOnFocusChangeListener {
            v, hasFocus ->

            if(hasFocus) {
                appbarLogin.setExpanded(false, true)
            }
            else {
                appbarLogin.setExpanded(true, true)
            }
        }

        txtPassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotEmpty()) {
                    if(btnLogin.visibility == View.GONE) {
                        btnLogin.visibility = View.VISIBLE
                    }
                }
            }
        })

        btnBack.setOnClickListener {
            onBackPressed()
        }


        btnLogin.setOnClickListener{
            attemptLogin(txtUsername, txtPassword, applicationContext)
        }
    }

    private fun attemptLogin(mUsername: EditText, mPassword: EditText, ctx: Context) {

        // Reset errors.
        mUsername.error = null
        mPassword.error = null

        val username = mUsername.text.toString()
        val password = mPassword.text.toString()

        var cancel = false
        lateinit var focusView: View

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) run {
            mUsername.error = getString(R.string.field_required_error)
            mPassword.error = getString(R.string.field_required_error)
            focusView = mUsername

            focusView.requestFocus()

        }

        if (TextUtils.isEmpty(username)) {
            mUsername.error = getString(R.string.field_required_error)
            focusView = mUsername
            cancel = true
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.error =getString(R.string.field_required_error)
            focusView = mPassword
            cancel = true
        }

        if (!TextUtils.isEmpty(username) && !isUsernameLengthValid(username)) {
            mUsername.error = "Username is too short"
            focusView = mUsername
            cancel = true
        }

        if (!TextUtils.isEmpty(username) && !isPassLengthValid(username)) {
            mUsername.error = "Password is too short"
            focusView = mUsername
            cancel = true
        }

        if(cancel) {
            focusView.requestFocus()
        }
        else {
            progressBar!!.visibility = View.VISIBLE
            val hashdPass = hashString("SHA-1", password)
            authenticateUser(username, hashdPass, ctx)
        }

    }

    private fun isUsernameLengthValid(uname: String): Boolean {
        //TODO: Replace this with your own logic
        return uname.length > 6
    }

    private fun isPassLengthValid(pword: String): Boolean {
        //TODO: Replace this with your own logic
        return pword.length > 5
    }

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }


    private fun authenticateUser(uname: String, pword: String, ctx :Context) {

        RetrofitClient.getClient().validateUser(uname, pword).enqueue(object: Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                progressBar!!.visibility = View.GONE

                if(response.body()!!.get("status").asInt == 1) {

                    val unamePrefs = customPreference(ctx, CUSTOM_PREF_NAME)
                    unamePrefs.username = uname

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val unsuccessful = response.body()!!.get("msg").asString
                    toast(unsuccessful)
                }
            }

        })
    }
}
