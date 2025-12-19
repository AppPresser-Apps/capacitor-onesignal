# capacitor--onesignal

Capactior plugin for OneSignal Push notifications

## Install

```bash
npm install capacitor--onesignal
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


### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>

## Android setup

Make sure your Android host app includes the OneSignal SDK and the runtime notification permission (Android 13+):

1. In your host app's `build.gradle` (app module) add the OneSignal dependency:

```groovy
implementation 'com.onesignal:OneSignal:4.9.4'
```

2. Add the POST_NOTIFICATIONS permission to your `AndroidManifest.xml` (required for Android 13+):

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

3. If you get a Gradle SDK error when building locally, ensure `local.properties` contains `sdk.dir=/path/to/Android/sdk` or set the `ANDROID_HOME` environment variable.

