package app.com.android.paybills.signup.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import app.com.android.paybills.R
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.providers.PreferencesHelper.userdata
import java.lang.StringBuilder

class FragmentEmailMobile : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    val CUSTOM_USERDATA = "UserData"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_email_mobile, container, false)
        val btnEmailMobileNext: Button = view.findViewById(R.id.btnEmailMobileNext)
        val txtEmail: EditText = view.findViewById(R.id.txtEmailAddress)
        val txtMobile: EditText = view.findViewById(R.id.txtMobileNumber)

        btnEmailMobileNext.setOnClickListener {
            attemptEmailMobile(txtEmail, txtMobile, this.requireContext())
        }

        listener?.onFragmentEmailMobileInteraction("2")

        return view
    }

    private fun replaceFragment(frag: Fragment) {

        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .commit()
    }

    private fun attemptEmailMobile(mEmail: EditText, mMobile: EditText, ctx: Context) {

        // Reset errors.
        mEmail.error = null
        mMobile.error = null

        val email = mEmail.text.toString()
        val mobile = mMobile.text.toString()

        var cancel = false
        lateinit var focusView: View

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(mobile)) run {
            mEmail.error = getString(R.string.field_required_error)
            mMobile.error = getString(R.string.field_required_error)
            focusView = mEmail

            focusView.requestFocus()

        }

        if (!TextUtils.isEmpty(email) && !isEmailValid(email)) {
            mEmail.error = "Must contain @"
            focusView = mEmail
            cancel = true
        }

        if (TextUtils.isEmpty(email)) {
            mEmail.error = getString(R.string.field_required_error)
            focusView = mEmail
            cancel = true
        }
        if (TextUtils.isEmpty(mobile)) {
            mMobile.error =getString(R.string.field_required_error)
            focusView = mMobile
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
            strValues.append(email)
            strValues.append(",")
            strValues.append(mobile)

            prefs.userdata = strValues.toString()
            replaceFragment(FragmentCreateAccount())
        }

    }

    private fun isEmailValid(emailAdd: String): Boolean {
        //TODO: Replace this with your own logic
        return emailAdd.contains("@")
    }


    fun onButtonPressed(data: String) {
        listener?.onFragmentEmailMobileInteraction(data)
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
        fun onFragmentEmailMobileInteraction(pageNo: String)
    }

}
