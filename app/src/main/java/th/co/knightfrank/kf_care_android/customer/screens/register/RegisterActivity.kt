package th.co.knightfrank.kf_care_android.customer.screens.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.parceler.Parcels
import th.co.knightfrank.data_java.data.parcels.UserInfoParcel
import th.co.knightfrank.data_java.data.requests.users.RegisterCustomerRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.main.MainActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    companion object {
        private const val DATA_USER_INFO = "DATA_USER_INFO"
        private const val DATA_USER_INVITATION_CODE = "DATA_USER_INVITATION_CODE"

        fun start(context: Context, userInfoData: UserInfoParcel? = null, userInvitationCode: String? = null) {
            val intent = Intent(context, RegisterActivity::class.java)

            if (userInfoData != null && userInvitationCode != null) {
                intent.putExtra(DATA_USER_INFO, Parcels.wrap(userInfoData))
                intent.putExtra(DATA_USER_INVITATION_CODE, Parcels.wrap(userInvitationCode))
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    @Inject
    lateinit var appSettings: AppSettings

    private val userInfoParcel: UserInfoParcel by lazy { Parcels.unwrap<UserInfoParcel>(intent.getParcelableExtra(DATA_USER_INFO)) }
    private val userInvitationCode: String by lazy { Parcels.unwrap<String>(intent.getParcelableExtra(DATA_USER_INVITATION_CODE)) }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        viewModel.status.observe(this, Observer {
            when (it) {
                is RegisterViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is RegisterViewModel.Status.Success -> {

                    appSettings.setValue(AppSettingsKey.ROOM_NO,
                            userInfoParcel._roomInfo?.let {
                                if (it._roomNo != null) {
                                    return@let it._roomNo
                                } else {
                                    return@let ""
                                }
                            }!!
                    )

                    alertFacade.success(it.viewDataBundle.status_msg!!, this)

                    //goto register next step
                    //send parcels to next step
                    MainActivity.start(this)
                }
                is RegisterViewModel.Status.Error -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
                is RegisterViewModel.Status.ErrorException -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
            }
        })

        edit_text_first_name_input.setText(userInfoParcel._firstName)
        edit_text_last_name_input.setText(userInfoParcel._lastName)
        edit_text_tel_no_input.setText(userInfoParcel._mobileNo)
        edit_text_email_input.setText(userInfoParcel._email)

        text_view_project.text = userInfoParcel._projectInfo?._projectNameTh
        text_view_floor.text = userInfoParcel._floorInfo?._floorName
        text_view_room.text = userInfoParcel._roomInfo?._roomNo

        btn_register_submit.setOnClickListener {
            //call register
            if (validator(
                            edit_text_first_name_input.text.toString(),
                            edit_text_last_name_input.text.toString(),
                            edit_text_tel_no_input.text.toString(),
                            edit_text_email_input.text.toString(),
                            edit_text_password_input.text.toString(),
                            edit_text_confirm_password_input.text.toString()
                    )) {
                viewModel.registerCustomer(
                        RegisterCustomerRequest(
                                userID = userInfoParcel._userID!!,
                                firstName = edit_text_first_name_input.text.toString(),
                                lastName = edit_text_last_name_input.text.toString(),
                                mobileNo = edit_text_tel_no_input.text.toString(),
                                email = edit_text_email_input.text.toString(),
                                lineID = userInfoParcel._lineID!!,
                                password = edit_text_password_input.text.toString(),
                                registerType = userInfoParcel._registerType!!,
                                parkingNo = userInfoParcel._parkingNo!!,
                                carRegistration = userInfoParcel._carRegistration!!,
                                isContactLine = userInfoParcel._isContactLine!!,
                                isContactMobile = userInfoParcel._isContactMobile!!,
                                projectID = userInfoParcel._projectInfo?._projectID!!,
                                buildingID = userInfoParcel._buildingInfo?._buildingID!!,
                                floorID = userInfoParcel._floorInfo?._floorID!!,
                                roomID = userInfoParcel._roomInfo?._roomID!!,
                                firebaseToken = userInfoParcel._firebaseToken!!,
                                invitationCode = userInvitationCode
                        )
                )
            }
        }

        btn_register_cancel.setOnClickListener {
            this.finish()
        }

    }

    fun validator(
            first_name: String,
            last_name: String,
            tel_no: String,
            email: String,
            password: String,
            confirm_password: String
    ): Boolean {

        if (first_name == "") {
            alertFacade.error(getString(R.string.error_require_first_name), this)
            edit_text_first_name_input.requestFocus()
            return false
        }
        if (last_name == "") {
            alertFacade.error(getString(R.string.error_require_last_name), this)
            edit_text_last_name_input.requestFocus()
            return false
        }
        if (tel_no == "") {
            alertFacade.error(getString(R.string.error_require_tel_no), this)
            edit_text_tel_no_input.requestFocus()
            return false
        }
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
        if (password.length <= 5) {
            alertFacade.error(getString(R.string.error_require_password_size), this)
            edit_text_password_input.requestFocus()
            return false
        }
        if (password != confirm_password) {
            alertFacade.error(getString(R.string.error_require_confirmed_password), this)
            edit_text_confirm_password_input.requestFocus()
            return false
        }

        return true
    }


}
