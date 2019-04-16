package th.co.knightfrank.kf_care_android.customer.screens.workorder

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_work_order_web_view.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class WorkOrderWebViewActivity : BaseActivity() {
    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, WorkOrderWebViewActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_work_order_web_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (appSettings.getInt(AppSettingsKey.USER_ID) == -1) {
            alertFacade.error("Invalid token, Please Login Again!!!", this)
            appSettings.clearValue(AppSettingsKey.USER_ID)
            SplashActivity.start(this, null)
        }

        web_view_work_order!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                //super.onPageStarted(view, url, favicon)
                loading_indicator.visibility = View.VISIBLE
                Log.e("web_view_work_order", "onPageStarted")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                //super.onPageFinished(view, url)
                loading_indicator.visibility = View.GONE
                Log.e("web_view_work_order", "onPageFinished")
            }
        }

        web_view_work_order.settings.javaScriptEnabled = true
        web_view_work_order.loadUrl("http://203.154.74.42:8007/mobile/index?token=" + appSettings.getValue(AppSettingsKey.LOGIN_TOKEN))


    }
}
