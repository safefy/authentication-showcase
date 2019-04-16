package th.co.knightfrank.kf_care_android.customer.screens.registernew


import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register_new.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.requests.buildings.InquiryMasterBuildingRegisterListRequest
import th.co.knightfrank.data_java.data.requests.floors.InquiryMasterFloorRegisterListRequest
import th.co.knightfrank.data_java.data.requests.rooms.InquiryMasterRoomRegisterListRequest
import th.co.knightfrank.data_java.data.requests.users.InquiryMasterProjectRegisterListRequest
import th.co.knightfrank.data_java.data.requests.users.RegisterNewRequest
import th.co.knightfrank.domain.components.AppSettingsKey
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.models.registernew.RegisterNewChoiceModel
import th.co.knightfrank.kf_care_android.customer.components.appsettings.AppSettings
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity
import th.co.knightfrank.kf_care_android.customer.ui.views.spinner.SpinnerDialog
import th.co.knightfrank.kf_care_android.technician.models.BuildingChoiceModel
import th.co.knightfrank.kf_care_android.technician.models.FloorChoiceModel
import th.co.knightfrank.kf_care_android.technician.models.RoomChoiceModel
import javax.inject.Inject

class RegisterNewActivity : BaseActivity(), TextWatcher {

    companion object {

        private const val DATA_ERROR_MESSAGE = "DATA_ERROR_MESSAGE"
        fun start(context: Context, errorMessage: String? = null) {
            val intent = Intent(context, RegisterNewActivity::class.java)

            if (errorMessage != null) {
                intent.putExtra(RegisterNewActivity.DATA_ERROR_MESSAGE, errorMessage)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }


    }

    override fun getLayoutId(): Int = R.layout.activity_register_new

    @Inject
    lateinit var appSettings: AppSettings

    private lateinit var viewModel: RegisterNewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterNewViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val firebaseInstanceId = FirebaseInstanceId.getInstance().instanceId

        viewModel.inquiryMasterProjectRegisterListRequest(
                InquiryMasterProjectRegisterListRequest(
                        token = "1")
        )

        viewModel.status.observe(this, Observer {
            when (it) {
                is RegisterNewViewModel.Status.Loading -> loading_indicator.visibility = View.VISIBLE
                else -> loading_indicator.visibility = View.GONE
            }

            when (it) {
                is RegisterNewViewModel.Status.Success -> {
                    alertFacade.success(it.viewDataBundle.status_msg!!, this)

                    AlertDialog.Builder(this)
                            .setTitle(resources.getString(R.string.register_toolbar_name))
                            .setMessage(resources.getString(R.string.register_success))
                            .setPositiveButton(resources.getString(R.string.invitation_code_button_submit), { dialog, _ ->
                                dialog.dismiss()
                                this.finish()
                            })
                            .create()
                            .show()
                    //goto register next step
                    //send parcels to next step
                    //RegisterActivity.start(this, it.viewDataBundle.userInfoParcel!!, it.viewDataBundle.userInvitationCode!!)
                }
                is RegisterNewViewModel.Status.Error -> {
                    alertFacade.error(it.viewDataBundle.status_msg!!, this)
                }
                is RegisterNewViewModel.Status.LoadProjectSuccess -> {
                    Log.w("projects", "e" + viewModel.currentValue.viewDataBundle.projectList)
                    spinner_project.setChoices(
                            choices = it.viewDataBundle.projectList?.map {
                                RegisterNewChoiceModel.valueOf(it)
                            }!!
                            /*choices = let {
                                val list: MutableList<RegisterNewResponse>? = mutableListOf()
                                list?.addAll(viewModel.currentValue.viewDataBundle.projectList!!)
                                list?.add(0, RegisterNewResponse(
                                        _projectID = 0,
                                        _projectName = resources.getString(R.string.announcement_create_require_accept_default)
                                ))
                                list?.map {
                                    RegisterNewChoiceModel.valueOf(it)
                                }!!
                            }*/

                    )
                    Log.w("project", "e" + viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID)
                    viewModel.inquiryMasterBuildingRegisterListRequest(
                            InquiryMasterBuildingRegisterListRequest(

                                    projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID
                            )
                    )

                    //Log.w("building","e"+viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID)

                }

                is RegisterNewViewModel.Status.LoadBuildingSuccess -> {

                    spinner_building.setChoices(
                            choices = it.viewDataBundle.buildingList?.map {
                                BuildingChoiceModel.valueOf(it, appSettings.getValue(AppSettingsKey.LANGUAGE))

                            }!!

                            /*choices = let {
                                val list: MutableList<BuildingInfoResponse>? = mutableListOf()
                                list?.addAll(viewModel.currentValue.viewDataBundle.buildingList!!)
                                list?.add(0, BuildingInfoResponse(
                                        _buildingID = 0,
                                        _buildingNameTh= resources.getString(R.string.announcement_create_require_accept_default)
                                ))
                                list?.map {
                                    BuildingChoiceModel.valueOf(it, appSettings.getValue(AppSettingsKey.LANGUAGE))
                                }!!
                            }*/
                    )
                    // Log.e("building","Data : "+viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID)
                    viewModel.inquiryMasterFloorRegisterListRequest(
                            InquiryMasterFloorRegisterListRequest(
                                    buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID,
                                    projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID
                            )
                    )

                }

                is RegisterNewViewModel.Status.SuccessWithNoData -> {
                    AlertDialog.Builder(this)
                            //.setTitle(resources.getString(R.string.register_toolbar_name))
                            .setMessage(resources.getString(R.string.register_alert_building))
                            .setPositiveButton(resources.getString(R.string.parcel_register_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()

                }

                is RegisterNewViewModel.Status.LoadFloorSuccess -> {
                    //Log.e("SelectFloor","Data : "+it.viewDataBundle.floorList)

                    spinner_floor.setChoices(
                            choices = it.viewDataBundle.floorList?.map {
                                FloorChoiceModel.valueOf(it)
                            }!!

                            /*choices = let {
                                val list: MutableList<FloorInfoResponse>? = mutableListOf()
                                list?.addAll(viewModel.currentValue.viewDataBundle.floorList!!)
                                list?.add(0, FloorInfoResponse(
                                        _floorID = 0,
                                        _floorName = resources.getString(R.string.announcement_create_require_accept_default)
                                ))
                                list?.map {
                                    FloorChoiceModel.valueOf(it)
                                }!!
                            }*/
                    )

                    viewModel.inquiryMasterRoomRegisterListRequest(
                            InquiryMasterRoomRegisterListRequest(
                                    buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID,
                                    projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID,
                                    floorID = viewModel.currentValue.viewDataBundle.floorList!![spinner_floor.selectedIndex]._floorID
                            )
                    )
                }

                is RegisterNewViewModel.Status.SuccessWithNoDataFloor -> {
                    val count = viewModel.currentValue.viewDataBundle.count
                    if (count == "0") {
                        AlertDialog.Builder(this)
                                //.setTitle(resources.getString(R.string.register_toolbar_name))
                                .setMessage(resources.getString(R.string.register_alert_floor))
                                .setPositiveButton(resources.getString(R.string.parcel_register_dialog_accept), { dialog, _ ->
                                    dialog.dismiss()
                                    viewModel.inquiryMasterRoomRegisterListRequest(
                                            InquiryMasterRoomRegisterListRequest(
                                                    buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID,
                                                    projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID,
                                                    floorID = null
                                            )
                                    )
                                })
                                .create()
                                .show()

                    } else {

                    }


                }

                is RegisterNewViewModel.Status.LoadRoomSuccess -> {

                    spinner_room.setChoices(
                            choices = it.viewDataBundle.roomList?.map {
                                RoomChoiceModel.valueOf(it)
                            }!!

                            /*choices = let {
                                val list: MutableList<RoomsInfoResponse>? = mutableListOf()
                                list?.addAll(viewModel.currentValue.viewDataBundle.roomList!!)
                                list?.add(0, RoomsInfoResponse(
                                        _roomID = 0,
                                        _roomNo = resources.getString(R.string.announcement_create_require_accept_default)
                                ))
                                list?.map {
                                    RoomChoiceModel.valueOf(it)
                                }!!
                            }*/
                    )

                }

                is RegisterNewViewModel.Status.SuccessWithNoDataRooms -> {
                    AlertDialog.Builder(this)
                            //.setTitle(resources.getString(R.string.register_toolbar_name))
                            .setMessage(getString(R.string.register_alert_room))
                            .setPositiveButton(getString(R.string.parcel_register_dialog_accept), { dialog, _ ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                }
            }
        })

        text_view_agrees.setOnTouchListener { view, motionEvent ->
            RegisterNewActivityViewAgrees.start(this@RegisterNewActivity)

            return@setOnTouchListener false
        }

        check_box_agrees.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
            if (b) {
                btn_register_submit_new.isEnabled = true
                btn_register_submit_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_red)
            } else {
                btn_register_submit_new.isEnabled = false
                btn_register_submit_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_diable)
            }
        }

        btn_register_submit_new.setOnClickListener {
            //call register
            if (validator(
                            edit_text_first_name_input_new.text.toString(),
                            edit_text_last_name_input_new.text.toString(),
                            edit_text_tel_no_input_new.text.toString(),
                            edit_text_email_input_new.text.toString(),
                            edit_text_password_input_new.text.toString(),
                            edit_text_confirm_password_input_new.text.toString(),
                            spinner_room.selectedIndex,
                            spinner_project.selectedIndex,
                            spinner_floor.selectedIndex


                    )) {
                firebaseInstanceId.addOnSuccessListener {
                    viewModel.registerNewCustomer(
                            RegisterNewRequest(
                                    firstName = edit_text_first_name_input_new.text.toString(),
                                    lastName = edit_text_last_name_input_new.text.toString(),
                                    mobileNo = edit_text_tel_no_input_new.text.toString(),
                                    email = edit_text_email_input_new.text.toString(),
                                    lineID = "",
                                    password = edit_text_password_input_new.text.toString(),
                                    registerType = 1,
                                    parkingNo = "",
                                    carRegistration = "",
                                    isContactLine = true,
                                    isContactMobile = true,
                                    projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID!!,
                                    buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID!!,
                                    floorID = viewModel.currentValue.viewDataBundle.floorList!![spinner_floor.selectedIndex]._floorID!!,
                                    roomID = viewModel.currentValue.viewDataBundle.roomList!![spinner_room.selectedIndex]._roomID!!,
                                    firebaseToken = it.token,
                                    invitationCode = 1
                            )
                    )
                }

            }
        }

        btn_register_cancel_new.setOnClickListener {
            this.finish()
        }


        //if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {

        //}

        spinner_project?.onSelectedChangeListener = object : SpinnerDialog.OnSelectedChangeListener {
            override fun onSelectedChange(choice: SpinnerDialog.Choice, index: Int) {
                // if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                viewModel.inquiryMasterBuildingRegisterListRequest(
                        InquiryMasterBuildingRegisterListRequest(
                                //projectID = waterBillingWaterJobInfoParcelData._projectID,
                                projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID
                        )
                )
                // }
            }
        }
        spinner_building?.onSelectedChangeListener = object : SpinnerDialog.OnSelectedChangeListener {
            override fun onSelectedChange(choice: SpinnerDialog.Choice, index: Int) {
                // if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                viewModel.inquiryMasterFloorRegisterListRequest(
                        InquiryMasterFloorRegisterListRequest(
                                //projectID = waterBillingWaterJobInfoParcelData._projectID,
                                projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID,
                                buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID
                        )
                )
                // }
            }
        }

        spinner_floor?.onSelectedChangeListener = object : SpinnerDialog.OnSelectedChangeListener {
            override fun onSelectedChange(choice: SpinnerDialog.Choice, index: Int) {
                // if (appSettings.getInt(AppSettingsKey.USER_ID) != -1) {
                viewModel.inquiryMasterRoomRegisterListRequest(
                        InquiryMasterRoomRegisterListRequest(
                                //projectID = waterBillingWaterJobInfoParcelData._projectID,
                                projectID = viewModel.currentValue.viewDataBundle.projectList!![spinner_project.selectedIndex]._projectID,
                                buildingID = viewModel.currentValue.viewDataBundle.buildingList!![spinner_building.selectedIndex]._buildingID,
                                floorID = viewModel.currentValue.viewDataBundle.floorList!![spinner_floor.selectedIndex]._floorID
                        )
                )
                // }
            }
        }

        edit_text_first_name_input_new.addTextChangedListener(this)
        edit_text_last_name_input_new.addTextChangedListener(this)
        edit_text_tel_no_input_new.addTextChangedListener(this)
        edit_text_email_input_new.addTextChangedListener(this)
        edit_text_password_input_new.addTextChangedListener(this)
        edit_text_confirm_password_input_new.addTextChangedListener(this)
    }

    fun validator(
            first_name: String,
            last_name: String,
            tel_no: String,
            email: String,
            password: String,
            confirm_password: String,
            roomID: Int,
            projectID: Int,
            floorID: Int
    ): Boolean {
//        if (floorID == 0) {
//            alertFacade.error(getString(R.string.error_alert_floor), this)
//            spinner_room.requestFocus()
//            return false
//        }
//
//        if (roomID == 0) {
//            alertFacade.error(getString(R.string.error_alert_room), this)
//            spinner_floor.requestFocus()
//            return false
//        }

        var isValida: Boolean = true

        if (first_name == "") {
            isValida = false
            edit_text_first_name_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_first_name_input_new.requestFocus()
        }

        if (last_name == "") {
            isValida = false
            edit_text_last_name_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_last_name_input_new.requestFocus()
        }
        if (tel_no == "") {
            isValida = false
            edit_text_tel_no_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_tel_no_input_new.requestFocus()
        }
        if (email == "") {
            isValida = false
            edit_text_email_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_email_input_new.requestFocus()
        }
        if (password == "") {
            isValida = false
            edit_text_password_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_password_input_new.requestFocus()
        }
        if (password.length <= 5) {
            isValida = false
            edit_text_password_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_password_input_new.requestFocus()
        }
        if (confirm_password.isEmpty() || password != confirm_password) {
            isValida = false
            edit_text_confirm_password_input_new.background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
            edit_text_confirm_password_input_new.requestFocus()
        }

        return isValida
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val background: Drawable?
        if (p0?.count() == 0)
            background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white_stroke_5)
        else
            background = ContextCompat.getDrawable(this, R.drawable.shape_button_round_corner_red_bg_white)

        when (p0?.hashCode()) {
            edit_text_first_name_input_new.editableText.hashCode() -> {
                edit_text_first_name_input_new.background = background
            }
            edit_text_last_name_input_new.editableText.hashCode() -> {
                edit_text_last_name_input_new.background = background
            }
            edit_text_tel_no_input_new.editableText.hashCode() -> {
                edit_text_tel_no_input_new.background = background
            }
            edit_text_email_input_new.editableText.hashCode() -> {
                edit_text_email_input_new.background = background
            }
            edit_text_password_input_new.editableText.hashCode() -> {
                edit_text_password_input_new.background = background
            }
            edit_text_confirm_password_input_new.editableText.hashCode() -> {
                edit_text_confirm_password_input_new.background = background
            }
        }
    }

}
