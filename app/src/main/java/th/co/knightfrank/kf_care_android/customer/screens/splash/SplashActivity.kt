package th.co.knightfrank.kf_care_android.customer.screens.splash

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.users.LoginMobileRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.main.MainAdminActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.login.LoginActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.MainActivity
import th.co.knightfrank.kf_care_android.customer.screens.register.RegisterInvitationCodeActivity
import th.co.knightfrank.kf_care_android.customer.screens.registernew.RegisterNewActivity
import th.co.knightfrank.kf_care_android.customer.screens.resetpassword.ForgetPasswordActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import th.co.knightfrank.kf_care_android.technician.screens.main.MainTechnicianActivity
import javax.inject.Inject

@RuntimePermissions
class SplashActivity : BaseActivity() {

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, SplashActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    private lateinit var viewModel: SplashViewModel

    private val errorMessage: String? by lazy { intent.getStringExtra(DATA_ERROR_MESSAGE) }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

        //var token = FirebaseInstanceId.getInstance().token
        //Log.e("SplashActivity","Token : "+token)

        val firebaseInstanceId = FirebaseInstanceId.getInstance().instanceId
        var token: String? = null

//        firebaseInstanceId.addOnSuccessListener {
//            token = it.token
//            Log.e("newToken", it.token)
//        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is SplashViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {

                is SplashViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.status_msg!!, this)

                    //PLS Make sure is AppSttingsKey with this key is set value (not null) Before use this code
                    if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {

                        //Login and check role
                        //is 7 -> customer
                        //is 5,6 -> technician
                        //is 1,2,3,4 -> admin
                        when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                            RoleName.SYSTEM_ADMIN.identifier,
                            RoleName.MANAGER.identifier,
                            RoleName.ASSISTANT_MANAGER.identifier,
                            RoleName.ADMIN.identifier -> {
                                //admin
                                //MainAdminActivity.start(this)
                                goToMainAdminWithPermissionCheck()
                            }
                            RoleName.TECHNICIAN_MANAGER.identifier,
                            RoleName.TECHNICIAN.identifier -> {
                                //technician
                                //MainTechnicianActivity.start(this)
                                goToMainTechnicianWithPermissionCheck()
                            }
                            RoleName.CUSTOMER.identifier -> {
                                //customer
                                //MainActivity.start(this)
                                goToMainCustomerWithPermissionCheck()
                            }
                        }
                        //alertFacade.error("USER_ID : " + appSettings.getInt(AppSettingsKey.USER_ID), this)
                    }
                }
                is SplashViewModel.Status.Error -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
                is SplashViewModel.Status.ErrorException -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }

            }
        })

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            firebaseInstanceId.addOnSuccessListener {
                viewModel.loginMobileWithEmail(
                        LoginMobileRequest(
                                username = appSettings.getValue(AppSettingsKey.USERNAME),
                                password = appSettings.getValue(AppSettingsKey.PASSWORD),
                                uuid = it.token
                        )
                )
            }

//            viewModel.loginMobileWithEmail(
//                    LoginMobileRequest(
//                            username = appSettings.getValue(AppSettingsKey.USERNAME),
//                            password = appSettings.getValue(AppSettingsKey.PASSWORD),
//                            uuid = let {
//                                var firebaseToken: String = null
//                                firebaseInstanceId.addOnSuccessListener {
//                                    Log.e("newToken", it.token)
//                                    it.token
//                                }
//                            }
//                    )
//            )
        }

//        val config = resources.configuration
//        config.setLocale(Locale("th"))
//        resources.updateConfiguration(config, resources.displayMetrics)


        if (errorMessage != null) {
            alertFacade.error(errorMessage!!, this)
        }

        btn_email_login_splash.setOnClickListener {
            if (validator(edit_text_email_input_splash.text.toString(), edit_text_password_input_splash.text.toString())) {
                //viewModel.emailLoginRequest("yahoo@gmail.com", "drowssap1")
                //viewModel.emailLoginRequest(edit_text_email_input.text.toString().trim(), edit_text_password_input.text.toString().trim())

                firebaseInstanceId.addOnSuccessListener {
                    viewModel.loginMobileWithEmail(
                            LoginMobileRequest(
                                    username = edit_text_email_input_splash.text.toString().trim(),
                                    password = edit_text_password_input_splash.text.toString().trim(),
                                    uuid = it.token
                            )
                    )
                }
            }
        }

        text_view_switch_language.setOnClickListener({ _ -> switchLanguage() })
        /*btn_email_register.setOnClickListener({ _ ->
            //startRegisterInvitationCodeActivity()
            //goToRegisterInvitationCodeWithPermissionCheck()

        })
        btn_goto_login.setOnClickListener({ _ ->
            goToLoginWithPermissionCheck()
        })*/

        text_view_goto_forget_password_splash.setOnClickListener {
            ForgetPasswordActivity.start(this)
        }
        btn_register_splash.setOnClickListener({ _ ->
            startRegisterNewActivity()
            //goToRegisterInvitationCodeWithPermissionCheck()
        })
    }

    fun startRegisterNewActivity() {
        RegisterNewActivity.start(this)
    }

    fun startRegisterInvitationCodeActivity() {
        RegisterInvitationCodeActivity.start(this)
    }

    fun startLoginActivity() {
        LoginActivity.start(this)
    }

    fun validator(email: String, password: String): Boolean {
        if (email == "") {
            alertFacade.error(getString(R.string.error_require_email), this)
            edit_text_email_input_splash.requestFocus()

            return false
        }

        if (password == "") {
            alertFacade.error(getString(R.string.error_require_password), this)
            edit_text_password_input_splash.requestFocus()

            return false
        }

        return true
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

                            SplashActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }
                        getString(R.string.language_thai) -> {
                            //config.setLocale(Locale("th"))
                            //resources.updateConfiguration(config, resources.displayMetrics)
                            appSettings.setValue(AppSettingsKey.LANGUAGE, "th")

                            SplashActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }
                        getString(R.string.language_english) -> {
                            //config.setLocale(Locale.ENGLISH)
                            //resources.updateConfiguration(config, resources.displayMetrics)
                            appSettings.setValue(AppSettingsKey.LANGUAGE, "en")

                            SplashActivity.start(this, null)

                            //alertFacade.success(config.locale.language, this)
                        }

                    }
                    dialog.dismiss()
                })
                .create()
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToRegisterInvitationCode() {
        RegisterInvitationCodeActivity.start(this)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToMainCustomer() {
        MainActivity.start(this)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToMainAdmin() {
        MainAdminActivity.start(this)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToMainTechnician() {
        MainTechnicianActivity.start(this)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun goToLogin() {
        LoginActivity.start(this)
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun readPermissionDenied() {
        alertFacade.error("OnPermissionDenied", this)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun neverAskReadPermissionAgain() {
        alertFacade.error("OnNeverAskAgain", this)
    }


}