package app.com.android.paybills.signup.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import app.com.android.paybills.R
import app.com.android.paybills.home.activity.MainActivity
import app.com.android.paybills.util.services.RetrofitClient
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.providers.PreferencesHelper.userdata
import com.google.gson.JsonObject
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentSuccessful.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentSuccessful.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentSuccessful : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var txtUserid: TextView? = null
    var progressBar: ProgressBar? = null
    val CUSTOM_USERDATA = "UserData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_successful, container, false)

        val rndid = (1000..9999).shuffled().first()

        txtUserid = view.findViewById(R.id.txtgeneratedId)
        progressBar = view.findViewById(R.id.progressBarComplete)
        val btnCompleteSignUp : Button = view.findViewById(R.id.btnCompleteSignup)

        txtUserid!!.text = rndid.toString()

        btnCompleteSignUp.setOnClickListener {
            progressBar!!.visibility = View.VISIBLE
            createUser(this.requireContext(), rndid)
        }

        listener?.onSuccessfulFragmentInteraction("4")

        return view
    }

    private fun createUser(ctx: Context, rndValue: Int) {

    /*    val service = ServiceVolley()
        val apiController = ApiController(service)

        val path = EndPoint.URL_REGISTER_USER
        val params = JSONObject() */

        val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_USERDATA)
        val storedValues = prefs.userdata

        val arrVal: List<String> = storedValues.split(",")

        val mFname: String = arrVal[0]
        val mLname: String = arrVal[1]
        val mEmail: String = arrVal[2]
        val mMobile: String = arrVal[3]
        val mUsername: String = arrVal[4]
        val mPassword: String = arrVal[5]

        RetrofitClient.getClient().registerUser(mFname, mLname, mEmail,
                mMobile, mUsername, mPassword, rndValue).enqueue(object: Callback<JsonObject> {

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.body()!!.get("status").asInt == 1) {
                    progressBar!!.visibility = View.GONE

                    val success = response.body()!!.get("msg").asString
                    toast(success)
                    val intent = Intent(activity, MainActivity::class.java)
                    activity!!.startActivity(intent)
                    activity!!.finish()
                }
                else {
                    val unsuccessful = response.body()!!.get("msg").asString
                    toast(unsuccessful)
                }
            }


        })

      /*  params.put("firstName", mFname)
        params.put("lastName", mLname)
        params.put("emailAddress", mEmail)
        params.put("mobileNo", mMobile)
        params.put("username", mUsername)
        params.put("password", mPassword)
        params.put("userid", rndValue)

        apiController.post(path, params) {response ->

            val obj = JSONObject(response.toString())
            toast(response.toString())
            progressBar!!.visibility = View.GONE

            if(obj.getInt("status") == 1) {

              //  val success = obj.getString("msg")
              //  toast(success)
                val intent = Intent(activity, MainActivity::class.java)
                activity!!.startActivity(intent)
                activity!!.finish()
            }
            else {
                val unsuccessful = obj.getString("msg")
                toast(unsuccessful)
            }
        } */
    }

    fun onButtonPressed(data: String) {
        listener?.onSuccessfulFragmentInteraction(data)
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
        fun onSuccessfulFragmentInteraction(pageNo: String)
    }
}
