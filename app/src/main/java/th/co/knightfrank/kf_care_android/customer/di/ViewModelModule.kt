package th.co.knightfrank.kf_care_android.customer.di

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import th.co.knightfrank.kf_care_android.admin.screens.announcement.AnnouncementCreateViewModel
import th.co.knightfrank.kf_care_android.admin.screens.announcement.edit.AnnouncementEditViewModel
import th.co.knightfrank.kf_care_android.admin.screens.announcement.personsearch.PersonSearchViewModel
import th.co.knightfrank.kf_care_android.admin.screens.billing.list.BillingListViewModel
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobassignment.JobAssignmentViewModel
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobcreate.CreateJobRequestByAdminViewModel
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.search.SearchJobRequestViewModel
import th.co.knightfrank.kf_care_android.admin.screens.message.reply.ReplyInboxMessageViewModel
import th.co.knightfrank.kf_care_android.admin.screens.parcel.checkout.ParcelCheckoutViewModel
import th.co.knightfrank.kf_care_android.admin.screens.parcel.registerparcel.RegisterParcelViewModel
import th.co.knightfrank.kf_care_android.admin.screens.room.search.RoomSearchViewModel
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementListViewModel
import th.co.knightfrank.kf_care_android.customer.screens.billing.central.BillingCentralExpenseDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.billing.water.BillingWaterDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestCreateViewModel
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestListViewModel
import th.co.knightfrank.kf_care_android.customer.screens.login.LoginViewModel
import th.co.knightfrank.kf_care_android.customer.screens.main.MainViewModel
import th.co.knightfrank.kf_care_android.customer.screens.main.dashboardsearch.DashboardSearchViewModel
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageCreateViewModel
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageListViewModel
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelDetailViewModel
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelListViewModel
import th.co.knightfrank.kf_care_android.customer.screens.profile.ProfileViewModel
import th.co.knightfrank.kf_care_android.customer.screens.profile.editprofile.ProfileEditViewModel
import th.co.knightfrank.kf_care_android.customer.screens.register.RegisterInvitationCodeViewModel
import th.co.knightfrank.kf_care_android.customer.screens.register.RegisterViewModel
import th.co.knightfrank.kf_care_android.customer.screens.registernew.RegisterNewViewModel
import th.co.knightfrank.kf_care_android.customer.screens.resetpassword.ForgetPasswordViewModel
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.ChangeUserRoomListViewModel
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashViewModel
import th.co.knightfrank.kf_care_android.technician.screens.billing.getwaterbillingjobdetail.WaterBillingJobDetailViewModel
import th.co.knightfrank.kf_care_android.technician.screens.billing.getwaterbillingjoblist.GetWaterBillingJobListViewModel
import th.co.knightfrank.kf_care_android.technician.screens.billing.saveroomwatermeter.SaveRoomWaterMeterViewModel
import th.co.knightfrank.kf_care_android.technician.screens.jobrequest.takeactionresult.TakeActionResultViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelMapKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(RegisterNewViewModel::class)
    abstract fun bindRegisterNewViewModel(viewModel: RegisterNewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(RegisterInvitationCodeViewModel::class)
    abstract fun bindRegisterInvitationCodeViewModel(viewModel: RegisterInvitationCodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(AnnouncementListViewModel::class)
    abstract fun bindAnnouncementListViewModel(viewModel: AnnouncementListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(AnnouncementDetailViewModel::class)
    abstract fun bindAnnouncementDetailViewModel(viewModel: AnnouncementDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(JobRequestCreateViewModel::class)
    abstract fun bindJobRequestCreateViewModel(viewModel: JobRequestCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(JobRequestListViewModel::class)
    abstract fun bindJobRequestListViewModel(viewModel: JobRequestListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(JobRequestDetailViewModel::class)
    abstract fun bindJobRequestDetailViewModel(viewModel: JobRequestDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ParcelListViewModel::class)
    abstract fun bindParcelListViewModel(viewModel: ParcelListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ParcelDetailViewModel::class)
    abstract fun bindParcelDetailViewModel(viewModel: ParcelDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(MessageCreateViewModel::class)
    abstract fun bindMessageCreateViewModel(viewModel: MessageCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(MessageListViewModel::class)
    abstract fun bindMessageListViewModel(viewModel: MessageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(MessageDetailViewModel::class)
    abstract fun bindMessageDetailViewModel(viewModel: MessageDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(AnnouncementCreateViewModel::class)
    abstract fun bindAnnouncementCreateViewModel(viewModel: AnnouncementCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(PersonSearchViewModel::class)
    abstract fun bindPersonSearchViewModel(viewModel: PersonSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(AnnouncementEditViewModel::class)
    abstract fun bindAnnouncementEditViewModel(viewModel: AnnouncementEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(JobAssignmentViewModel::class)
    abstract fun bindJobAssignmentViewModel(viewModel: JobAssignmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(CreateJobRequestByAdminViewModel::class)
    abstract fun bindCreateJobRequestByAdminViewModel(viewModel: CreateJobRequestByAdminViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(TakeActionResultViewModel::class)
    abstract fun bindTakeActionResultViewModel(viewModel: TakeActionResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ReplyInboxMessageViewModel::class)
    abstract fun bindReplyInboxMessageViewModel(viewModel: ReplyInboxMessageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(GetWaterBillingJobListViewModel::class)
    abstract fun bindGetWaterBillingJobListViewModel(viewModel: GetWaterBillingJobListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(WaterBillingJobDetailViewModel::class)
    abstract fun bindWaterBillingJobDetailViewModel(viewModel: WaterBillingJobDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(SaveRoomWaterMeterViewModel::class)
    abstract fun bindSaveRoomWaterMeterViewModel(viewModel: SaveRoomWaterMeterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ChangeUserRoomListViewModel::class)
    abstract fun bindChangeUserRoomListViewModel(viewModel: ChangeUserRoomListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(DashboardSearchViewModel::class)
    abstract fun bindDashboardSearchViewModel(viewModel: DashboardSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(RoomSearchViewModel::class)
    abstract fun bindRoomSearchViewModel(viewModel: RoomSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(RegisterParcelViewModel::class)
    abstract fun bindRegisterParcelViewModel(viewModel: RegisterParcelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ParcelCheckoutViewModel::class)
    abstract fun bindParcelCheckoutViewModel(viewModel: ParcelCheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(BillingWaterDetailViewModel::class)
    abstract fun bindBillingWaterDetailViewModel(viewModel: BillingWaterDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(BillingCentralExpenseDetailViewModel::class)
    abstract fun bindBillingCentralExpenseDetailViewModel(viewModel: BillingCentralExpenseDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ForgetPasswordViewModel::class)
    abstract fun bindForgetPasswordViewModel(viewModel: ForgetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(ProfileEditViewModel::class)
    abstract fun bindProfileEditViewModel(viewModel: ProfileEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(SearchJobRequestViewModel::class)
    abstract fun bindSearchJobRequestViewModel(viewModel: SearchJobRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(BillingListViewModel::class)
    abstract fun bindBillingListViewModel(viewModel: BillingListViewModel): ViewModel

}