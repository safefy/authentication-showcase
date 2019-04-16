package th.co.knightfrank.kf_care_android.customer.screens.login


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.users.LoginMobileRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.main.MainAdminActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.main.MainActivity
import th.co.knightfrank.kf_care_android.customer.screens.registernew.RegisterNewActivity
import th.co.knightfrank.kf_care_android.customer.screens.resetpassword.ForgetPasswordActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import th.co.knightfrank.kf_care_android.technician.screens.main.MainTechnicianActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, LoginActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        //var token = FirebaseInstanceId.getInstance().token
        //Log.e("LoginActivity","Token : "+token)
        val firebaseInstanceId = FirebaseInstanceId.getInstance().instanceId
//        var token:String? = null
//
//        firebaseInstanceId.addOnSuccessListener {
//            token = it.token
//        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is LoginViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {

                is LoginViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.status_msg!!, this)


                    when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                        RoleName.SYSTEM_ADMIN.identifier,
                        RoleName.MANAGER.identifier,
                        RoleName.ASSISTANT_MANAGER.identifier,
                        RoleName.ADMIN.identifier -> {
                            //admin
                            MainAdminActivity.start(this)
                        }
                        RoleName.TECHNICIAN_MANAGER.identifier,
                        RoleName.TECHNICIAN.identifier -> {
                            //technician
                            MainTechnicianActivity.start(this)
                        }
                        RoleName.CUSTOMER.identifier -> {
                            //customer
                            MainActivity.start(this)
                        }
                    }

                    //goto mainActivity
                    //MainActivity.start(this)
                }
                is LoginViewModel.Status.Error -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
                is LoginViewModel.Status.ErrorException -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }

            }
        })

        btn_email_login.setOnClickListener {
            if (validator(edit_text_email_input.text.toString(), edit_text_password_input.text.toString())) {
                //viewModel.emailLoginRequest("yahoo@gmail.com", "drowssap1")
                //viewModel.emailLoginRequest(edit_text_email_input.text.toString().trim(), edit_text_password_input.text.toString().trim())

                firebaseInstanceId.addOnSuccessListener {
                    viewModel.loginMobileWithEmail(
                            LoginMobileRequest(
                                    username = edit_text_email_input.text.toString().trim(),
                                    password = edit_text_password_input.text.toString().trim(),
                                    uuid = it.token
                            )
                    )
                }
            }
        }

        text_view_switch_languages.setOnClickListener({ _ -> switchLanguage() })

        text_view_goto_forget_password.setOnClickListener {
            ForgetPasswordActivity.start(this)
        }

        btn_register.setOnClickListener({ _ ->
            startRegisterNewActivity()
            //goToRegisterInvitationCodeWithPermissionCheck()
        })
    }

    fun startRegisterNewActivity() {
        RegisterNewActivity.start(this)
    }

    fun switchLanguage() {
        val items = arrayOf(getString(R.string.settings_language_option_default), getString(R.string.language_thai), getString(R.string.language_english))

        val config = resources.configuration

        AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.language_alert_title))
                .setItems(items, { dialog, which ->
                    when (items[which]) {
                        getString(R.string.settings_language_option_default) -> {
                            //config.setLocale(Locale.getDefault())
                            //resources.updateConfiguration(config, resources.displayMetrics)
                            appSettings.setValue(AppSettingsKey.LANGUAGE, "")

                            LoginActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }
                        getString(R.string.language_thai) -> {
                            //config.setLocale(Locale("th"))
                            //resources.updateConfiguration(config, resources.displayMetrics)
                            appSettings.setValue(AppSettingsKey.LANGUAGE, "th")

                            LoginActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }
                        getString(R.string.language_english) -> {
                            //config.setLocale(Locale.ENGLISH)
                            //resources.updateConfiguration(config, resources.displayMetrics)
                            appSettings.setValue(AppSettingsKey.LANGUAGE, "en")

                            LoginActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }

                    }
                    dialog.dismiss()
                })
                .create()
                .show()
    }

    fun validator(email: String, password: String): Boolean {
        if (email == "") {
            alertFacade.error(getString(R.string.error_require_email), this)
            edit_text_email_input.requestFocus()

            return false
        }

        if (password == "") {
            alertFacade.error(getString(R.string.error_require_password), this)
            edit_text_password_input.requestFocus()

            return false
        }

        return true
    }
}
