package app.com.android.paybills.util.providers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "SocialDB", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable("Users", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "firstName" to TEXT,
                "lastName" to TEXT,
                "emailAddress" to TEXT,
                "mobileNo" to TEXT,
                "username" to TEXT,
                "password" to TEXT,
                "userid" to INTEGER)

        db.createTable("Transactions", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "fullName" to TEXT,
                "emailAddress" to TEXT,
                "mobileNo" to TEXT,
                "serviceProvider" to TEXT,
                "serviceIdNumber" to TEXT,
                "cost" to REAL,
                "bankName" to TEXT,
                "communication" to TEXT,
                "userid" to INTEGER,
                "username" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.dropTable("Users", true)
        db.dropTable("Transactions", true)
    }

    val Context.database: MyDatabaseOpenHelper
        get() = MyDatabaseOpenHelper.getInstance(applicationContext)

}