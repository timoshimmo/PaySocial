package app.com.android.paybills.home.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import app.com.android.paybills.R
import app.com.android.paybills.util.adapters.MyActivitiesRecyclerViewAdapter
import app.com.android.paybills.util.models.HistoryItem
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.volley.EndPoint
import org.json.JSONObject
import app.com.android.paybills.util.providers.PreferencesHelper.username
import app.com.android.paybills.util.services.RetrofitClient
import app.com.android.paybills.util.volley.ApiController
import app.com.android.paybills.util.volley.ServiceVolley
import com.google.gson.JsonObject
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class FragmentActivities : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private var historyList: MutableList<HistoryItem> = mutableListOf()
    val CUSTOM_PREF_NAME = "UserName"
    var progressBar: ProgressBar? = null
    lateinit var rvHistory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activities_list, container, false)

        rvHistory = view.findViewById(R.id.listHistory)
        progressBar = view.findViewById(R.id.progressBarHistory)

        progressBar!!.visibility = View.VISIBLE

        val prefs = PreferencesHelper.customPreference(this.requireContext(), CUSTOM_PREF_NAME)
        val usernameValue = prefs.username

        loadHistory(this.requireContext(), this.requireActivity(), usernameValue)

        return view
    }

    private fun loadHistory(ctx: Context, acty: FragmentActivity, uname: String) {

        RetrofitClient.getClient().retrieveHistory(uname).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.body()!!.get("status").asInt == 1) {

                    progressBar!!.visibility = View.GONE

                    val arrayAll = response.body()!!.getAsJsonArray("result")

                    for(i in 0 until arrayAll.size()) {

                        val allData = arrayAll.get(i).asJsonObject

                        val mServiceType: String = allData.get("serviceType").asString
                        val mServiceProvider: String = allData.get("serviceProvider").asString
                        val mTransactionId: String = allData.get("transactionCode").asString
                        val mCost: String = allData.get("cost").asString
                        val mTransactionStatus: String = allData.get("transactionStatus").asString

                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                        val convertedDate: Date = formatter.parse(allData.get("created").asString)

                        val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
                        val createdDay = sdf.format(convertedDate)

                        val historyData = HistoryItem(mServiceType, mCost, createdDay, mTransactionId, mServiceProvider, mTransactionStatus)

                        historyList.add(historyData)

                    }

                    with(rvHistory) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = MyActivitiesRecyclerViewAdapter(historyList, listener, acty)
                    }


                }
                else {
                    val unsuccessful = response.body()!!.get("msg").asString
                    toast(unsuccessful)
                }
            }

        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
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
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: HistoryItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                FragmentActivities().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
