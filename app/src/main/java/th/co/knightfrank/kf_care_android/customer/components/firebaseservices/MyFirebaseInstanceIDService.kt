package th.co.knightfrank.kf_care_android.customer.components.firebaseservices

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    private var TAG = "MyFirebaseInstanceIDService"

    override fun onTokenRefresh() {
        //Get updated token
        var refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "New Token : " + refreshedToken)
    }
}