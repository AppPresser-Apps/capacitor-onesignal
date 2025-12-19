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

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }

    /**
     * Initialize OneSignal with application context and appId.
     */
    public void initialize(Context ctx, String appId) {
        if (appId == null || appId.isEmpty()) {
            Logger.warn("CapOneSignal", "initialize: appId is null/empty");
            return;
        }
        // Use the OneSignal v5+ API to initialize with the App ID
        OneSignal.initWithContext(ctx.getApplicationContext(), appId);
        Logger.info("CapOneSignal", "OneSignal initialized with appId: " + appId);
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
    public void setExternalUserId(String externalUserId) {
        if (externalUserId == null || externalUserId.isEmpty()) {
            Logger.warn("CapOneSignal", "setExternalUserId: externalUserId is null/empty");
            return;
        }
        // Use login() to associate the current subscription with an external id
        OneSignal.login(externalUserId);
        Logger.info("CapOneSignal", "setExternalUserId (login): " + externalUserId);
    }

    /**
     * Remove external user id (v5 API: logout).
     */
    public void removeExternalUserId() {
        OneSignal.logout();
        Logger.info("CapOneSignal", "removeExternalUserId (logout) called");
    }
}
