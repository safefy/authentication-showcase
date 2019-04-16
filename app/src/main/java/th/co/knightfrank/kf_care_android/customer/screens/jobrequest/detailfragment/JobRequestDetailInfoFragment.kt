package th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_job_request_detail.*
import kotlinx.android.synthetic.main.fragment_job_request_detail_info.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import th.co.knightfrank.data_java.data.entities.users.RoleName

import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobassignment.JobRequestJobAssignmentActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment
import th.co.knightfrank.kf_care_android.technician.screens.jobrequest.takeactionresult.TakeActionResultActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class JobRequestDetailInfoFragment : BaseFragment() {

    companion object {
        const val DATA_JOB_REQUEST_ID = "DATA_JOB_REQUEST_ID"
    }

    private lateinit var viewModel: JobRequestDetailViewModel

    private val intentJobRequestID: Int
        get() = arguments?.getInt(DATA_JOB_REQUEST_ID, 0)!!

    private var mJobRequestRecyclerViewAdapter: JobRequestRecyclerViewAdapter? = null
    private var mJobRequestTechnicianRecyclerViewAdapter: JobRequestRecyclerViewAdapter? = null

    private var is_status_approve: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobRequestDetailViewModel::class.java)

        //viewModel.getJobRequestDetailRequest(intentJobRequestID)

        //Log.e("job_id", intentJobRequestID.toString())

        return inflater.inflate(R.layout.fragment_job_request_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstances()
        viewModel.status.observe(this, Observer {
            when (it) {
                is JobRequestDetailViewModel.Status.Loading -> activity?.loading_indicator?.visible()
                else -> activity?.loading_indicator.gone()
            }

            when (it) {
                is JobRequestDetailViewModel.Status.Success -> {
                    activity?.text_view_job_request_title?.text = it.viewDataBundle.jobRequestDetailData?._title
                    activity?.text_view_job_status?.text = it.viewDataBundle.jobRequestDetailData?._jobStatus?._jobStatusNameThai

                    if (it.viewDataBundle.jobRequestDetailData?._assignToStaff != null) {
                        linear_layout_technician_container.visibility = View.VISIBLE
                        text_view_technician_name.text = it.viewDataBundle.jobRequestDetailData._assignToStaff?._firstName + " " + it.viewDataBundle.jobRequestDetailData._assignToStaff?._lastName
                    } else {
                        linear_layout_technician_container.visibility = View.GONE
                    }

                    text_view_job_request_admin_appointment_date.text = it.viewDataBundle.jobRequestDetailData?._adminAppointDate.let {
                        val adminAppointDateString = it
                        var outputDateString: String? = null
                        val inputFormat = "yyyy-MM-dd HH:mm:ss"
                        val outputFormat = "dd/MM/yyyy HH:mm"

                        if (!adminAppointDateString.isNullOrEmpty()) {
                            val inputDateFormat = SimpleDateFormat(inputFormat, Locale.US).parse(adminAppointDateString)
                            outputDateString = SimpleDateFormat(outputFormat, Locale.US).format(inputDateFormat)
                        } else {
                            val userAppointDateString = viewModel.currentValue.viewDataBundle.jobRequestDetailData?._appointDate
                            if (!userAppointDateString.isNullOrEmpty()) {
                                val inputDateFormat = SimpleDateFormat(inputFormat, Locale.US).parse(userAppointDateString)
                                outputDateString = SimpleDateFormat(outputFormat, Locale.US).format(inputDateFormat)
                            }
                        }
                        return@let outputDateString
                    }
                    text_view_job_no.text = it.viewDataBundle.jobRequestDetailData?._jobNo
                    text_view_priority.text = it.viewDataBundle.jobRequestDetailData?._priorityName
                    text_view_area.text = it.viewDataBundle.jobRequestDetailData?._jobAreaName
                    text_view_system.text = it.viewDataBundle.jobRequestDetailData?._jobSystemTypeName
                    text_view_job_title.text = it.viewDataBundle.jobRequestDetailData?._title
                    text_view_job_request_detail.text = it.viewDataBundle.jobRequestDetailData?._detail
                    text_view_job_request_contact_name.text = it.viewDataBundle.jobRequestDetailData?._contactName
                    text_view_job_request_contact_tel_no.text = it.viewDataBundle.jobRequestDetailData?._contactMobileNo
                    text_view_job_request_contact_room_no.text = it.viewDataBundle.jobRequestDetailData?._contactRoomName

                    gv_job_request_image_list.layoutManager = GridLayoutManager(context!!, 3)
                    mJobRequestRecyclerViewAdapter = JobRequestRecyclerViewAdapter(
                            it.viewDataBundle.jobRequestDetailData?._jobRequestImageList!!,
                            //testList,
                            context!!)
                    gv_job_request_image_list.adapter = mJobRequestRecyclerViewAdapter

                    if (!it.viewDataBundle.jobRequestDetailData._staffComment.isNullOrEmpty()) {
                        linear_layout_technician_comment_container.visibility = View.VISIBLE
                        text_view_technician_staff_comment.text = it.viewDataBundle.jobRequestDetailData._staffComment
                        gv_job_request_technician_image_list.layoutManager = GridLayoutManager(context!!, 3)
                        mJobRequestTechnicianRecyclerViewAdapter = JobRequestRecyclerViewAdapter(
                                it.viewDataBundle.jobRequestDetailData._jobRepairImageList!!,
                                context!!
                        )
                        gv_job_request_technician_image_list.adapter = mJobRequestTechnicianRecyclerViewAdapter
                    } else {
                        linear_layout_technician_comment_container.visibility = View.GONE
                    }

                    if (!it.viewDataBundle.jobRequestDetailData._contactComment.isNullOrEmpty()) {
                        linear_layout_contact_comment_container.visibility = View.VISIBLE
                        text_view_job_request_contact_comment.text = it.viewDataBundle.jobRequestDetailData._contactComment
                    } else {
                        linear_layout_contact_comment_container.visibility = View.GONE
                    }


                    //form job success control
                    when (it.viewDataBundle.jobRequestDetailData._jobStatus!!._jobStatusID) {
                        4 -> {
                            when (it.viewDataBundle.appSettingRoleID) {
                                RoleName.MANAGER.identifier,
                                RoleName.ASSISTANT_MANAGER.identifier,
                                RoleName.ADMIN.identifier,
                                RoleName.TECHNICIAN_MANAGER.identifier -> {
                                    val maintenanceEndDateLong: Long? = it.viewDataBundle.jobRequestDetailData._maintenanceEndDate?.replace("/Date(", "")?.replace("+0700)/", "")?.toLong()
                                    val maintenanceEndDate = maintenanceEndDateLong?.let {
                                        return@let Date(it * 1000)
                                    } ?: Calendar.getInstance().time
                                    val maintenanceEndDateRelative = getDifferenceDays(maintenanceEndDate, Calendar.getInstance().time)

                                    if (it.viewDataBundle.jobRequestDetailData._jobRequestUser!!._roleID == RoleName.CUSTOMER.identifier && maintenanceEndDateRelative > 3) {
                                        enableViewCloseJobRequest()
                                    } else {
                                        linear_layout_close_job_request.visibility = View.GONE
                                        relative_layout_job_done_btn_control.visibility = View.GONE
                                    }
                                }
                                else -> {
                                    enableViewCloseJobRequest()
                                }
                            }
                        }
                        else -> {
                            linear_layout_close_job_request.visibility = View.GONE
                            relative_layout_job_done_btn_control.visibility = View.GONE
                        }
                    }

                    hideEditButton()
                    hideAcceptButton()
                    hideTakeActionResultButton()
                    linear_layout_close_job_request.gone()
                    when (it.viewDataBundle.appSettingRoleID) {
                        RoleName.SYSTEM_ADMIN.identifier,
                        RoleName.MANAGER.identifier,
                        RoleName.ASSISTANT_MANAGER.identifier,
                        RoleName.ADMIN.identifier -> {
                            //admin
                            when (it.viewDataBundle.jobRequestDetailData._jobStatus?._jobStatusID) {
                                1 -> {
                                    showJobAssignButton()
                                }
                                2, 3, 6 -> {
                                    showEditButton()
                                }
                                4 -> {
                                    linear_layout_admin_action.visibility = View.GONE
                                    relative_layout_job_done_btn_control.visibility = View.VISIBLE
                                    edit_text_job_detail_input.visibility = View.GONE

                                    btn_send_job_request.setOnClickListener {
                                        viewModel.jobApproveByManagerRequest(
                                                jobRequestID = intentJobRequestID,
                                                isApprove = is_status_approve
                                        )
                                    }
                                }
                                else -> {
                                    linear_layout_admin_action.visibility = View.GONE
                                }
                            }
                        }
                        RoleName.TECHNICIAN_MANAGER.identifier -> {
                            when (it.viewDataBundle.jobRequestDetailData._jobStatus?._jobStatusID) {
                                1 -> {
                                    showJobAssignButton()
                                }
                                2 -> {
                                    showEditButton()
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showAcceptButton()
                                    }
                                }
                                3 -> {
                                    showEditButton()
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showTakeActionResultButton()
                                    }
                                }
                                4 -> {
                                    when (it.viewDataBundle.appSettingRoleID) {
                                        RoleName.TECHNICIAN_MANAGER.identifier -> {
                                            relative_layout_job_done_btn_control.visibility = View.VISIBLE
                                            edit_text_job_detail_input.visibility = View.GONE

                                            btn_send_job_request.setOnClickListener {
                                                viewModel.jobApproveByManagerRequest(
                                                        jobRequestID = intentJobRequestID,
                                                        isApprove = is_status_approve
                                                )
                                            }
                                        }
                                        else -> {
                                            linear_layout_close_job_request.visibility = View.GONE
                                        }
                                    }
                                }
                                6 -> {
                                    showEditButton()
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showTakeActionResultButton()
                                    }
                                }
                                else -> {
                                    linear_layout_close_job_request.visibility = View.GONE
                                }
                            }


                        }
                        RoleName.TECHNICIAN.identifier -> {
                            //technician
                            linear_layout_close_job_request.visibility = View.GONE
                            when (it.viewDataBundle.jobRequestDetailData._jobStatus?._jobStatusID) {
                                1 -> {
                                    showJobAssignButton()
                                }
                                2 -> {
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showAcceptButton()
                                    }
                                }
                                3 -> {
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showTakeActionResultButton()
                                    }
                                }
                                4 -> {
                                    linear_layout_close_job_request.visibility = View.GONE
                                }
                                6 -> {
                                    if (it.viewDataBundle.appSettingUserID == it.viewDataBundle.jobRequestDetailData._assignToStaff?._userID) {
                                        showTakeActionResultButton()
                                    }
                                }
                                else -> {
                                    linear_layout_close_job_request.visibility = View.GONE
                                }
                            }
                        }
                        RoleName.CUSTOMER.identifier -> {
                            //customer
                            btn_send_job_request.setOnClickListener {
                                viewModel.jobApproveRequest(
                                        jobRequestID = intentJobRequestID,
                                        isApprove = is_status_approve,
                                        comment = edit_text_job_detail_input.text.toString()
                                )
                            }
                        }
                    }
                }
                is JobRequestDetailViewModel.Status.SendJobApproveSuccess -> {
                    AlertDialog.Builder(activity!!)
                            .setMessage(resources.getString(R.string.job_request_detail_technician_btn_submit_success_dialog))
                            .setPositiveButton(resources.getString(R.string.job_request_create_send_success_message_dialog_accept)) { dialog, _ ->
                                dialog.dismiss()
                                activity?.finish()
                            }
                            .create()
                            .show()
                }
                is JobRequestDetailViewModel.Status.JobAcceptSuccess -> {
                    AlertDialog.Builder(activity!!)
                            .setMessage(resources.getString(R.string.job_accept_dialog_content))
                            .setPositiveButton(resources.getString(R.string.job_accept_dialog_content)) { dialog, _ ->
                                dialog.dismiss()
                                activity?.finish()
                            }
                            .create()
                            .show()
                }
                is JobRequestDetailViewModel.Status.JobApproveByManagerSuccess -> {
                    AlertDialog.Builder(activity!!)
                            .setMessage(resources.getString(R.string.job_request_detail_technician_btn_submit_success_dialog))
                            .setPositiveButton(resources.getString(R.string.job_request_create_send_success_message_dialog_accept)) { dialog, _ ->
                                dialog.dismiss()
                                activity?.finish()
                            }
                            .create()
                            .show()
                }
                is JobRequestDetailViewModel.Status.Error -> {
                    alertFacade.error("Something went wrong.", activity!!)
                }
                is JobRequestDetailViewModel.Status.ErrorException -> {
                    alertFacade.error("Something went wrong.", activity!!)
                }
                is JobRequestDetailViewModel.Status.Logout -> {
                    alertFacade.error("Invalid token, Please Login Again!!!", activity!!)
                    SplashActivity.start(activity!!, "Invalid token, Please Login Again!!!")
                }
            }
        })
    }

    private fun showJobAssignButton() {
        btn_admin_action.text = getString(R.string.job_request_detail_btn_job_assign)
        showEditButton()
    }

    private fun initInstances() {

        btnAcceptJob?.setOnClickListener {
            viewModel.jobAcceptRequest(jobRequestID = intentJobRequestID)
        }

        btn_admin_action?.run {
            setOnClickListener {
                activity?.let { activity ->
                    JobRequestJobAssignmentActivity.start(activity, intentJobRequestID)
                }
            }
        }

        btnTakeActionResult?.setOnClickListener {
            activity?.let { activity ->
                TakeActionResultActivity.start(activity, intentJobRequestID)
            }
        }

    }

    private fun enableViewCloseJobRequest() {
        var is_relative_layout_job_done_btn_control_clicked = true

        //for user submit job done
        linear_layout_close_job_request.visibility = View.VISIBLE

        relative_layout_job_done_btn_control.setOnClickListener {
            if (is_relative_layout_job_done_btn_control_clicked) {
                image_view_arrow.setImageResource(R.drawable.ic_arrow_down_bg_red)
                linear_layout_job_done_container.visibility = View.VISIBLE
                is_relative_layout_job_done_btn_control_clicked = false
            } else {
                image_view_arrow.setImageResource(R.drawable.ic_arrow_left_bg_red)
                linear_layout_job_done_container.visibility = View.GONE
                is_relative_layout_job_done_btn_control_clicked = true
            }
        }

        is_status_approve = true
        radio_group_job_status.setOnCheckedChangeListener { group, i ->
            when (i) {
                radio_btn_job_status_success.id -> {
                    is_status_approve = true
                }
                radio_btn_job_status_fail.id -> {
                    is_status_approve = false
                }
            }
        }
    }

    private fun getDifferenceDays(d1: Date, d2: Date): Long {
        val diff = d2.time - d1.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }

    override fun onResume() {
        super.onResume()

        viewModel.getJobRequestDetailRequest(intentJobRequestID)
    }

    private fun showTakeActionResultButton() {
        btnTakeActionResult.visible()
    }

    private fun hideTakeActionResultButton() {
        btnTakeActionResult.gone()
    }

    private fun showEditButton() {
        linear_layout_admin_action.visible()
    }

    private fun hideEditButton() {
        linear_layout_admin_action.gone()
    }

    private fun showAcceptButton() {
        btnAcceptJob.visible()
    }

    private fun hideAcceptButton() {
        btnAcceptJob.gone()
    }
}

private fun View?.visible() {
    this?.visibility = View.VISIBLE
}

private fun View?.gone() {
    this?.visibility = View.GONE

}


