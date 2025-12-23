package com.apppresser.onesignal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.NotificationManagerCompat;
import com.getcapacitor.Logger;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

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
     * Set OneSignal log level. Accepts: VERBOSE, DEBUG, INFO, WARN, WARNING, ERROR, NONE
     */
    public void setLogLevel(String level) {
        if (level == null || level.isEmpty()) {
            Logger.warn("CapOneSignal", "setLogLevel: level is null/empty");
            return;
        }
        String upper = level.toUpperCase();

        // In OneSignal v5, use the LogLevel enum from com.onesignal.debug
        LogLevel logLevel;
        switch (upper) {
            case "VERBOSE":
                logLevel = LogLevel.VERBOSE;
                break;
            case "DEBUG":
                logLevel = LogLevel.DEBUG;
                break;
            case "INFO":
                logLevel = LogLevel.INFO;
                break;
            case "WARN":
            case "WARNING":
                logLevel = LogLevel.WARN;
                break;
            case "ERROR":
                logLevel = LogLevel.ERROR;
                break;
            case "NONE":
                logLevel = LogLevel.NONE;
                break;
            default:
                Logger.warn("CapOneSignal", "Unknown log level: " + level + ", defaulting to INFO");
                logLevel = LogLevel.INFO;
        }

        // Set SDK log level using the Debug manager in v5
        try {
            OneSignal.getDebug().setLogLevel(logLevel);
            Logger.info("CapOneSignal", "OneSignal log level set to: " + upper);
        } catch (Exception e) {
            Logger.warn("CapOneSignal", "Failed to set OneSignal log level: " + e.getMessage());
        }
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
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE, ctx.getPackageName());
        } else {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", ctx.getPackageName(), null));
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
