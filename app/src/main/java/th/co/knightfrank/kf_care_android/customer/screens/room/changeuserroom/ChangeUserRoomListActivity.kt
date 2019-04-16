package th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_change_user_room_list.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.users.ChangeUserRoomRequest
import th.co.knightfrank.data_java.data.requests.users.GetUserByUserIDRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.screens.main.MainActivity
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.listsectionviewadapter.UserRoomListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import javax.inject.Inject

class ChangeUserRoomListActivity : BaseActivity() {

    private val changeUserRoomListAdapter = ChangeUserRoomListAdapter(listener = { roomID ->
        AlertDialog.Builder(this)
                .setMessage(resources.getString(R.string.change_room_dialog_confirm_content))
                .setPositiveButton(resources.getString(R.string.change_room_dialog_confirm_accept), { dialog, _ ->
                    dialog.dismiss()
                    viewModel.changeUserRoomRequest(
                            ChangeUserRoomRequest(
                                    token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                                    userID = appSettings.getInt(AppSettingsKey.USER_ID),
                                    roomID = roomID
                            )
                    )
                })
                .setNegativeButton(resources.getString(R.string.change_room_dialog_confirm_cancel), { dialog, _ ->
                    dialog.dismiss()
                })
                .create()
                .show()

    }, context = this)

    companion object {
        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"

        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, ChangeUserRoomListActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: ChangeUserRoomListViewModel

    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId(): Int = R.layout.activity_change_user_room_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChangeUserRoomListViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_black)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
            viewModel.getUserByUserIDRequest(
                    GetUserByUserIDRequest(
                            token = appSettings.getValue(AppSettingsKey.LOGIN_TOKEN),
                            userID = appSettings.getInt(AppSettingsKey.USER_ID)
                    )
            )
        }

        viewModel.status.observe(this, Observer {
            when (it) {
                is ChangeUserRoomListViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is ChangeUserRoomListViewModel.Status.LoadUserInfoSuccess -> {
                    val list: MutableList<UserRoomListSectionData> = mutableListOf()
                    it.viewDataBundle.userRoomInfoList!!.forEach { item ->
                        list.add(UserRoomListSectionData(item, this))
                    }
                    changeUserRoomListAdapter.setItems(list)
                }

                is ChangeUserRoomListViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                    MainActivity.start(this)
                }

                is ChangeUserRoomListViewModel.Status.SuccessWithNoData -> {
                    changeUserRoomListAdapter.clear()
                    changeUserRoomListAdapter.notifyDataSetChanged()
                    alertFacade.info(it.viewDataBundle.headerResponse!!._responseDesc!!, this)
                }

                is ChangeUserRoomListViewModel.Status.Error -> {
                    if (it.viewDataBundle.headerResponse!!._responseCode == "014") {
                        alertFacade.error("Invalid token, Please Login Again!!!", this)
                        appSettings.clearValue(AppSettingsKey.USER_ID)
                        SplashActivity.start(this, null)
                    }
                }

                is ChangeUserRoomListViewModel.Status.ErrorException -> {
                    alertFacade.error(it.message, this)
                }
            }
        })

        recycler_view_user_room_list.adapter = changeUserRoomListAdapter
    }
}
