package th.co.knightfrank.kf_care_android.customer.screens.billing.water

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.views.photoview.PhotoViewActivity

class BillingWaterImageRecyclerViewAdapter(val items: MutableList<String>,
                                           val context: Context) : RecyclerView.Adapter<BillingWaterImageRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_image_round_border, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = this.items[position]

        holder?.setImageView(item, context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image_view_job_request: SimpleDraweeView = itemView.findViewById(R.id.image_view_job_request)

        fun setImageView(urlString: String, context: Context) {
            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlString))
                    .setResizeOptions(ResizeOptions(1280, 720))
                    .build()

            val controller = Fresco.newDraweeControllerBuilder()
                    .setTapToRetryEnabled(true)
                    .setOldController(image_view_job_request.controller)
                    .setImageRequest(request)
                    .build()

            image_view_job_request.controller = controller

            image_view_job_request.setOnClickListener {
                PhotoViewActivity.start(context, urlString)
            }

        }
    }

}