package app.com.android.paybills.launch.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.com.android.paybills.R

private const val ARG_NAME = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLaunchFourth.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentLaunchFourth : Fragment() {
    // TODO: Rename and change types of parameters
    private var mName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mName = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launch_fourth, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment FragmentLaunchFourth.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                FragmentLaunchFourth().apply {
                    arguments = Bundle().apply {
                        putString(ARG_NAME, param1)
                    }
                }
    }
}
