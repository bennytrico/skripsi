package com.example.skripsicustomer1;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import static com.example.skripsicustomer1.CurrentUser.currentUserID;
import static com.example.skripsicustomer1.CurrentUser.getCurrentCustomerData;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        getCurrentCustomerData();
        if (currentUserID != null) {
            sendRegistrationToServer(token);
        }
    }
    private void sendRegistrationToServer(String token) {
        DatabaseReference dbCustomer = FirebaseDatabase.getInstance().getReference("Customers").child(currentUserID);
        Map<String, Object> updateToken = new HashMap<String, Object>();
        updateToken.put("fcm_token",token);
        dbCustomer.updateChildren(updateToken);
    }
}
