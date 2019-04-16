package th.co.knightfrank.kf_care_android.customer.ui.alertfacade

import android.app.Activity
import android.view.View

interface IAlertFacade {

    fun info(message: String, activity: Activity)

    fun success(message: String, activity: Activity)

    fun error(message: String, activity: Activity)

    fun infoWithView(message: String, view: View)

    fun successWithView(message: String, view: View)

    fun errorWithView(message: String, view: View)
}