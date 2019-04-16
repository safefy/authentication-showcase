package th.co.knightfrank.kf_care_android.customer.components.firebaseservices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.screens.splash.SplashActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var TAG = "MyFirebaseMessagingService"

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d(TAG, "onNewToken : " + p0.toString())
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "FROM : " + remoteMessage.from)

        //Verify if the message contains data
        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Message data : " + remoteMessage.data)
        }

        //Verify if the message contains notification
        if (remoteMessage.notification != null) {
            Log.e(TAG, "Message title : " + remoteMessage.notification?.title)
            Log.e(TAG, "Message body : " + remoteMessage.notification?.body)

            //remoteMessage.notification?.title
            //sendNotification(remoteMessage.notification?.body)
            sendNotification(remoteMessage.notification)
        }
    }

    private fun sendNotification(notification: RemoteMessage.Notification?) {
        var intent = Intent(this@MyFirebaseMessagingService, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("Notification", notification?.body)

        val notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channelId, channelName, importance)

            notificationManager.createNotificationChannel(mChannel)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val icon = BitmapFactory.decodeResource(resources, R.drawable.logo_kf)

        var notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.logo_kf)
                .setContentTitle(notification?.title)
                .setContentText(notification?.body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }

}

