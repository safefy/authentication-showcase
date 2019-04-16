package th.co.knightfrank.kf_care_android.customer.screens.message

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_message_detail.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.threeten.bp.format.DateTimeFormatter
import th.co.knightfrank.data_java.data.entities.users.RoleName
import th.co.knightfrank.data_java.data.requests.messages.GetInboxMessageDetailRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.message.reply.ReplyInboxMessageActivity
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class MessageDetailActivity : BaseActivity() {

    companion object {
        private const val DATA_MESSAGE_ID = "DATA_MESSAGE_ID"

        fun start(context: Context, dashboardID: Int? = null) {
            val intent = Intent(context, MessageDetailActivity::class.java)

            if (dashboardID != null) {
                intent.putExtra(DATA_MESSAGE_ID, dashboardID)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_message_detail

    private val inboxMessageIDData: Int by lazy { intent.getIntExtra(DATA_MESSAGE_ID, 0) }

    private var messageDetailImageRecyclerViewAdapter: MessageDetailImageRecyclerViewAdapter? = null

    private lateinit var viewModel: MessageDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageDetailViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.status.observe(this, Observer {
            when (it) {
                is MessageDetailViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is MessageDetailViewModel.Status.Success -> {
                    text_view_message_subject.text = it.viewDataBundle.messageDetailData?._subject
                    text_view_message_from.text = resources.getString(
                            R.string.message_detail_from,
                            it.viewDataBundle.messageDetailData?._contactRoomName,
                            it.viewDataBundle.messageDetailData?._contactName
                    )
                    text_view_message_to.text = resources.getString(
                            R.string.message_detail_to,
                            resources.getString(R.string.legal_entity)
                    )
                    text_view_message_created_date.text = it.viewDataBundle.messageDetailData?._createAt!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"))

                    if (it.viewDataBundle.messageDetailData._inboxMessageImageList!!.isNotEmpty()) {
                        linear_layout_message_images_container.visibility = View.VISIBLE

                        message_image_list.layoutManager = GridLayoutManager(this, 3)
                        messageDetailImageRecyclerViewAdapter = MessageDetailImageRecyclerViewAdapter(
                                it.viewDataBundle.messageDetailData?._inboxMessageImageList!!,
                                this
                        )
                        message_image_list.adapter = messageDetailImageRecyclerViewAdapter
                    } else {
                        linear_layout_message_images_container.visibility = View.GONE
                    }

                    text_view_message_content.text = it.viewDataBundle.messageDetailData?._contents

                    //reply contents
                    if (it.viewDataBundle.messageDetailData?._replyMessage != null && it.viewDataBundle.messageDetailData?._isReply == true) {
                        //have reply
                        linear_layout_reply_content.visibility = View.VISIBLE
                        linear_layout_send_reply_container.visibility = View.GONE
                        text_view_reply_subject.text = it.viewDataBundle.messageDetailData?._replyMessage?._subject
                        text_view_reply_to.text = resources.getString(
                                R.string.message_detail_reply_to_user,
                                it.viewDataBundle.messageDetailData?._contactRoomName,
                                it.viewDataBundle.messageDetailData?._contactName
                        )
                        text_view_reply_created_date.text = it.viewDataBundle.messageDetailData?._replyMessage?._createAt?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"))
                        text_view_reply_content.text = it.viewDataBundle.messageDetailData?._replyMessage?._contents
                    } else {
                        //don't have reply
                        linear_layout_reply_content.visibility = View.GONE
                        linear_layout_send_reply_container.visibility = View.VISIBLE

                        btn_send_reply.setOnClickListener {
                            ReplyInboxMessageActivity.start(
                                    context = this,
                                    messageID = inboxMessageIDData,
                                    messageSubject = viewModel.currentValue.viewDataBundle.messageDetailData?._subject,
                                    contactName = viewModel.currentValue.viewDataBundle.messageDetailData?._contactName,
                                    contactRoomName = viewModel.currentValue.viewDataBundle.messageDetailData?._contactRoomName
                            )
                        }
                    }

                    if (RoleName.CUSTOMER.identifier == appSettings.getInt(AppSettingsKey.ROLE_ID)) {
                        linear_layout_send_reply_container.visibility = View.GONE
                    }
                }

                is MessageDetailViewModel.Status.SuccessWithNoData -> {
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    this.finish()
                }

                is MessageDetailViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        SplashActivity.start(this, null)
                    }
                }

                is MessageDetailViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1 && inboxMessageIDData != 0) {
            viewModel.getInboxMessageDetailRequest(
                    GetInboxMessageDetailRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID),
                            inboxMessageID = inboxMessageIDData
                    )
            )
        }
    }
}
