package th.co.knightfrank.kf_care_android.customer.screens.resetpassword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.users.ForgetPasswordRequest
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class ForgetPasswordActivity : BaseActivity() {
    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, ForgetPasswordActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_forget_password

    @Inject
    lateinit var appSettings: AppSettings

    private lateinit var viewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForgetPasswordViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_black)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        btn_email_submit.setOnClickListener {
            if (edit_text_email_input.text.count() > 0) {
                viewModel.forgetPasswordRequest(
                        ForgetPasswordRequest(
                                _email = edit_text_email_input.text.toString()
                        )
                )
            } else {
                alertFacade.info("Please input email same as registered email.", this)
            }
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is ForgetPasswordViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ForgetPasswordViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.headerResponse!!._responseDesc!!, this)

                    AlertDialog.Builder(this)
                            .setMessage(
                                    String.format(
                                            resources.getString(R.string.forget_password_dialog_content),
                                            edit_text_email_input.text.toString()
                                    )
                            )
                            .setPositiveButton(resources.getString(R.string.forget_password_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                                this.finish()
                            })
                            .create()
                            .show()
                }

                is ForgetPasswordViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is ForgetPasswordViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, null)
                    } else {
                        alertFacade.error("Something went wrong!!!", this)
                    }
                }

                is ForgetPasswordViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

    }
}
