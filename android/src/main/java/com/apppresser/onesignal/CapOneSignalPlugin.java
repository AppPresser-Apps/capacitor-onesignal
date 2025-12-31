package com.apppresser.onesignal;

import android.Manifest;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.content.pm.PackageManager;
import android.os.Build;
import com.getcapacitor.PermissionState;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

@CapacitorPlugin(
    name = "CapOneSignal",
    permissions = { @Permission(alias = "notification", strings = { Manifest.permission.POST_NOTIFICATIONS }) }
)
public class CapOneSignalPlugin extends Plugin {

    private CapOneSignal implementation = new CapOneSignal();

    @PluginMethod
    public void initialize(PluginCall call) {
        String appId = call.getString("appID");
        if (appId == null || appId.isEmpty()) {
            call.reject("appID is required");
            return;
        }

        // Initialize OneSignal via implementation helper
        implementation.initialize(getContext(), appId);

        call.resolve();
    }

    @PluginMethod
    public void requestPermission(PluginCall call) {
        // For Android 13 (API 33) and newer, we always trigger the runtime permission prompt.
        // If the permission has already been granted, Capacitor will resolve successfully.
        // This ensures the dialog is shown for new installations, and for existing ones
        // where the user may not have been prompted before.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionForAlias("notification", call, "notificationPermissionCallback");
        } else {
            // For older Android versions, use the legacy implementation
            boolean fallbackToSettings = call.getBoolean("fallbackToSettings", false);
            implementation.requestPermission(getContext(), call, fallbackToSettings);

            // Re-check the status after the call, as the user might have been sent to settings
            boolean isEnabled = implementation.areNotificationsEnabled(getContext());
            JSObject result = new JSObject();
            result.put("accepted", isEnabled);
            call.resolve(result);
        }
    }

    @PermissionCallback
    private void notificationPermissionCallback(PluginCall call) {
        JSObject result = new JSObject();
        if (getPermissionState("notification") == PermissionState.GRANTED) {
            Logger.info(getLogTag(), "User granted notification permission.");
            result.put("accepted", true);
            call.resolve(result);
        } else {
            Logger.warn(getLogTag(), "User denied notification permission.");
            // You can either reject or resolve with accepted: false
            result.put("accepted", false);
            call.resolve(result);
        }
    }

    @PluginMethod
    public void setExternalUserId(PluginCall call) {
        String userId = call.getString("userID");
        if (userId == null || userId.isEmpty()) {
            call.reject("userID is required");
            return;
        }

        implementation.setExternalUserId(userId);

        call.resolve();
    }

    @PluginMethod
    public void setLogLevel(PluginCall call) {
        String level = call.getString("level");
        if (level == null || level.isEmpty()) {
            call.reject("level is required");
            return;
        }

        implementation.setLogLevel(level);

        call.resolve();
    }

    @PluginMethod
    public void clearExternalUserId(PluginCall call) {
        implementation.clearExternalUserId();

        call.resolve();
    }

    @PluginMethod
    public void addTags(PluginCall call) {
        JSObject tagsObject = call.getObject("tags");
        if (tagsObject == null) {
            call.reject("tags is required");
            return;
        }

        java.util.Map<String, String> tags = new java.util.HashMap<>();
        java.util.Iterator<String> keys = tagsObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = tagsObject.optString(key, null);
            if (value != null) {
                tags.put(key, value);
            }
        }

        if (tags.isEmpty()) {
            call.reject("tags must be an object with string values");
            return;
        }

        implementation.addTags(tags);
        call.resolve();
    }

    @PluginMethod
    public void removeTags(PluginCall call) {
        JSObject tagsObject = call.getObject("tags");
        if (tagsObject == null) {
            call.reject("tags is required");
            return;
        }

        java.util.List<String> tagKeys = new java.util.ArrayList<>();
        java.util.Iterator<String> keys = tagsObject.keys();
        while (keys.hasNext()) {
            tagKeys.add(keys.next());
        }

        if (tagKeys.isEmpty()) {
            call.reject("tags must be an object with keys to remove");
            return;
        }

        implementation.removeTags(tagKeys);
        call.resolve();
    }

    @PluginMethod
    public void addTag(PluginCall call) {
        String tagKey = call.getString("tagKey");
        if (tagKey == null || tagKey.isEmpty()) {
            call.reject("tagKey is required");
            return;
        }

        String tagValue = call.getString("tagValue");
        if (tagValue == null || tagValue.isEmpty()) {
            call.reject("tagValue is required");
            return;
        }

        implementation.addTag(tagKey, tagValue);
        call.resolve();
    }

    @PluginMethod
    public void removeTag(PluginCall call) {
        String tag = call.getString("tag");
        if (tag == null || tag.isEmpty()) {
            call.reject("tag is required");
            return;
        }

        implementation.removeTag(tag);
        call.resolve();
    }

}
