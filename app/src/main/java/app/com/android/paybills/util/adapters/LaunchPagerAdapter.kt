package app.com.android.paybills.util.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import app.com.android.paybills.launch.fragments.*

class LaunchPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {



    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentLaunchFirst.newInstance("FIRST_FRAG")
            1 -> fragment = FragmentLaunchSecond.newInstance("SECOND_FRAG")
            2 -> fragment = FragmentLaunchThird.newInstance("THIRD_FRAG")
            3 -> fragment = FragmentLaunchFourth.newInstance("FOURTH_FRAG")
            4 -> fragment = FragmentLaunchFifth.newInstance("FIFTH_FRAG")
        }

        return fragment
    }

    override fun getCount(): Int {
        return 5
    }
}