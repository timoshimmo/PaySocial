package app.com.android.paybills.util.volley

import org.json.JSONObject

class ApiController constructor(serviceInjection: ServiceInterface): ServiceInterface {

    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, params, completionHandler)
    }


}