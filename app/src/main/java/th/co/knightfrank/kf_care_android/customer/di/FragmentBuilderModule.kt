package th.co.knightfrank.kf_care_android.customer.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import th.co.knightfrank.kf_care_android.admin.screens.announcement.AnnouncementCreateInfoFragment
import th.co.knightfrank.kf_care_android.admin.screens.announcement.AnnouncementCreatePersonFragment
import th.co.knightfrank.kf_care_android.admin.screens.announcement.AnnouncementCreateSendTypeFragment
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment.JobRequestDetailInfoFragment
import th.co.knightfrank.kf_care_android.customer.screens.jobrequest.detailfragment.JobRequestDetailTransactionFragment
import th.co.knightfrank.kf_care_android.customer.screens.main.MainCardFragment
import th.co.knightfrank.kf_care_android.customer.screens.parcel.guide.ParcelGuideFragment
import th.co.knightfrank.kf_care_android.customer.screens.profile.ProfileInfoFragment
import th.co.knightfrank.kf_care_android.customer.screens.profile.ProfileNotificationSettingFragment

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeJobRequestDetailInfoFragment(): JobRequestDetailInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeJobRequestDetailTransactionFragment(): JobRequestDetailTransactionFragment

    @ContributesAndroidInjector
    abstract fun contributeMainCardFragment(): MainCardFragment

    @ContributesAndroidInjector
    abstract fun contributeParcelGuideFragment(): ParcelGuideFragment

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementCreateInfoFragment(): AnnouncementCreateInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementCreateSendTypeFragment(): AnnouncementCreateSendTypeFragment

    @ContributesAndroidInjector
    abstract fun contributeAnnouncementCreateAllPersonFragment(): AnnouncementCreatePersonFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileInfoFragment(): ProfileInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileNotificationSettingFragment(): ProfileNotificationSettingFragment

}