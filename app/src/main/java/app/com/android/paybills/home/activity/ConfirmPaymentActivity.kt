package app.com.android.paybills.home.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import app.com.android.paybills.R
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.providers.PreferencesHelper.username
import app.com.android.paybills.util.services.RetrofitClient
import app.com.android.paybills.util.volley.ApiController
import app.com.android.paybills.util.volley.EndPoint
import app.com.android.paybills.util.volley.ServiceVolley
import com.google.gson.JsonObject
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ConfirmPaymentActivity : AppCompatActivity() {

    val CUSTOM_PREF_NAME = "UserName"
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_process)

        val edtUserid: EditText = findViewById(R.id.txtUserIdResult)
        val btnConfirm : Button = findViewById(R.id.btnConfirmPayment)
        val txtTile : TextView = findViewById(R.id.txtConfirmPaymentTitle)

        progressBar = findViewById(R.id.progressBarConfirmUserId)

        val bundle=intent.extras
        if(bundle!=null) {

            val selectedService : String = bundle.getString("SELECTED_SERVICE")
            val fName : String = bundle.getString("FULLNAME")
            val email : String = bundle.getString("EMAILADD")
            val pNum : String = bundle.getString("PHONENO")
            val sType : String = bundle.getString("SERVICETYPE")
            val sProvider : String = bundle.getString("SERVICES")
            val sNo : String = bundle.getString("SERVICENO")
            val cost : String = bundle.getString("AMTPAID")
            val bName : String = bundle.getString("BANKNAME")
            val comm : String = bundle.getString("COMMUNICA")

            txtTile.text = getString(R.string.make_payment_title, selectedService)

            val txtFullNameResult : TextView = findViewById(R.id.txtFullnameResult)
            val txtEmailResult : TextView = findViewById(R.id.txtEmailResult)
            val txtphoneNoResult : TextView = findViewById(R.id.txtphoneNoResult)
            val txtServiceResult : TextView = findViewById(R.id.txtServiceResult)
            val txtServiceNoResult : TextView = findViewById(R.id.txtServiceNoResult)
            val txtamtPayResult : TextView = findViewById(R.id.txtamtPayResult)
            val txtBankResult : TextView = findViewById(R.id.txtBankResult)
            val txtCommChanelResult : TextView = findViewById(R.id.txtCommChanelResult)

            txtFullNameResult.text = fName
            txtEmailResult.text = email
            txtphoneNoResult.text = pNum
            txtServiceResult.text = sProvider
            txtServiceNoResult.text = sNo
            txtamtPayResult.text = cost
            txtBankResult.text = bName
            txtCommChanelResult.text = comm

            btnConfirm.setOnClickListener {
                val userid: String = edtUserid.text.toString()
                progressBar!!.visibility = View.VISIBLE
                retrieveUserId(applicationContext, selectedService, fName, email, pNum,
                        sType, sProvider, sNo, comm, bName, cost, userid)
            }
        }
    }

    private fun retrieveUserId(ctx : Context, selectedService: String, fName: String, email: String, pNum: String,
                               sType: String, sProvider: String, sNo: String, comm: String, bName: String,
                               cost: String, uid: String)  {

        val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_PREF_NAME)
        val username = prefs.username

        RetrofitClient.getClient().retrieveUserId(username).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                toast("Failed to retrieve data: ${t.message}")
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if(response.body()!!.get("status").asInt == 1) {

                    progressBar!!.visibility = View.GONE

                    val arrayAll = response.body()!!.getAsJsonArray("userID")

                    val userIdObj = arrayAll.get(0).asJsonObject

                    val userIdValue = userIdObj.get("userid").asString

                    if(uid != userIdValue) {
                        toast("Incorrect User ID")
                    }

                    else {

                        val intent = Intent(this@ConfirmPaymentActivity, CompletePaymentActivity::class.java)
                        intent.putExtra("SELECTED_SERVICE", selectedService)
                        intent.putExtra("FULLNAME", fName)
                        intent.putExtra("EMAILADD", email)
                        intent.putExtra("CONTACT_NUMBER", pNum)
                        intent.putExtra("SERVICETYPE", sType)
                        intent.putExtra("SERVICE_PROVIDER", sProvider)
                        intent.putExtra("SERVICENO", sNo)
                        intent.putExtra("COMMUNICA", comm)
                        intent.putExtra("BANK_NAME", bName)
                        intent.putExtra("COST_AMT", cost)
                        intent.putExtra("USERID", uid)

                        startActivity(intent)
                        finish()
                    }


                }
                else {

                }
                toast(response.body().toString())
            }

        })

    /*    val service = ServiceVolley()
        val apiController = ApiController(service)

        val path = EndPoint.URL_GET_USERID
        val params = JSONObject()

        val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_PREF_NAME)
        val username = prefs.username

        params.put("username", "")

        apiController.post(path, params) {response ->

            progressBar!!.visibility = View.GONE
            val obj = JSONObject(response.toString())

            if(obj.getInt("status") == 1) {
                val resObj = obj.getJSONObject("result")
                val checkedId = resObj.getString("userid")

                toast("Retrieved Id $checkedId")

                if(uid != checkedId) {
                    toast("Incorrect User ID")
                }

                else {

                    val intent = Intent(this, CompletePaymentActivity::class.java)
                    intent.putExtra("SELECTED_SERVICE", selectedService)
                    intent.putExtra("FULLNAME", fName)
                    intent.putExtra("EMAILADD", email)
                    intent.putExtra("CONTACT_NUMBER", pNum)
                    intent.putExtra("SERVICETYPE", sType)
                    intent.putExtra("SERVICE_PROVIDER", sProvider)
                    intent.putExtra("SERVICENO", sNo)
                    intent.putExtra("COMMUNICA", comm)
                    intent.putExtra("BANK_NAME", bName)
                    intent.putExtra("COST_AMT", cost)
                    intent.putExtra("USERID", uid)

                    startActivity(intent)
                    finish()
                }
            }
            else {
                toast(obj.getString("msg"))
            }
        } */

    }

}
