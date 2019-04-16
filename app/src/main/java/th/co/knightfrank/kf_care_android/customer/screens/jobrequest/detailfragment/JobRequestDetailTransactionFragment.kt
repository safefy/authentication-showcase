package th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_job_request_detail_transaction.*
import kotlinx.android.synthetic.main.layout_loading_overlay.*
import org.threeten.bp.format.DateTimeFormatter

import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.ui.fragments.BaseFragment


class JobRequestDetailTransactionFragment : BaseFragment() {

    companion object {
        const val DATA_JOB_REQUEST_ID = "DATA_JOB_REQUEST_ID"
    }

    private lateinit var viewModel: JobRequestDetailViewModel

    private val intentJobRequestID: Int
        get() = arguments?.getInt(JobRequestDetailTransactionFragment.DATA_JOB_REQUEST_ID, 0)!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobRequestDetailViewModel::class.java)

        return inflater.inflate(R.layout.fragment_job_request_detail_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(this, Observer {
            when (it) {
                is JobRequestDetailViewModel.Status.Loading -> activity!!.loading_indicator.visibility = View.VISIBLE
                else -> activity!!.loading_indicator.visibility = View.GONE
            }

            when (it) {
                is JobRequestDetailViewModel.Status.Success -> {
                    it.viewDataBundle.transactionList!!.forEach { item ->
                        when (item._jobStatus!!._jobStatusID) {
                            1 -> {
                                image_view_transaction_status_1.setImageResource(R.drawable.ic_approve_bg_green)
                                text_view_transaction_status_1_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.black))))
                                linear_layout_transaction_status_1_desc_container.visibility = View.VISIBLE
                                text_view_transaction_status_1_desc.text = item._transactionDetail
                                text_view_transaction_status_1_created_date.text = item._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            }
                            2 -> {
                                image_view_transaction_status_2.setImageResource(R.drawable.ic_approve_bg_grey)
                                text_view_transaction_status_2_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.black))))
                                linear_layout_transaction_status_2_desc_container.visibility = View.VISIBLE
                                text_view_transaction_status_2_desc.text = item._jobStatus!!._jobStatusNameThai
                                text_view_transaction_status_2_created_date.text = item._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            }
                            3 -> {
                                image_view_transaction_status_2.setImageResource(R.drawable.ic_approve_bg_green)
                                text_view_transaction_status_2_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.black))))
                                linear_layout_transaction_status_2_desc_container.visibility = View.VISIBLE
                                text_view_transaction_status_2_desc.text = item._transactionDetail
                                text_view_transaction_status_2_created_date.text = item._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            }
                            4 -> {
                                image_view_transaction_status_3.setImageResource(R.drawable.ic_approve_bg_green)
                                text_view_transaction_status_3_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.black))))
                                linear_layout_transaction_status_3_desc_container.visibility = View.VISIBLE
                                text_view_transaction_status_3_desc.text = item._transactionDetail
                                text_view_transaction_status_3_created_date.text = item._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            }
                            5 -> {
                                image_view_transaction_status_4.setImageResource(R.drawable.ic_approve_bg_green)
                                text_view_transaction_status_4_title.setTextColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(activity!!, R.color.black))))
                                linear_layout_transaction_status_4_desc_container.visibility = View.VISIBLE
                                text_view_transaction_status_4_desc.text = item._transactionDetail
                                text_view_transaction_status_4_created_date.text = item._createDateTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                            }
                        }
                    }
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

    override fun onResume() {
        super.onResume()
        viewModel.getJobRequestTransactionListRequest(intentJobRequestID)
    }

}