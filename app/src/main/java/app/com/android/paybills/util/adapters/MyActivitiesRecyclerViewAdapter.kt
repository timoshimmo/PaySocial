package app.com.android.paybills.util.adapters

import android.content.Intent
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import app.com.android.paybills.R
import app.com.android.paybills.home.activity.TransactionDetailsActivity
import app.com.android.paybills.home.fragments.FragmentActivities.OnListFragmentInteractionListener
import app.com.android.paybills.util.models.HistoryItem
import kotlinx.android.synthetic.main.fragment_activities.view.*


class MyActivitiesRecyclerViewAdapter(
        private val mValues: List<HistoryItem>,
        private val mListener: OnListFragmentInteractionListener?,
        private val mActivity: FragmentActivity)
    : RecyclerView.Adapter<MyActivitiesRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as HistoryItem
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_activities, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitleView.text = item.content
        holder.mCostView.text = item.cost
        holder.mServiceCompany.text = item.serviceCompany
        holder.mDateView.text = item.createdAt
        holder.mTranView.text = item.transactionId

        if(item.status == "0" || item.status == "1") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.mProcessView.setTextColor(mActivity.resources.getColor(R.color.colorOrange, null))
            }
            else {
                holder.mProcessView.setTextColor(mActivity.resources.getColor(R.color.colorOrange))
            }
            holder.mProcessView.text = "In Process"
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.mProcessView.setTextColor(mActivity.resources.getColor(R.color.colorGoGreen, null))
            }
            else {
                holder.mProcessView.setTextColor(mActivity.resources.getColor(R.color.colorGoGreen))
            }
            holder.mProcessView.text = "Complete"
        }

        holder.mBtnRowHistory.setOnClickListener {

            val intent = Intent(mActivity, TransactionDetailsActivity::class.java)
            intent.putExtra("SELECTED_SERVICE_TYPE", it.txtHistoryTitle.text)
            intent.putExtra("SELECTED_COST_VALUE", it.txtCostHistory.text)
            intent.putExtra("SELECTED_CREATED_DATE", mValues[holder.adapterPosition].createdAt)
            intent.putExtra("SELECTED_TRANSAC_ID", it.txtTransactionId.text)
            mActivity.startActivity(intent)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.txtHistoryTitle
        val mCostView: TextView = mView.txtCostHistory
        val mServiceCompany: TextView = mView.txtServiceCompany
        val mProcessView: TextView = mView.txtProcessStatus
        val mTranView: TextView = mView.txtTransactionId
        val mDateView: TextView = mView.txtFullDate
        val mBtnRowHistory: LinearLayout = mView.btnRowHistory
       // val mhistoryBody: LinearLayout = mView.historyBody
    }
}
