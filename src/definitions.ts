import type { PluginListenerHandle } from '@capacitor/core';

export interface NotificationClickData {
  notificationId?: string;
  launchURL?: string;
  additionalData?: { [key: string]: any };
}

export interface NotificationClickListener {
  (data: NotificationClickData): void;
}

export interface CapOneSignalPlugin {
  initialize(options: { appID: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
  setLogLevel(options: { level: string }): Promise<void>;
  setExternalUserId(options: { userID: string }): Promise<void>;
  clearExternalUserId(): Promise<void>;
  addTags(options: { tags: { [key: string]: string } }): Promise<void>;
  removeTags(options: { tags: string[] }): Promise<void>;
  addTag(options: { tag: string }): Promise<void>;
  removeTag(options: { tag: string }): Promise<void>;

  /**
   * Listen for notification clicks.
   * When a user taps a push notification containing deeplink data,
   * the NotificationClickData will be emitted to all registered listeners.
   */
  addListener(
    eventName: string,
    listenerFunc: (...args: any[]) => void,
  ): Promise<PluginListenerHandle>;
}