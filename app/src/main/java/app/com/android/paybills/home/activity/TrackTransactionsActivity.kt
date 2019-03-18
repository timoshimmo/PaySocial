package app.com.android.paybills.home.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import app.com.android.paybills.R

class TrackTransactionsActivity : AppCompatActivity() {

    val CUSTOM_PREF_NAME = "UserName"
    var progressBar: ProgressBar? = null
    lateinit var rvHistory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_transactions)
    }
}
