import Foundation
import OneSignalFramework

// NOTE: If you encounter "Invalid redeclaration of 'CapOneSignal'" error, please check for duplicate CapOneSignal class definitions elsewhere in the project.

@objc public class CapOneSignal: NSObject {

    @objc public func initialize(_ appID: String) {
        print("Initializing OneSignal with App ID: \(appID)")
        OneSignal.initialize(appID)
    }

    @objc public func setLogLevel(_ level: String) {
        let upper = level.uppercased()
        switch upper {
        case "VERBOSE":
            OneSignal.Debug.setLogLevel(.LL_VERBOSE)
        case "DEBUG":
            OneSignal.Debug.setLogLevel(.LL_DEBUG)
        case "INFO":
            OneSignal.Debug.setLogLevel(.LL_INFO)
        case "WARN", "WARNING":
            OneSignal.Debug.setLogLevel(.LL_WARN)
        case "ERROR":
            OneSignal.Debug.setLogLevel(.LL_ERROR)
        case "NONE":
            OneSignal.Debug.setLogLevel(.LL_NONE)
        default:
            print("Unknown log level: \(level), defaulting to INFO")
            OneSignal.Debug.setLogLevel(.LL_INFO)
        }
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
