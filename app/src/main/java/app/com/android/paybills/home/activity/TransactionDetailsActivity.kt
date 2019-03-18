package app.com.android.paybills.home.activity

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import app.com.android.paybills.R
import app.com.android.paybills.util.services.RetrofitClient
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailsActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        val txtTDServiceType: TextView = findViewById(R.id.txtServiceType)
        val txtTDCostValue: TextView = findViewById(R.id.txtCostValue)
        val txtTDDateCreated: TextView = findViewById(R.id.txtDateCreated)

        val txtTDTransactionCode: TextView = findViewById(R.id.txtTrTransactionCodeValue)

        val txtTDServiceProviderName: TextView = findViewById(R.id.txtTransacDetailsServiceProviderName)
        val txtTDServicesIDNum: TextView = findViewById(R.id.txtTransacDetailsServicesIDNum)

        val txtTDBankName: TextView = findViewById(R.id.txtTransacDetailsBankName)
        val txtTDDAcctNo: TextView = findViewById(R.id.txtTransacDetailsAcctNo)
        val txtTDStatus: TextView = findViewById(R.id.txtTransacDetailsStatus)

        progressBar = findViewById(R.id.progressBarTransacDetails)

        val imgServiceTypeIcon: ImageView = findViewById(R.id.imgTransacDetailsServiceIcon)
        val imgTransactionIcon: ImageView = findViewById(R.id.imgTransacDetailsTransactionIcon)
        val imgTransactionCodeIcon: ImageView = findViewById(R.id.imgTrTransactionCodeIcon)

        val btnTrackTransaction: Button = findViewById(R.id.btnTrackTransaction)

        val bundle = intent.extras
        if(bundle!=null)
        {
            val strSelectedServiceType = bundle.getString("SELECTED_SERVICE_TYPE")
            val strSelectedCostValue = bundle.getString("SELECTED_COST_VALUE")
            val strSelectedCreatedDate = bundle.getString("SELECTED_CREATED_DATE")
            val strSelectedTransacId = bundle.getString("SELECTED_TRANSAC_ID")

            txtTDServiceType.text = strSelectedServiceType
            txtTDCostValue.text = strSelectedCostValue
            txtTDDateCreated.text = strSelectedCreatedDate

            RetrofitClient.getClient().retrieveTransactions(Integer.valueOf(strSelectedTransacId)).enqueue(object: Callback<JsonObject> {

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    toast("Failed to retrieve data: ${t.message}")
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val arrTransaction: JsonArray = response.body()!!.getAsJsonArray("transaction")

                    val objList = arrTransaction[0].asJsonObject
                    val mTransCode = objList.get("transactionCode").asString
                    val mServiceProvider = objList.get("serviceProvider").asString
                    val mServiceIdNo = objList.get("serviceIdNo").asString

                    val mBankName = objList.get("bankName").asString
                    val mTransacStatus = objList.get("transactionStatus").asString

                    when (strSelectedServiceType) {
                        "Pay Tv" -> imgServiceTypeIcon.setImageDrawable(ContextCompat.getDrawable(this@TransactionDetailsActivity,
                                R.drawable.ic_satellite_dish_dark))
                        "Internet" -> imgServiceTypeIcon.setImageDrawable(ContextCompat.getDrawable(this@TransactionDetailsActivity,
                                R.drawable.ic_router_dark))
                        else -> imgServiceTypeIcon.setImageDrawable(ContextCompat.getDrawable(this@TransactionDetailsActivity,
                                R.drawable.ic_light_bulb_dark))
                    }


                    imgTransactionCodeIcon.setImageDrawable(ContextCompat.getDrawable(this@TransactionDetailsActivity,
                            R.drawable.ic_supermarket_barcode))

                    imgTransactionIcon.setImageDrawable(ContextCompat.getDrawable(this@TransactionDetailsActivity,
                            R.drawable.ic_sent_mail))

                    txtTDTransactionCode.text = mTransCode
                    txtTDServiceProviderName.text = mServiceProvider
                    txtTDServicesIDNum.text = mServiceIdNo

                    txtTDBankName.text = mBankName
                    txtTDDAcctNo.text = "01445544332"
                    if(mTransacStatus == "0" || mTransacStatus == "1") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            txtTDStatus.setTextColor(resources.getColor(R.color.colorOrange, null))
                        }
                        else {
                            txtTDStatus.setTextColor(resources.getColor(R.color.colorOrange))
                        }
                        txtTDStatus.text = "In Process"
                        btnTrackTransaction.visibility = View.VISIBLE
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            txtTDStatus.setTextColor(resources.getColor(R.color.colorGoGreen, null))
                        } else {
                            txtTDStatus.setTextColor(resources.getColor(R.color.colorGoGreen))
                        }
                        txtTDStatus.text = "Complete"

                    }

                    btnTrackTransaction.setOnClickListener {
                        val intent = Intent(this@TransactionDetailsActivity, TransactionsActivity::class.java)
                        intent.putExtra("TRANSAC_STATUS", mTransacStatus)
                        startActivity(intent)
                    }
                }
            })
        }

    }
}
