package th.co.knightfrank.kf_care_android.customer.ui.fragments

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import th.co.knightfrank.kf_care_android.customer.di.Injectable
import th.co.knightfrank.kf_care_android.customer.ui.alertfacade.IAlertFacade
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var alertFacade: IAlertFacade

    val parentFragmentManager: FragmentManager
        get() = fragmentManager!!

    fun alertError(message: String) {
        alertFacade.error(message, this.activity!!)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val config = resources.configuration
//        if (AppSettings(this.activity!!).getValue(AppSettingsKey.LANGUAGE).isNotEmpty()) {
//            config.setLocale(Locale(AppSettings(this.activity!!).getValue(AppSettingsKey.LANGUAGE)))
//        } else {
//            config.setLocale(Locale.getDefault())
//        }
//    }


}