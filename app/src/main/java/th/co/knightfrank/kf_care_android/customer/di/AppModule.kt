package th.co.knightfrank.kf_care_android.customer.di

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import dagger.Binds
import dagger.Module
import th.co.knightfrank.kf_care_android.MainApplication
import th.co.knightfrank.kf_care_android.customer.ui.alertfacade.AlertFacade
import th.co.knightfrank.kf_care_android.customer.ui.alertfacade.IAlertFacade
import th.co.knightfrank.kf_care_android.customer.ui.viewmodels.ViewModelFactory

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(mainApplication: MainApplication): Context

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindAlertFacade(alertFacade: AlertFacade): IAlertFacade
}
