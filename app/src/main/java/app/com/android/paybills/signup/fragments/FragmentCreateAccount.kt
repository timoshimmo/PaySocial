package app.com.android.paybills.signup.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import app.com.android.paybills.home.activity.MainActivity
import app.com.android.paybills.R
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.providers.PreferencesHelper.customPreference
import app.com.android.paybills.util.providers.PreferencesHelper.userdata
import java.lang.StringBuilder
import java.security.MessageDigest


class FragmentCreateAccount : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    val CUSTOM_USERDATA = "UserData"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account, container, false)

        val btnFinish: Button = view.findViewById(R.id.btnFinish)
        val txtUsername: EditText = view.findViewById(R.id.txtUsername)
        val txtPassword: EditText = view.findViewById(R.id.txtPassword)

        btnFinish.setOnClickListener {
            attemptCreateAccount(txtUsername, txtPassword, this.requireContext())
        }

        listener?.onFragmentCreateAccountInteraction("3")

        return view
    }

    private fun replaceFragment(frag: Fragment) {

        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .commit()
    }

    private fun attemptCreateAccount(mUsername: EditText, mPassword: EditText, ctx: Context) {

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

            val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_USERDATA)
            val storedValues = prefs.userdata

            val strValues = StringBuilder()

            strValues.append(storedValues)
            strValues.append(",")
            strValues.append(username)
            strValues.append(",")
            strValues.append(hashString("SHA-1", password))

            prefs.userdata = strValues.toString()
            replaceFragment(FragmentSuccessful())

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


    fun onButtonPressed(data: String) {
        listener?.onFragmentCreateAccountInteraction(data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentCreateAccountInteraction(pageNo: String)
    }

}
