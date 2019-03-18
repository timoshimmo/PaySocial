package app.com.android.paybills.util.adapters

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import app.com.android.paybills.R
import app.com.android.paybills.home.activity.TransactionDetailsActivity
import app.com.android.paybills.home.fragments.FragmentActivities
import app.com.android.paybills.util.models.HistoryItem
import app.com.android.paybills.util.models.TrackTransactionItem
import kotlinx.android.synthetic.main.row_track_transaction_layout.view.*

class TrackTransactionAdapter(private val mValues: List<TrackTransactionItem>,
                              private val mActivity: FragmentActivity) : RecyclerView.Adapter<TrackTransactionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_track_transaction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTransCode.text = item.transactionCode
        holder.mTitleView.text = item.title
        holder.mCostView.text = item.cost
        holder.mServiceCompany.text = item.serviceCompany
        holder.mDateView.text = item.createdAt
        holder.mStatus.text = item.status

        holder.mBtnRowHistory.setOnClickListener {

            val intent = Intent(mActivity, TransactionDetailsActivity::class.java)
            intent.putExtra("SELECTED_SERVICE_TYPE", it.txtTrackTransTitle.text)
            intent.putExtra("SELECTED_COST_VALUE", it.txtTrakCostValue.text)
            intent.putExtra("SELECTED_CREATED_DATE", mValues[holder.adapterPosition].createdAt)
            intent.putExtra("SELECTED_TRANSAC_ID", it.txtTrakTransactionId.text)
            mActivity.startActivity(intent)
        }
    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTransCode: TextView = mView.txtTrakTransactionId
        val mTitleView: TextView = mView.txtTrackTransTitle
        val mCostView: TextView = mView.txtTrakCostValue
        val mServiceCompany: TextView = mView.txtTrakServiceCompany
        val mDateView: TextView = mView.txtTrakFullDate
        val mStatus:TextView = mView.txtTrakTransactionStatus
        val mBtnRowHistory: LinearLayout = mView.btnTrakTransRow
    }
}