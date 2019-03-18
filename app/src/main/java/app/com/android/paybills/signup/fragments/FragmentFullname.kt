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


class FragmentFullname : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    val CUSTOM_USERDATA = "UserData"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fullname, container, false)
        val btnFullNameNext: Button = view.findViewById(R.id.btnFullNameNext)

        val txtFirstName: EditText = view.findViewById(R.id.txtFirstname)
        val txtLastName: EditText = view.findViewById(R.id.txtLastname)

        btnFullNameNext.setOnClickListener {
            attemptFullname(txtFirstName, txtLastName, this.requireContext())
        }

        listener?.onFragmentFullnameInteraction("1")

        return view
    }

    private fun replaceFragment(frag: Fragment) {

        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .commit()
    }

    private fun attemptFullname(mFirstName: EditText, mLastname: EditText, ctx: Context) {

        // Reset errors.
        mFirstName.error = null
        mLastname.error = null

        val firstName = mFirstName.text.toString()
        val surname = mLastname.text.toString()

        var focusView: View

        if (TextUtils.isEmpty(firstName) && TextUtils.isEmpty(surname)) run {
            mFirstName.error = getString(R.string.field_required_error)
            mLastname.error = getString(R.string.field_required_error)
            focusView = mFirstName

            focusView.requestFocus()
        }

        else {

            if (TextUtils.isEmpty(firstName)) {
                mFirstName.error = getString(R.string.field_required_error)
                focusView = mFirstName

                focusView.requestFocus()
            } else if (TextUtils.isEmpty(surname)) {
                mLastname.error =getString(R.string.field_required_error)
                focusView = mLastname

                focusView.requestFocus()
            } else {

                val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_USERDATA)

                val strValues = StringBuilder()

                strValues.append(firstName)
                strValues.append(",")
                strValues.append(surname)

                prefs.userdata = strValues.toString()
                replaceFragment(FragmentEmailMobile())
            }

        }

    }

    fun onButtonPressed(data: String) {
        listener?.onFragmentFullnameInteraction(data)
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
        fun onFragmentFullnameInteraction(pageNo: String)
    }

   /* companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment FragmentLaunchFirst.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                FragmentFullname().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TITLE, param1)
                    }
                }
    }  */

}
