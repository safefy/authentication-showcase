package th.co.knightfrank.kf_care_android.customer.components

import android.content.Intent
import android.net.Uri

class IntentBuilder {

    companion object {
        fun imageSelector(title: String): Intent {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            return Intent.createChooser(intent, title)
        }

        fun location(lat: Double, lng: Double): Intent {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:$lat,$lng")
            return intent
        }

        fun callTo(telNo: String): Intent {
            return Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telNo")
            }
        }
    }
}