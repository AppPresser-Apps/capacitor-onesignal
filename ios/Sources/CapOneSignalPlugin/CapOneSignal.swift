import Foundation

@objc public class CapOneSignal: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
