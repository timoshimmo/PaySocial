package app.com.android.paybills.home.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import app.com.android.paybills.R
import app.com.android.paybills.home.fragments.FragmentActivities
import app.com.android.paybills.home.fragments.ServicesFragment
import app.com.android.paybills.login.LoginActivity
import app.com.android.paybills.util.models.HistoryItem
import app.com.android.paybills.util.models.ServicesModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener,
ServicesFragment.OnListFragmentInteractionListener, FragmentActivities.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: HistoryItem?) {

    }

    override fun onListFragmentInteraction(item: ServicesModel.ServicesItem?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val homeTab: TabLayout = findViewById(R.id.homeTab)

        val btnLogout: Button = findViewById(R.id.btnLogout)

        val tab: TabLayout.Tab? = homeTab.getTabAt(0)
        tab!!.select()
        replaceFragment(ServicesFragment.newInstance(1))
        homeTab.addOnTabSelectedListener(this)

        btnLogout.setOnClickListener {

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {

        setCurrentTabFragment(tab!!.position)
    }

    private fun replaceFragment(frag: Fragment) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.homeFragContainer, frag)
                .commit()
    }

    private fun setCurrentTabFragment(tabPosition: Int) {
        when (tabPosition) {

            0 -> {
                replaceFragment(ServicesFragment.newInstance(1))
            }

            1 -> {
                replaceFragment(FragmentActivities.newInstance(1))

            }

        }
    }

}
