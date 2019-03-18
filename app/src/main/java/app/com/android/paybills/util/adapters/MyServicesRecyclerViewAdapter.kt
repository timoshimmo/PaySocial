package app.com.android.paybills.util.adapters

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.com.android.paybills.home.activity.MakePaymentActivity
import app.com.android.paybills.R
import app.com.android.paybills.home.fragments.ServicesFragment.OnListFragmentInteractionListener
import app.com.android.paybills.util.models.ServicesModel.ServicesItem
import kotlinx.android.synthetic.main.fragment_services.view.*

/**
 * [RecyclerView.Adapter] that can display a [ServicesItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */

class MyServicesRecyclerViewAdapter(
        private val mValues: List<ServicesItem>,
        private val mListener: OnListFragmentInteractionListener?,
        private val mContext: FragmentActivity)
    : RecyclerView.Adapter<MyServicesRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ServicesItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_services, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitleView.text = item.title
        holder.mSubTitleView.text = item.subtitle
        holder.mImageIcon.setImageDrawable(ContextCompat.getDrawable(mContext, item.imgName))
        holder.mBtnRow.setOnClickListener {
            when(position) {
                0 -> {
                    val intent = Intent(mContext, MakePaymentActivity::class.java)
                    intent.putExtra("SERVICE_PAY", "Pay Tv")
                    mContext.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(mContext, MakePaymentActivity::class.java)
                    intent.putExtra("SERVICE_PAY", "Internet")
                    mContext.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(mContext, MakePaymentActivity::class.java)
                    intent.putExtra("SERVICE_PAY", "Electricity")
                    mContext.startActivity(intent)
                }
            }
        }
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.item_Title
        val mSubTitleView: TextView = mView.item_subtitle
        val mImageIcon: ImageView = mView.imgIcon
        val mBtnRow: CardView = mView.rowServices

    }
}
