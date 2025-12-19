package com.apppresser.onesignal;

import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapOneSignal")
public class CapOneSignalPlugin extends Plugin {

    private CapOneSignal implementation = new CapOneSignal();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        String appId = call.getString("appId");
        if (appId == null || appId.isEmpty()) {
            call.reject("appId is required");
            return;
        }

        // Initialize OneSignal via implementation helper
        implementation.initialize(getContext(), appId);

        call.resolve();
    }

    @PluginMethod
    public void requestPermission(PluginCall call) {
        boolean fallbackToSettings = call.getBoolean("fallbackToSettings", false);
        Context ctx = getContext();

        boolean granted = implementation.areNotificationsEnabled(ctx);

        if (!granted && fallbackToSettings) {
            implementation.openNotificationSettings(ctx);
        }

        JSObject ret = new JSObject();
        ret.put("accepted", granted);
        call.resolve(ret);
    }
}