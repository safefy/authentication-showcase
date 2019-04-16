package th.co.knightfrank.kf_care_android.customer.screens.billing.water

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.knightfrank.data_java.data.requests.billings.GetBillingWaterJobInvoiceDetailRequest
import th.co.knightfrank.data_java.data.requests.billings.InformPaymentRequest
import th.co.knightfrank.data_java.data.responses.HeaderResponse
import th.co.knightfrank.data_java.data.responses.billings.BillingWaterJobInvoiceDetailInfoResponse
import th.co.knightfrank.domain.repositories.billings.BillingRepository
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.DisposableViewModel
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.StatusViewModel
import javax.inject.Inject

class BillingWaterDetailViewModel
@Inject
constructor(private val billingRepository: BillingRepository)
    : DisposableViewModel(), StatusViewModel<BillingWaterDetailViewModel.Status, BillingWaterDetailViewModel.ViewDataBundle> {

    private val mutableStatus: MutableLiveData<Status> = MutableLiveData()

    override val status: LiveData<Status>
        get() = mutableStatus

    init {
        mutableStatus.value = Status.Stale(ViewDataBundle())
    }

    @MainThread
    fun getBillingWaterJobInvoiceDetailRequest(getBillingWaterJobInvoiceDetailRequest: GetBillingWaterJobInvoiceDetailRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                billingRepository.getBillingWaterJobInvoiceDetail(
                        getBillingWaterJobInvoiceDetailRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.Success(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!,
                                                    billingInfoResponse = opt._data?._responseBody
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "014" ->
                                    mutableStatus.value = Status.Logout("Something went wrong", ViewDataBundle())
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    @MainThread
    fun informPaymentRequest(informPaymentRequest: InformPaymentRequest) {
        mutableStatus.value = Status.Loading(currentValue.viewDataBundle)
        addDisposable(
                billingRepository.informPayment(
                        informPaymentRequest
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ opt ->
                            when {
                                opt._data!!._responseHeader!!._responseCode == "000" -> {
                                    mutableStatus.value = Status.SubmitSuccess(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                }
                                opt._data!!._responseHeader!!._responseCode == "001" ->
                                    mutableStatus.value = Status.SuccessWithNoData(
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                                opt._data!!._responseHeader!!._responseCode == "014" ->
                                    mutableStatus.value = Status.Logout("Something went wrong", ViewDataBundle())
                                else ->
                                    mutableStatus.value = Status.Error(
                                            opt._data!!._responseHeader!!._responseDesc!!,
                                            currentValue.viewDataBundle.copy(
                                                    headerResponse = opt._data!!._responseHeader!!
                                            )
                                    )
                            }
                        }, {
                            mutableStatus.value = Status.ErrorException("Something went wrong", ViewDataBundle())
                        })
        )
    }

    sealed class Status(viewDataBundle: ViewDataBundle) : StatusViewModel.Status<ViewDataBundle>(viewDataBundle) {
        class Stale(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Loading(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Success(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class SuccessWithNoData(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Error(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class ErrorException(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
        class Logout(val message: String, viewDataBundle: ViewDataBundle) : Status(viewDataBundle)

        class SubmitSuccess(viewDataBundle: ViewDataBundle) : Status(viewDataBundle)
    }

    data class ViewDataBundle(val headerResponse: HeaderResponse? = null,
                              val billingInfoResponse: BillingWaterJobInvoiceDetailInfoResponse? = null)
}