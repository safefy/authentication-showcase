package th.co.knightfrank.kf_care_android.customer.screens.message

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageByUserIDRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.message.listsectionviewadapter.MessageListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class MessageListActivity : BaseActivity() {

    private val messageListAdapter = MessageListAdapter(listener = { inboxMessageID ->
        MessageDetailActivity.start(this, inboxMessageID)
        //Log.e("MessageListActivity", "inboxMessageID : " + inboxMessageID)
    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, MessageListActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MessageListViewModel

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_message_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageListViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        if (appSettings.getInt(AppSettingsKey.ROLE_ID) != -1) {
            when (appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                RoleName.SYSTEM_ADMIN.identifier,
                RoleName.MANAGER.identifier,
                RoleName.ASSISTANT_MANAGER.identifier,
                RoleName.ADMIN.identifier,
                RoleName.TECHNICIAN_MANAGER.identifier,
                RoleName.TECHNICIAN.identifier -> {
                    toolbar_add.visibility = View.INVISIBLE
                }
                RoleName.CUSTOMER.identifier -> {
                    toolbar_add.visibility = View.VISIBLE
                }
            }
        }

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is MessageListViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is MessageListViewModel.Status.Success -> {

                    val messageList: MutableList<MessageListSectionData> = mutableListOf()

                    it.viewDataBundle.inboxMessageList!!.forEach { item ->
                        messageList.add(MessageListSectionData(item, this))
                    }

                    messageListAdapter.setItems(messageList)
                }

                is MessageListViewModel.Status.SuccessWithNoData -> {
                    messageListAdapter.clear()
                    messageListAdapter.notifyDataSetChanged()
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                }

                is MessageListViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is MessageListViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_message_list.adapter = messageListAdapter

        toolbar_add.setOnClickListener {
            MessageCreateActivity.start(this, null)
        }
    }

    override fun onResume() {
        super.onResume()

        //Main Content
        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.getInboxMessageByUserIDRequest(
                    GetInboxMessageByUserIDRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID)
                    )
            )
        }
    }
}
