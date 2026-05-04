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
* [`addTags(...)`](#addtags)
* [`removeTags(...)`](#removetags)
* [`addTag(...)`](#addtag)
* [`removeTag(...)`](#removetag)
* [`addListener(string, ...)`](#addlistenerstring-)
* [Interfaces](#interfaces)

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


### addTags(...)

```typescript
addTags(options: { tags: { [key: string]: string; }; }) => Promise<void>
```

| Param         | Type                                               |
| ------------- | -------------------------------------------------- |
| **`options`** | <code>{ tags: { [key: string]: string; }; }</code> |

--------------------


### removeTags(...)

```typescript
removeTags(options: { tags: string[]; }) => Promise<void>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ tags: string[]; }</code> |

--------------------


### addTag(...)

```typescript
addTag(options: { tag: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ tag: string; }</code> |

--------------------


### removeTag(...)

```typescript
removeTag(options: { tag: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ tag: string; }</code> |

--------------------


### addListener(string, ...)

```typescript
addListener(eventName: string, listenerFunc: (...args: any[]) => void) => Promise<PluginListenerHandle>
```

Listen for notification clicks.
When a user taps a push notification containing deeplink data,
the NotificationClickData will be emitted to all registered listeners.

| Param              | Type                                     |
| ------------------ | ---------------------------------------- |
| **`eventName`**    | <code>string</code>                      |
| **`listenerFunc`** | <code>(...args: any[]) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

