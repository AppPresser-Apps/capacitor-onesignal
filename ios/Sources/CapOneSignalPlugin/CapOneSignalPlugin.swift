import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapOneSignalPlugin)
public class CapOneSignalPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapOneSignalPlugin"
    public let jsName = "CapOneSignal"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermission", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapOneSignal()

    @objc func initialize(_ call: CAPPluginCall) {
        guard let appId = call.getString("appId") else {
            call.reject("appId is required")
            return
        }
        implementation.initialize(appId)
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

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
