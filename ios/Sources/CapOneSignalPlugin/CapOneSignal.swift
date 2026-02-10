import Foundation
import OneSignalFramework
import UIKit

@objc public class CapOneSignal: NSObject, OSNotificationClickListener {
    
    @objc public static let shared = CapOneSignal()
    
    public override init() { super.init() }
    
    @objc public func initialize(_ appID: String) {
        print("Initializing OneSignal with App ID: \(appID)")
        OneSignal.initialize(appID)
        
        OneSignal.Notifications.addClickListener(CapOneSignal.shared)
    }
    
    @objc public func handleLaunchOptions(_ launchOptions: [UIApplication.LaunchOptionsKey: Any]?) {
        if let remoteNotif = launchOptions?[.remoteNotification] as? [String: Any] {
            print("DEBUG: App launched via Notification Tap!")
            
            // OneSignal stores the launch URL in 'custom' -> 'u'
            if let custom = remoteNotif["custom"] as? [String: Any],
               let launchURL = custom["u"] as? String,
               let url = URL(string: launchURL) {
                
                print("DEBUG: Found Launch URL in Cold Start: \(launchURL)")
                
                // Open it immediately
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { // Small delay to let OS settle
                    UIApplication.shared.open(url, options: [:], completionHandler: nil)
                }
            }
        }
    }
    
    @objc public func onClick(event: OSNotificationClickEvent) {
        if let launchURL = event.notification.launchURL, let url = URL(string: launchURL) {
            print("OneSignal: Cold Start/Background URL: \(launchURL)")
            DispatchQueue.main.async {
                UIApplication.shared.open(url, options: [:])
            }
        }
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
    
    public func addTags(_ tags: [String: String], completion: @escaping (Bool) -> Void) {
        print("Adding tags: \(tags)")
        OneSignal.User.addTags(tags)
    }
    
    public func removeTags(_ tags: [String], completion: @escaping (Bool) -> Void) {
        print("Removing tags: \(tags)")
        OneSignal.User.removeTags(tags)
    }
    
    public func sendTag(_ key: String, _ value: String, completion: @escaping (Bool) -> Void) {
        print("Sending tag: \(key) = \(value)")
        OneSignal.User.addTags([key: value])
    }
    
    public func removeTag(_ key: String, completion: @escaping (Bool) -> Void) {
        print("Removing tag: \(key)")
        OneSignal.User.removeTags([key])
    }
    
}
