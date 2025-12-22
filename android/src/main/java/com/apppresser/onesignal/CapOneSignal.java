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
     * Set OneSignal log level. Accepts: VERBOSE, DEBUG, INFO, WARN, WARNING, ERROR, NONE
     */
    public void setLogLevel(String level) {
        if (level == null || level.isEmpty()) {
            Logger.warn("CapOneSignal", "setLogLevel: level is null/empty");
            return;
        }
        String upper = level.toUpperCase();
        OneSignal.LOG_LEVEL logLevel = OneSignal.LOG_LEVEL.INFO;
        switch (upper) {
            case "VERBOSE":
                logLevel = OneSignal.LOG_LEVEL.VERBOSE;
                break;
            case "DEBUG":
                logLevel = OneSignal.LOG_LEVEL.DEBUG;
                break;
            case "INFO":
                logLevel = OneSignal.LOG_LEVEL.INFO;
                break;
            case "WARN":
            case "WARNING":
                logLevel = OneSignal.LOG_LEVEL.WARN;
                break;
            case "ERROR":
                logLevel = OneSignal.LOG_LEVEL.ERROR;
                break;
            case "NONE":
                logLevel = OneSignal.LOG_LEVEL.NONE;
                break;
            default:
                Logger.warn("CapOneSignal", "Unknown log level: " + level + ", defaulting to INFO");
                logLevel = OneSignal.LOG_LEVEL.INFO;
        }

        // Set SDK log level (verbose/debug/info/warn/error/none). The second parameter controls visual level; choose NONE for minimal visual logs.
        try {
            OneSignal.setLogLevel(logLevel, OneSignal.LOG_LEVEL.NONE);
            Logger.info("CapOneSignal", "OneSignal log level set to: " + upper);
        } catch (NoSuchMethodError | IncompatibleClassChangeError e) {
            // Fallback if single-arg setLogLevel exists (older/newer SDKs)
            try {
                OneSignal.setLogLevel(logLevel);
                Logger.info("CapOneSignal", "OneSignal log level set (single-arg) to: " + upper);
            } catch (Exception ex) {
                Logger.warn("CapOneSignal", "Failed to set OneSignal log level: " + ex.getMessage());
            }
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
