package th.co.knightfrank.kf_care_android.customer.ui.alertfacade

import android.app.Activity
import android.view.View
import de.mateware.snacky.Snacky
import javax.inject.Inject

class AlertFacade
@Inject constructor() : IAlertFacade {

    private fun build(message: String, activity: Activity): Snacky.Builder {
        return Snacky.builder()
                .setActivity(activity)
                .setText(message)
    }

    override fun info(message: String, activity: Activity) {
        build(message, activity).info().show()
    }

    override fun success(message: String, activity: Activity) {
        build(message, activity).success().show()
    }

    override fun error(message: String, activity: Activity) {
        build(message, activity).error().show()
    }

    private fun buildWithView(message: String, view: View): Snacky.Builder {
        return Snacky.builder()
                .setView(view)
                .setText(message)
    }

    override fun infoWithView(message: String, view: View) {
        buildWithView(message, view).info().show()
    }

    override fun successWithView(message: String, view: View) {
        buildWithView(message, view).success().show()
    }

    override fun errorWithView(message: String, view: View) {
        buildWithView(message, view).error().show()
    }
}
