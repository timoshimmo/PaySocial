package app.com.android.paybills.home.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import app.com.android.paybills.R
import kotlinx.android.synthetic.main.activity_make_payment.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class MakePaymentActivity : AppCompatActivity() {

    var listServiceProviders: List<String> = listOf()
    var listBanks: List<String> = listOf("Access bank", "Diamond Bank", "Guaranty Trust Bank", "UBA", "Zenith Bank")
    var listCommunica: List<String> = listOf("Call", "Sms", "Email", "Social Chat")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_payment)

        val fullName:EditText = findViewById(R.id.txtFullName)
        val emailAdd:EditText = findViewById(R.id.txtEmailAddress)
        val phoneNo:EditText = findViewById(R.id.txtPhoneNo)

        val selectService:EditText = findViewById(R.id.txtSelectedService)
        val serviceNo:EditText = findViewById(R.id.txtServiceIdNumber)

        val AmtPay:EditText = findViewById(R.id.txtPaymentAmount)
        val txtBank:EditText = findViewById(R.id.txtSelectedBank)

        val txtCommMethod:EditText = findViewById(R.id.txtCommunicationMethod)

        val btnSelectService: LinearLayout = findViewById(R.id.btnSelectService)
        val btnSelectBank: LinearLayout = findViewById(R.id.btnSelectBank)
        val btnCommunicationMethod: LinearLayout = findViewById(R.id.btnCommunicationMethod)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        val bundle=intent.extras
        var title = ""
        var mServiceSelected = ""

        if(bundle!=null) {

            mServiceSelected = bundle.getString("SERVICE_PAY")
            title = getString(R.string.make_payment_title, mServiceSelected)
            txtMakePaymentTitle.text = title

            when (mServiceSelected) {
                "Pay Tv" -> listServiceProviders = listOf("DSTV", "Kwese", "GoTv", "StarTimes")
                "Internet" -> listServiceProviders = listOf("Spectranet", "Smile", "Tizeti", "MainOne")
                else -> listServiceProviders = listOf("Eko Electric", "Ikeja Disco", "Ajah Disco")
            }


            btnSelectService.setOnClickListener {

                selector("Services", listServiceProviders) { dialogInterface, i ->
                    selectService.setText(listServiceProviders[i])
                }
            }

            btnSelectBank.setOnClickListener {
                selector("Banks", listBanks) { dialogInterface, i ->
                    txtBank.setText(listBanks[i])
                }
            }

            btnCommunicationMethod.setOnClickListener {
                selector("How to communicate?", listCommunica) { dialogInterface, i ->
                    txtCommMethod.setText(listCommunica[i])
                }
            }
        }

        btnSubmit.setOnClickListener {

            if(fullName.text.toString() == "" || emailAdd.text.toString() == "" || phoneNo.text.toString() == "" || selectService.text.toString() == ""
            || serviceNo.text.toString() == "" || AmtPay.text.toString() == "" || txtBank.text.toString() == "" || txtCommMethod.text.toString() == "") {
                toast("All fields are required!")
            }
            else {

                val intent = Intent(this, ConfirmPaymentActivity::class.java)
                intent.putExtra("SELECTED_SERVICE", mServiceSelected)
                intent.putExtra("FULLNAME", fullName.text.toString())
                intent.putExtra("EMAILADD", emailAdd.text.toString())
                intent.putExtra("PHONENO", phoneNo.text.toString())
                intent.putExtra("SERVICETYPE", title)
                intent.putExtra("SERVICES", selectService.text.toString())
                intent.putExtra("SERVICENO", serviceNo.text.toString())
                intent.putExtra("AMTPAID", AmtPay.text.toString())
                intent.putExtra("BANKNAME", txtBank.text.toString())
                intent.putExtra("COMMUNICA", txtCommMethod.text.toString())
                startActivity(intent)
            }
        }

    }
}
