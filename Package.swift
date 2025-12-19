// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorOnesignal",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorOnesignal",
            targets: ["CapOneSignalPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/OneSignal/OneSignal-XCFramework.git", .upToNextMajor(from: "5.0.2"))
    ],
    targets: [
        .target(
            name: "CapOneSignalPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "OneSignalFramework", package: "OneSignal-XCFramework")
            ],
            path: "ios/Sources/CapOneSignalPlugin"),
        .testTarget(
            name: "CapOneSignalPluginTests",
            dependencies: ["CapOneSignalPlugin"],
            path: "ios/Tests/CapOneSignalPluginTests")
    ]
)