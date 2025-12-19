# capacitor-onesignal

Capactior plugin for OneSignal Push notifications for Capcitor 8+ Swift Package Manager

THIS PLUGIN IS IN DEVELOPMENT. USE AT YOUR OWN RISK! 

## Install

```bash
npm install capacitor-onesignal
npx cap sync
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`requestPermission(...)`](#requestpermission)
* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: { appId: string; }) => Promise<void>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ appId: string; }</code> |

--------------------


### requestPermission(...)

```typescript
requestPermission(options?: { fallbackToSettings?: boolean | undefined; } | undefined) => Promise<{ accepted: boolean; }>
```

| Param         | Type                                           |
| ------------- | ---------------------------------------------- |
| **`options`** | <code>{ fallbackToSettings?: boolean; }</code> |

**Returns:** <code>Promise&lt;{ accepted: boolean; }&gt;</code>

--------------------

</docgen-api>

## Android setup

Make sure your Android host app includes the runtime notification permission (Android 13+) and that native dependencies are installed:

1. Add the POST_NOTIFICATIONS permission to your `AndroidManifest.xml` (required for Android 13+):

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

