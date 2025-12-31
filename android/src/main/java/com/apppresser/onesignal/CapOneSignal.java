package com.apppresser.onesignal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import com.getcapacitor.Logger;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import com.getcapacitor.PluginCall;
import com.getcapacitor.PermissionState;
import android.Manifest;
import androidx.core.app.ActivityCompat;

public class CapOneSignal {

    /**
     * Initialize OneSignal with application context and appId.
     */
    public void initialize(Context ctx, String appID) {
        if (appID == null || appID.isEmpty()) {
            Logger.warn("CapOneSignal", "initialize: appID is null/empty");
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Logger.warn("CapOneSignal", "OneSignal SDK v5 requires Android API 21 (Lollipop) or newer. Device is running API " + Build.VERSION.SDK_INT);
            return;
        }

        // Special handling for pre-release versions of Android
        if (!"REL".equals(Build.VERSION.CODENAME)) {
            Logger.info("CapOneSignal", "Running on a pre-release version of Android: " + Build.VERSION.CODENAME + ". Enabling verbose logging.");
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
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

    public void requestPermission(Context ctx, PluginCall call, boolean fallbackToSettings) {
        // This method should now ONLY handle the legacy path.
        // The Android 13+ logic is (and should be) in CapOneSignalPlugin.java.

        boolean granted = areNotificationsEnabled(ctx);
        Logger.info("CapOneSignal", "Legacy permission check: notifications enabled = " + granted);

        if (!granted && fallbackToSettings) {
            Logger.info("CapOneSignal", "Notifications are not enabled. Opening settings for user.");
            openNotificationSettings(ctx);
        }

        // Always resolve the call for this legacy path. The "status" will be determined
        // by the calling method in the plugin by re-checking areNotificationsEnabled.
        call.resolve();
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

    /**
     * Add multiple tags to the user.
     */
    public void addTags(java.util.Map<String, String> tags) {
        if (tags == null || tags.isEmpty()) {
            Logger.warn("CapOneSignal", "addTags: tags is null/empty");
            return;
        }
        OneSignal.getUser().addTags(tags);
        Logger.info("CapOneSignal", "addTags: " + tags.toString());
    }

    /**
     * Remove multiple tags from the user.
     */
    public void removeTags(java.util.Collection<String> tagKeys) {
        if (tagKeys == null || tagKeys.isEmpty()) {
            Logger.warn("CapOneSignal", "removeTags: tagKeys is null/empty");
            return;
        }
        OneSignal.getUser().removeTags(tagKeys);
        Logger.info("CapOneSignal", "removeTags: " + tagKeys.toString());
    }

    /**
     * Add a single tag to the user.
     */
    public void addTag(String key, String value) {
        if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
            Logger.warn("CapOneSignal", "addTag: key or value is null/empty");
            return;
        }
        OneSignal.getUser().addTag(key, value);
        Logger.info("CapOneSignal", "addTag: " + key + " = " + value);
    }

    /**
     * Remove a single tag from the user.
     */
    public void removeTag(String key) {
        if (key == null || key.isEmpty()) {
            Logger.warn("CapOneSignal", "removeTag: key is null/empty");
            return;
        }
        OneSignal.getUser().removeTag(key);
        Logger.info("CapOneSignal", "removeTag: " + key);
    }

}
