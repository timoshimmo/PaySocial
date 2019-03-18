package app.com.android.paybills.signup.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import app.com.android.paybills.R
import app.com.android.paybills.signup.fragments.FragmentCreateAccount
import app.com.android.paybills.signup.fragments.FragmentEmailMobile
import app.com.android.paybills.signup.fragments.FragmentFullname
import app.com.android.paybills.signup.fragments.FragmentSuccessful
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), FragmentFullname.OnFragmentInteractionListener, FragmentEmailMobile.OnFragmentInteractionListener,
FragmentCreateAccount.OnFragmentInteractionListener, FragmentSuccessful.OnFragmentInteractionListener{

    private lateinit var txtPageNo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        replaceFragment(FragmentFullname())

        txtPageNo = findViewById(R.id.txtPageNumber)

        btnSignUpBack.setOnClickListener {
           onBackPressed()
        }

    }

    private fun replaceFragment(frag: Fragment) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .commit()
    }

    override fun onFragmentFullnameInteraction(pageNo: String) {
        txtPageNo.text = getString(R.string.signup_page_number_value, pageNo)
    }

    override fun onFragmentEmailMobileInteraction(pageNo: String) {
        txtPageNo.text = getString(R.string.signup_page_number_value, pageNo)
    }

    override fun onFragmentCreateAccountInteraction(pageNo: String) {
        txtPageNo.text = getString(R.string.signup_page_number_value, pageNo)
    }

    override fun onSuccessfulFragmentInteraction(pageNo: String) {
        txtPageNo.text = getString(R.string.signup_page_number_value, pageNo)
    }

}
