import Capacitor
import Foundation
import OneSignalFramework
import UIKit

@objc public class CapOneSignal: NSObject, OSNotificationClickListener {

    @objc public static let shared = CapOneSignal()

    // Store weak reference to plugin for notifying JavaScript
    weak var plugin: CAPPlugin?

    public override init() { super.init() }

    @objc public func setPlugin(_ plugin: CAPPlugin) {
        self.plugin = plugin
    }

    @objc public func initialize(_ appID: String, plugin: CAPPlugin? = nil) {
        print("Initializing OneSignal with App ID: \(appID)")
        OneSignal.initialize(appID)

        // Set plugin reference if provided
        if let plugin = plugin {
            self.plugin = plugin
            print("CapOneSignal: Plugin reference set")
        }

        OneSignal.Notifications.addClickListener(CapOneSignal.shared)
    }

    @objc public func handleLaunchOptions(_ launchOptions: [UIApplication.LaunchOptionsKey: Any]?) {
        if let remoteNotif = launchOptions?[.remoteNotification] as? [String: Any] {
            print("DEBUG: App launched via Notification Tap!")

            // Build click data object for cold start
            var clickData: [String: Any] = [:]

            // Extract notification ID
            if let notifId = remoteNotif["id"] as? String {
                clickData["notificationId"] = notifId
            }

            if let custom = remoteNotif["custom"] as? [String: Any] {
                // Extract launch URL from 'custom' -> 'u'
                if let launchURL = custom["u"] as? String {
                    clickData["launchURL"] = launchURL

                    print("DEBUG: Found Launch URL in Cold Start: \(launchURL)")

                    // Open URL after small delay
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                        if let url = URL(string: launchURL) {
                            UIApplication.shared.open(url, options: [:], completionHandler: nil)
                        }
                    }
                }

                // Extract additionalData from 'custom' -> 'a'
                if let additionalDataAny = custom["a"] as? [AnyHashable: Any] {
                    var convertedData: [String: Any] = [:]
                    for (key, value) in additionalDataAny {
                        if let stringKey = key as? String {
                            convertedData[stringKey] = value
                        }
                    }
                    clickData["additionalData"] = convertedData
                    print("DEBUG: Found Additional Data in Cold Start: \(convertedData)")
                }
            }

            // Notify JavaScript listeners for cold start clicks
            if let plugin = self.plugin {
                print("DEBUG: Notifying JavaScript listeners of cold start click")
                plugin.notifyListeners("notificationClicked", data: clickData, retainUntilConsumed: true)
            }
        }
    }

    @objc public func onClick(event: OSNotificationClickEvent) {
        print("OneSignal: Notification clicked!")

        // Build click data object
        var clickData: [String: Any] = [:]

        // Notification ID
        clickData["notificationId"] = event.notification.notificationId
        print("OneSignal: Notification ID: \(event.notification.notificationId)")

        // Launch URL
        if let launchURL = event.notification.launchURL {
            clickData["launchURL"] = launchURL
            print("OneSignal: Launch URL: \(launchURL)")

            DispatchQueue.main.async {
                if let url = URL(string: launchURL) {
                    UIApplication.shared.open(url, options: [:])
                }
            }
        }

        // Additional Data - THIS IS THE KEY PART for your custom data
        if let additionalData = event.notification.additionalData {
            print("OneSignal: Additional Data found: \(additionalData)")
            // Convert [AnyHashable: Any] to [String: Any] for proper JS serialization
            var convertedData: [String: Any] = [:]
            for (key, value) in additionalData {
                if let stringKey = key as? String {
                    convertedData[stringKey] = value
                }
            }
            clickData["additionalData"] = convertedData
            print("OneSignal: Converted additionalData: \(convertedData)")
        } else {
            print("OneSignal: No additionalData in notification")
        }

        // Notify JavaScript listeners
        if let plugin = self.plugin {
            print("OneSignal: Notifying JavaScript listeners")
            plugin.notifyListeners("notificationClicked", data: clickData, retainUntilConsumed: true)
        } else {
            print("OneSignal: WARNING - plugin reference is nil, cannot notify JavaScript")
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
        completion(true)
    }

    public func removeTags(_ tags: [String], completion: @escaping (Bool) -> Void) {
        print("Removing tags: \(tags)")
        OneSignal.User.removeTags(tags)
        completion(true)
    }

    public func sendTag(_ key: String, _ value: String, completion: @escaping (Bool) -> Void) {
        print("Sending tag: \(key) = \(value)")
        OneSignal.User.addTags([key: value])
        completion(true)
    }

    public func removeTag(_ key: String, completion: @escaping (Bool) -> Void) {
        print("Removing tag: \(key)")
        OneSignal.User.removeTags([key])
        completion(true)
    }

}
