// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Capacitor_onesignal",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "Capacitor_onesignal",
            targets: ["CapOneSignalPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "CapOneSignalPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapOneSignalPlugin"),
        .testTarget(
            name: "CapOneSignalPluginTests",
            dependencies: ["CapOneSignalPlugin"],
            path: "ios/Tests/CapOneSignalPluginTests")
    ]
)