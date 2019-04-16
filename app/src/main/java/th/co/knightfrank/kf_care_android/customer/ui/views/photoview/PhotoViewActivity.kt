package th.co.knightfrank.kf_care_android.customer.ui.views.photoview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_photo_view.*
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity

class PhotoViewActivity : BaseActivity() {

    companion object {
        private const val DATA_IMAGE_URL = "DATA_IMAGE_URL"

        fun start(context: Context, imageURL: String? = null) {
            val intent = Intent(context, PhotoViewActivity::class.java)

            if (imageURL != null) {
                intent.putExtra(DATA_IMAGE_URL, imageURL)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_photo_view

    private val imageURLData: String by lazy { intent.getStringExtra(DATA_IMAGE_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photo_view.setImageURI(imageURLData)

    }
}
