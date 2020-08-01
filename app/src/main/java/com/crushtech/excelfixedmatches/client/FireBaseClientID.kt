package com.crushtech.excelfixedmatches.client

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val TAG="FireBaseClientMessenger"
class FireBaseClientID : FirebaseMessagingService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

       // sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG,"Data: " + message.data.toString())
        Log.d(TAG,"Data: " + message.notification.toString())
        super.onMessageReceived(message)
    }
}