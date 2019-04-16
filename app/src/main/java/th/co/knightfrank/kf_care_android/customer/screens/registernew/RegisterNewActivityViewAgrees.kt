package th.co.knightfrank.kf_care_android.customer.screens.registernew

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_new_view_agrees.*
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity

class RegisterNewActivityViewAgrees : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, RegisterNewActivityViewAgrees::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }


    }

    override fun getLayoutId(): Int = R.layout.activity_register_new_view_agrees

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl("http://27.254.141.140/kfcare/doc/Terms_and_Conditions.html")

        btn_back.setOnClickListener {
            this@RegisterNewActivityViewAgrees.finish()
        }
    }
}