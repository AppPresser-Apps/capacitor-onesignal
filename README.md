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
* [`setLogLevel(...)`](#setloglevel)
* [`setExternalUserId(...)`](#setexternaluserid)
* [`clearExternalUserId()`](#clearexternaluserid)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: { appID: string; }) => Promise<void>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ appID: string; }</code> |

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


### setLogLevel(...)

```typescript
setLogLevel(options: { level: string; }) => Promise<void>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ level: string; }</code> |

--------------------


### setExternalUserId(...)

```typescript
setExternalUserId(options: { userID: string; }) => Promise<void>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ userID: string; }</code> |

--------------------


### clearExternalUserId()

```typescript
clearExternalUserId() => Promise<void>
```

--------------------

</docgen-api>

