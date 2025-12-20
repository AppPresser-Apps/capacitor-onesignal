import Foundation
import OneSignalFramework

@objc public class CapOneSignal: NSObject {

    @objc public func initialize(_ appId: String) {
        print("Initializing OneSignal with App ID: \(appId)")
        //OneSignal.Debug.setLogLevel(.LL_VERBOSE)
        OneSignal.initialize(appId)
    }

    public func requestPermission(completion: @escaping (Bool) -> Void) {
        print("Requesting push notification permission")
        OneSignal.Notifications.requestPermission({ accepted in
            print("User accepted notifications: \(accepted)")
            completion(accepted)
        })
    }

    public func setExternalUserId(_ userID: String) {
        print("Setting external user ID to: \(userID)")
        OneSignal.login(userID)
    }

    public func clearExternalUserId() {
        print("Clearing external user ID")
        OneSignal.logout()
    }

}
