package app.com.android.paybills.util.providers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferencesHelper {

    val USER_ID = "USER_ID"
    val USER_NAME = "USER_NAME"
    val USERDATA = "USERDATA"


    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.username
        get() = getString(USER_NAME, "")
        set(value) {
            editMe {
                it.putString(USER_NAME, value)
            }
        }

    var SharedPreferences.userid
        get() = getString(USER_ID, "")
        set(value) {
            editMe {
                it.putString(USER_ID, value)
            }
        }

    var SharedPreferences.userdata
        get() = getString(USERDATA, "")
        set(value) {
            editMe {
                it.putString(USERDATA, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}