package app.com.android.paybills.util.models

import app.com.android.paybills.R
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object ServicesModel {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<ServicesItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, ServicesItem> = HashMap()

    private val COUNT = 3

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createServicesItem(i))
        }
    }

    private fun addItem(item: ServicesItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createServicesItem(position: Int): ServicesItem {
        val title = arrayOf("Pay TV", "Internet", "Electricity")
        val subTitle = arrayOf("DSTV, Kwese TV, Start Times, GoTV",
                "Spectranet, Smile, wifi.ng, Tizeti, Airtel Broadband",
                "Eko Electric, Ikeja Electric, Nesco")
        val imgName = arrayOf(R.drawable.ic_satellite_dish, R.drawable.ic_router, R.drawable.ic_light_bulb)


        return ServicesItem(position.toString(), imgName[position - 1], title[position - 1], subTitle[position - 1])
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class ServicesItem(val id: String, val imgName: Int, val title: String, val subtitle: String) {
        override fun toString(): String = title
    }
}
