package com.onesignal.capacitor;

import com.getcapacitor.JSObject;
import com.onesignal.notifications.IPermissionObserver;

public class PermissionObserver implements IPermissionObserver {
    private final OneSignalCapacitorPlugin plugin;

    public PermissionObserver(OneSignalCapacitorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onNotificationPermissionChange(boolean permission) {
        JSObject ret = new JSObject();
        ret.put("permission", permission);
        plugin.notifyListenersPublic("permissionChange", ret);
    }
}
