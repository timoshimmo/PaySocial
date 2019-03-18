package app.com.android.paybills.launch.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import app.com.android.paybills.R
import app.com.android.paybills.util.adapters.LaunchPagerAdapter
import app.com.android.paybills.login.LoginActivity
import app.com.android.paybills.signup.activity.SignUpActivity
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var cp: CirclePageIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        viewPager = findViewById(R.id.launch_pager)

        val pagerAdapter = LaunchPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        cp = findViewById(R.id.indicator)
        cp.setViewPager(viewPager)

        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnGoToCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}
