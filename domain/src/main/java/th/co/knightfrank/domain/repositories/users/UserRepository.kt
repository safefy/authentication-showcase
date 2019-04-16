package th.co.knightfrank.domain.repositories.users

import io.reactivex.Single
import th.co.knightfrank.data_java.data.api.API
import th.co.knightfrank.data_java.data.requests.users.*
import th.co.knightfrank.data_java.data.responses.users.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject
constructor(private val api: API) {

    fun loginMobileWithEmail(loginMobileRequest: LoginMobileRequest): Single<LoginMobileWrapperResponse<LoginMobileContainerResponse>> {
        return api.loginMobile(loginMobileRequest)
    }

    fun loginWithEmail(email: String, password: String): Single<LoginWrapperResponse<LoginContainerResponse>> { //Observable<Optional<LoginResponse>>{
        return api.login(LoginRequest(email, password))
    }

    fun inquiryProjectRegisterList(inquiryMasterProjectRegisterListRequest: InquiryMasterProjectRegisterListRequest): Single<InquiryMasterProjectRegisterListWrapperResponse<InquiryMasterProjectRegisterResponse>> {
        return api.inquiryMasterProjectList(inquiryMasterProjectRegisterListRequest)
    }

    fun getUserByInvitationCode(invitationCode: String): Single<GetUserByInvitationCodeWrapperResponse<GetUserByInvitationCodeContainerResponse>> {
        return api.getUserByInvitationCode(GetUserByInvitationCodeRequest(invitationCode))
    }

    fun getUserByUserID(getUserByUserIDRequest: GetUserByUserIDRequest): Single<GetUserByUserIDWrapperResponse<GetUserByInvitationCodeContainerResponse>> {
        return api.getUserByUserID(getUserByUserIDRequest)
    }

    fun registerCustomer(registerCustomerRequest: RegisterCustomerRequest): Single<RegisterCustomerWrapperResponse<RegisterCustomerContainerResponse>> {
        return api.registerCustomer(registerCustomerRequest)
    }

    fun registerNewCustomer(registerNewRequest: RegisterNewRequest): Single<RegisterNewCustomerWrapperResponse<RegisterNewCustomerContainerResponse>> {
        return api.registerNewCustomer(registerNewRequest)
    }

    fun inquiryCoOwnerByName(inquiryCoOwnerByNameRequest: InquiryCoOwnerByNameRequest): Single<InquiryCoOwnerByNameWrapperResponse<InquiryCoOwnerByNameContainerResponse>> {
        return api.inquiryCoOwnerByName(inquiryCoOwnerByNameRequest)
    }

    fun changeUserRoom(changeUserRoomRequest: ChangeUserRoomRequest): Single<ChangeUserRoomWrapperResponse<ChangeUserRoomContainerResponse>> {
        return api.changeUserRoom(changeUserRoomRequest)
    }

    fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): Single<ForgetPasswordWrapperResponse<ForgetPasswordContainerResponse>> {
        return api.forgetPassword(forgetPasswordRequest)
    }

    fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Single<ResetPasswordWrapperResponse<ResetPasswordContainerResponse>> {
        return api.resetPassword(resetPasswordRequest)
    }

    fun updateCustomerInfo(updateCustomerInfoRequest: UpdateCustomerInfoRequest): Single<UpdateCustomerInfoWrapperResponse<UpdateCustomerInfoContainerResponse>> {
        return api.updateCustomerInfo(updateCustomerInfoRequest)
    }

}