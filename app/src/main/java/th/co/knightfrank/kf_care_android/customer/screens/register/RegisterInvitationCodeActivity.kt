package th.co.knightfrank.kf_care_android.customer.screens.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register_invitation_code.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity

class RegisterInvitationCodeActivity : BaseActivity() {

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, RegisterInvitationCodeActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: RegisterInvitationCodeViewModel

    override fun getLayoutId(): Int = R.layout.activity_register_invitation_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseInstanceId = FirebaseInstanceId.getInstance().instanceId
//        var token:String? = null
//
//        firebaseInstanceId.addOnSuccessListener {
//            token = it.token
//        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterInvitationCodeViewModel::class.java)

        viewModel.status.observe(this, Observer {
            when (it) {
                is RegisterInvitationCodeViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is RegisterInvitationCodeViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.status_msg!!, this)

                    //goto register next step
                    //send parcels to next step
                    RegisterActivity.start(this, it.viewDataBundle.userInfoParcel!!, it.viewDataBundle.userInvitationCode!!)
                }
                is RegisterInvitationCodeViewModel.Status.Error -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
            }
        })

        btn_submit.setOnClickListener {
            //viewModel.getUserByInvitationCodeRequest("2018000334")

            if (validator(edit_text_invitation_code_input.text.toString())) {

                firebaseInstanceId.addOnSuccessListener {
                    viewModel.getUserByInvitationCodeRequest(
                            edit_text_invitation_code_input.text.toString().trim(),
                            it.token
                    )
                }
            }
        }


        btn_cancel.setOnClickListener {
            this.finish()
        }

    }

    fun validator(invitationCode: String): Boolean {

        if (invitationCode == "") {

            alertFacade.error(getString(R.string.error_require_invitation_code), this)
            edit_text_invitation_code_input.requestFocus()

            return false
        }

        return true
    }
}
