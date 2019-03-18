package app.com.android.paybills.home.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import app.com.android.paybills.R

class TransactionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        val imgTransactionComplete: ImageView = findViewById(R.id.imgPaymentConfirmedIndicator)
        val imgServiceComplete: ImageView = findViewById(R.id.imgServiceDeliveredIndicator)

        val bundle = intent.extras
        if(bundle!=null) {
            val strTransacStatus = bundle.getString("TRANSAC_STATUS")

            if(strTransacStatus == "0") {
                imgTransactionComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_spinner_dots))

                imgServiceComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_spinner_dots))
            }
            else if(strTransacStatus == "1") {
                imgTransactionComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_check_mark))

                imgServiceComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_spinner_dots))
            }
            else {
                imgTransactionComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_check_mark))

                imgServiceComplete.setImageDrawable(ContextCompat.getDrawable(this@TransactionsActivity,
                        R.drawable.ic_check_mark))
            }
        }
    }
}
