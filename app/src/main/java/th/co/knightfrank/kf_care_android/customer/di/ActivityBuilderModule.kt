package th.co.knightfrank.kf_care_android.customer.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import th.co.knightfrank.kf_care_android.admin.screens.announcement.AnnouncementCreateActivity
import th.co.knightfrank.kf_care_android.admin.screens.announcement.edit.AnnouncementEditActivity
import th.co.knightfrank.kf_care_android.admin.screens.announcement.personsearch.PersonSearchActivity
import th.co.knightfrank.kf_care_android.admin.screens.billing.list.BillingListActivity
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobassignment.JobRequestJobAssignmentActivity
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.jobcreate.CreateJobRequestByAdminActivity
import th.co.knightfrank.kf_care_android.admin.screens.jobrequest.search.SearchJobRequestActivity
import th.co.knightfrank.kf_care_android.admin.screens.main.MainAdminActivity
import th.co.knightfrank.kf_care_android.admin.screens.message.reply.ReplyInboxMessageActivity
import th.co.knightfrank.kf_care_android.admin.screens.parcel.checkout.ParcelCheckoutActivity
import th.co.knightfrank.kf_care_android.admin.screens.parcel.registerparcel.RegisterParcelActivity
import th.co.knightfrank.kf_care_android.admin.screens.room.search.RoomSearchActivity
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.announcement.AnnouncementListActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.central.BillingCentralExpenseDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.billing.water.BillingWaterDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestCreateActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.JobRequestListActivity
import th.co.knightfrank.kf_care_android.customer.screens.login.LoginActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.MainActivity
import th.co.knightfrank.kf_care_android.customer.screens.main.dashboardsearch.DashboardSearchActivity
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageCreateActivity
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.message.MessageListActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelDetailActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.ParcelListActivity
import th.co.knightfrank.kf_care_android.customer.screens.parcel.guide.ParcelGuideActivity
import th.co.knightfrank.kf_care_android.customer.screens.profile.ProfileActivity
import th.co.knightfrank.kf_care_android.customer.screens.profile.editprofile.ProfileEditActivity
import th.co.knightfrank.kf_care_android.customer.screens.register.RegisterActivity
import th.co.knightfrank.kf_care_android.customer.screens.register.RegisterInvitationCodeActivity
import th.co.knightfrank.kf_care_android.customer.screens.registernew.RegisterNewActivity
import th.co.knightfrank.kf_care_android.customer.screens.registernew.RegisterNewActivityViewAgrees
import th.co.knightfrank.kf_care_android.customer.screens.resetpassword.ForgetPasswordActivity
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.ChangeUserRoomListActivity
import th.co.knightfrank.kf_care_android.customer.screens.settings.SettingsActivity
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity
import th.co.knightfrank.kf_care_android.customer.screens.workorder.WorkOrderWebViewActivity
import th.co.knightfrank.kf_care_android.customer.ui.views.photoview.PhotoViewActivity
import th.co.knightfrank.kf_care_android.technician.screens.billing.getwaterbillingjobdetail.WaterBillingJobDetailActivity
import th.co.knightfrank.kf_care_android.technician.screens.billing.getwaterbillingjoblist.GetWaterBillingJobListActivity
import th.co.knightfrank.kf_care_android.technician.screens.billing.saveroomwatermeter.SaveRoomWaterMeterActivity
import th.co.knightfrank.kf_care_android.technician.screens.jobrequest.takeactionresult.TakeActionResultActivity
import th.co.knightfrank.kf_care_android.technician.screens.main.MainTechnicianActivity


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterInvitationCodeActivity(): RegisterInvitationCodeActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterNewActivity(): RegisterNewActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterNewActivityViewAgrees(): RegisterNewActivityViewAgrees


    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementDetailActivity(): AnnouncementDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeProfileActivity(): ProfileActivity

    @ContributesAndroidInjector
    abstract fun contributeWorkOrderWebViewActivity(): WorkOrderWebViewActivity

    @ContributesAndroidInjector
    abstract fun contributeJobRequestCreateActivity(): JobRequestCreateActivity

    @ContributesAndroidInjector
    abstract fun contributeJobRequestListActivity(): JobRequestListActivity

    @ContributesAndroidInjector
    abstract fun contributeJobRequestDetailActivity(): JobRequestDetailActivity

    @ContributesAndroidInjector
    abstract fun contributePhotoViewActivity(): PhotoViewActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivity(): SettingsActivity

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementListActivity(): AnnouncementListActivity

    @ContributesAndroidInjector
    abstract fun contributeParcelListActivity(): ParcelListActivity

    @ContributesAndroidInjector
    abstract fun contributeParcelGuideActivity(): ParcelGuideActivity

    @ContributesAndroidInjector
    abstract fun contributeParcelDetailActivity(): ParcelDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeMessageCreateActivity(): MessageCreateActivity

    @ContributesAndroidInjector
    abstract fun contributeMessageListActivity(): MessageListActivity

    @ContributesAndroidInjector
    abstract fun contributeMessageDetailActivity(): MessageDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeMainAdminActivity(): MainAdminActivity

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementCreateActivity(): AnnouncementCreateActivity

    @ContributesAndroidInjector
    abstract fun contributePersonSearchActivity(): PersonSearchActivity

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementEditActivity(): AnnouncementEditActivity

    @ContributesAndroidInjector
    abstract fun contributeJobRequestJobAssignmentActivity(): JobRequestJobAssignmentActivity

    @ContributesAndroidInjector
    abstract fun contributeCreateJobRequestByAdminActivity(): CreateJobRequestByAdminActivity

    @ContributesAndroidInjector
    abstract fun contributeTakeActionResultActivity(): TakeActionResultActivity

    @ContributesAndroidInjector
    abstract fun contributeMainTechnicianActivity(): MainTechnicianActivity

    @ContributesAndroidInjector
    abstract fun contributeReplyInboxMessageActivity(): ReplyInboxMessageActivity

    @ContributesAndroidInjector
    abstract fun contributeGetWaterBillingJobListActivity(): GetWaterBillingJobListActivity

    @ContributesAndroidInjector
    abstract fun contributeWaterBillingJobDetailActivity(): WaterBillingJobDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeSaveRoomWaterMeterActivity(): SaveRoomWaterMeterActivity

    @ContributesAndroidInjector
    abstract fun contributeChangeUserRoomListActivity(): ChangeUserRoomListActivity

    @ContributesAndroidInjector
    abstract fun contributeDashboardSearchActivity(): DashboardSearchActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterParcelActivity(): RegisterParcelActivity

    @ContributesAndroidInjector
    abstract fun contributeRoomSearchActivity(): RoomSearchActivity

    @ContributesAndroidInjector
    abstract fun contributeParcelCheckoutActivity(): ParcelCheckoutActivity

    @ContributesAndroidInjector
    abstract fun contributeBillingWaterDetailActivity(): BillingWaterDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeBillingCentralExpenseDetailActivity(): BillingCentralExpenseDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeForgetPasswordActivity(): ForgetPasswordActivity

    @ContributesAndroidInjector
    abstract fun contributeProfileEditActivity(): ProfileEditActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchJobRequestActivity(): SearchJobRequestActivity

    @ContributesAndroidInjector
    abstract fun contributeBillingListActivity(): BillingListActivity
}