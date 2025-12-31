import Capacitor
import Foundation

/// Please read the Capacitor iOS Plugin Development Guide
/// here: https://capacitorjs.com/docs/plugins/ios
@objc(CapOneSignalPlugin)
public class CapOneSignalPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapOneSignalPlugin"
    public let jsName = "CapOneSignal"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermission", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLogLevel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setExternalUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clearExternalUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addTags", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeTags", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addTag", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeTag", returnType: CAPPluginReturnPromise),
    ]
    private let implementation = CapOneSignal()

    @objc func initialize(_ call: CAPPluginCall) {
        guard let appID = call.getString("appID") else {
            call.reject("appId is required")
            return
        }
        implementation.initialize(appID)
        call.resolve()
    }

    @objc func setLogLevel(_ call: CAPPluginCall) {
        guard let level = call.getString("level") else {
            call.reject("level is required")
            return
        }
        implementation.setLogLevel(level)
        call.resolve()
    }

    @objc func requestPermission(_ call: CAPPluginCall) {
        // iOS permission request logic - resolve with whether user accepted notifications
        implementation.requestPermission { accepted in
            call.resolve([
                "accepted": accepted
            ])
        }
    }

    @objc func setExternalUserId(_ call: CAPPluginCall) {
        guard let userID = call.getString("userID") else {
            call.reject("externalUserId is required")
            return
        }
        implementation.setExternalUserId(userID)
        call.resolve()
    }

    @objc func clearExternalUserId(_ call: CAPPluginCall) {
        implementation.clearExternalUserId()
        call.resolve()
    }

    @objc func addTags(_ call: CAPPluginCall) {
        guard let tagsAny = call.getObject("tags") as? [String: Any] else {
            call.reject("tags is required")
            return
        }
        let tags = tagsAny.compactMapValues { ($0 as? String) }
        if tags.isEmpty {
            call.reject("tags must be a [String: Any] dictionary")
            return
        }
        implementation.addTags(tags) { success in
            if success {
                call.resolve()
            } else {
                call.reject("Failed to add tags")
            }
        }
    }

    @objc func removeTags(_ call: CAPPluginCall) {
        guard let tagsAny = call.getObject("tags") as? [String: Any] else {
            call.reject("tags is required")
            return
        }
        let tagKeys = tagsAny.keys.map { String($0) }
        if tagKeys.isEmpty {
            call.reject("tags must be a [String: Any] dictionary with keys to remove")
            return
        }
        implementation.removeTags(tagKeys) { success in
            if success {
                call.resolve()
            } else {
                call.reject("Failed to remove tags")
            }
        }
    }

    @objc func addTag(_ call: CAPPluginCall) {
        guard let tagKey = call.getString("tagKey") else {
            call.reject("tagKey is required")
            return
        }
        guard let tagValue = call.getString("tagValue") else {
            call.reject("tagValue is required")
            return
        }
        implementation.sendTag(tagKey, tagValue) { success in
            if success {
                call.resolve()
            } else {
                call.reject("Failed to add tag")
            }
        }
    }

    @objc func removeTag(_ call: CAPPluginCall) {
        guard let tag = call.getString("tag") else {
            call.reject("tag is required")
            return
        }
        implementation.removeTag(tag) { success in
            if success {
                call.resolve()
            } else {
                call.reject("Failed to remove tag")
            }
        }
    }

}
