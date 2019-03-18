package app.com.android.paybills.home.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import app.com.android.paybills.R
import app.com.android.paybills.util.services.RetrofitClient
import app.com.android.paybills.util.providers.PreferencesHelper
import app.com.android.paybills.util.providers.PreferencesHelper.username
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_complete_payment.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CompletePaymentActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    val CUSTOM_PREF_NAME = "UserName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_payment)

        progressBar = findViewById(R.id.progressBarCompletePayment)

        val bundle=intent.extras
        if(bundle!=null) {

            val selectedService : String = bundle.getString("SELECTED_SERVICE")
            val fName : String = bundle.getString("FULLNAME")
            val email : String = bundle.getString("EMAILADD")
            val contactNo : String = bundle.getString("CONTACT_NUMBER")
            val sType : String = bundle.getString("SERVICETYPE")
            val serviceProvider : String = bundle.getString("SERVICE_PROVIDER")
            val sNo : String = bundle.getString("SERVICENO")
            val bankName : String = bundle.getString("BANK_NAME")
            val costAmt : String = bundle.getString("COST_AMT")
            val comm : String = bundle.getString("COMMUNICA")
            val uid : String = bundle.getString("USERID")

            val rndTransactionCode = (100000..999999).shuffled().first()

            txtCompletePaymentTitle.text = getString(R.string.make_payment_title, selectedService)
            txtTransactionCode.text = rndTransactionCode.toString()
            txtTransferValue.text = getString(R.string.transfer_amount_details, costAmt)
            txtProviderName.text = serviceProvider
            txtFullBankName.text = bankName
            txtCommunicationInfo.text = getString(R.string.str_mobile_info, contactNo)


            btnCompletedTransactions.setOnClickListener {

                progressBar!!.visibility = View.VISIBLE
                        createTransaction(applicationContext, rndTransactionCode, fName, email, contactNo, sType, serviceProvider, sNo, costAmt.toDouble(),
                                bankName, comm, uid.toInt())

            }

        }
    }

    private fun createTransaction(ctx: Context, tCode: Int, fullName: String, email: String, pNo: String, sType: String,
                                  sProvider: String, sNo:String, cost: Double, bank: String, comm: String,
                                  userId: Int) {

        val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_PREF_NAME)
        val username = prefs.username

        val currentDate: String
        currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val curDate = LocalDateTime.now()
            curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            sdf.format(Date())
        }


        RetrofitClient.getClient().makeTransaction(tCode, fullName, email, pNo, sType, sProvider, sNo, cost,
                bank, comm, userId, username, currentDate, 0).enqueue(object: Callback<JsonObject> {
            
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.body()!!.get("status").asInt == 1) {
                    progressBar!!.visibility = View.GONE

                    val success = response.body()!!.get("msg").asString
                    toast(success)

                    val intent = Intent(this@CompletePaymentActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val unsuccessful = response.body()!!.get("msg").asString
                    toast(unsuccessful)
                }
            }

        })

     /*   val service = ServiceVolley()
        val apiController = ApiController(service)

        val path = EndPoint.URL_ADD_TRANSACTION
        val params = JSONObject()

        val prefs = PreferencesHelper.customPreference(ctx, CUSTOM_PREF_NAME)
        val username = prefs.username

        val currentDate: String
        currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val curDate = LocalDateTime.now()
            curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            sdf.format(Date())
        }

          params.put("transcode", tCode)
        params.put("fullname", fullName)
        params.put("emailAddress", email)
        params.put("mobileNo", pNo)
        params.put("serviceType", sType)
        params.put("serviceProvider", sProvider)
        params.put("serviceIdNo", sNo)
        params.put("cost", cost)
        params.put("bankName", bank)
        params.put("commChannel", comm)
        params.put("userid", rndValue)
        params.put("username", username)
        params.put("createdAt", currentDate)

        apiController.post(path, params) {response ->

            val obj = JSONObject(response.toString())

            if(obj.getInt("status") == 1) {

                progressBar!!.visibility = View.GONE
                val success = obj.getString("msg")
                toast(success)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val unsuccessful = obj.getString("msg")
                toast(unsuccessful)
            }
        }*/
    }
}
