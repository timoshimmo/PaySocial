package app.com.android.paybills.util.volley

import org.json.JSONObject

interface ServiceInterface {

    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}