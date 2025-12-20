package com.apppresser.onesignal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import com.getcapacitor.Logger;
import com.onesignal.OneSignal;

public class CapOneSignal {

    /**
     * Initialize OneSignal with application context and appId.
     */
    public void initialize(Context ctx, String appID) {
        if (appID == null || appID.isEmpty()) {
            Logger.warn("CapOneSignal", "initialize: appID is null/empty");
            return;
        }
        // Use the OneSignal v5+ API to initialize with the App ID
        OneSignal.initWithContext(ctx.getApplicationContext(), appID);
        Logger.info("CapOneSignal", "OneSignal initialized with appID: " + appID);
    }

    /**
     * Return whether notifications are enabled for the app.
     */
    public boolean areNotificationsEnabled(Context ctx) {
        return NotificationManagerCompat.from(ctx).areNotificationsEnabled();
    }

    /**
     * Open the app notification settings so the user can enable notifications.
     */
    public void openNotificationSettings(Context ctx) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, ctx.getPackageName());
        } else {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.fromParts("package", ctx.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    /**
     * Set an external user id for OneSignal (v5 API: login).
     */
    public void setExternalUserId(String userID) {
        if (userID == null || userID.isEmpty()) {
            Logger.warn("CapOneSignal", "setExternalUserId: userID is null/empty");
            return;
        }
        // Use setExternalUserId() to associate the current subscription with an external id
        OneSignal.login(userID);
        Logger.info("CapOneSignal", "setExternalUserId (login): " + userID);
    }

    /**
     * Clear external user id (v5 API: logout).
     */
    public void clearExternalUserId() {
        OneSignal.logout();
        Logger.info("CapOneSignal", "clearExternalUserId (logout) called");
    }
}
