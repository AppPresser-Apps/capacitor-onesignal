import { WebPlugin } from '@capacitor/core';

import type { CapOneSignalPlugin, NotificationClickData } from './definitions';
import type { PluginListenerHandle } from '@capacitor/core';

export class CapOneSignalWeb extends WebPlugin implements CapOneSignalPlugin {

  private oneSignalWindow?: Window & { OneSignal?: any };

  async requestPermission(): Promise<{ accepted: boolean }> {
    console.log('OneSignal Web requestPermission called');
    if (typeof Notification !== 'undefined' && Notification.requestPermission) {
      const result = await Notification.requestPermission();
      return { accepted: result === 'granted' };
    }
    return { accepted: false };
  }

  async initialize(options: { appID: string }): Promise<void> {
    console.log('OneSignal Web initialize called with appID:', options.appID);
    
    this.oneSignalWindow = window as Window & { OneSignal?: any };
    
    // Check if OneSignal Web SDK is loaded
    if (this.oneSignalWindow.OneSignal) {
      console.log('OneSignal Web SDK detected, initializing...');
      
      // Initialize OneSignal Web SDK
      this.oneSignalWindow.OneSignal = this.oneSignalWindow.OneSignal || [];
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.init({
          appId: options.appID,
        });
        
        // Register notification click handler
        this.oneSignalWindow!.OneSignal.addListener('notificationClick', (notification: any) => {
          console.log('OneSignal Web: notificationClick event received', notification);
          
          const clickData: NotificationClickData = {
            notificationId: notification.notificationId,
            launchURL: notification.url,
            additionalData: notification.additionalData || {},
          };
          
          // Notify Capacitor listeners
          this.notifyListeners('notificationClicked', clickData);
        });
      });
    } else {
      console.warn('OneSignal Web SDK not found. Make sure to include the OneSignal Web SDK script in your index.html');
      console.warn('Example: <script src="https://cdn.onesignal.com/sdks/web/v16/OneSignalSDK.js" async></script>');
    }
    
    // Listen for service worker messages as fallback
    if (typeof navigator !== 'undefined' && navigator.serviceWorker) {
      navigator.serviceWorker.addEventListener('message', (event) => {
        console.log('Service Worker message received:', event.data);
        
        if (event.data && event.data.type === 'notificationClick') {
          const clickData: NotificationClickData = {
            notificationId: event.data.notificationId,
            launchURL: event.data.url,
            additionalData: event.data.additionalData || {},
          };
          
          this.notifyListeners('notificationClicked', clickData);
        }
      });
    }
  }

  async setLogLevel(options: { level: string }): Promise<void> {
    console.log('OneSignal Web setLogLevel called with level:', options.level);
    // Web-specific setLogLevel logic (no-op for web unless using OneSignal Web SDK)
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.Debug.setLogLevel(options.level.toUpperCase());
      });
    }
  }

  async setExternalUserId(options: { userID: string }): Promise<void> {
    console.log('OneSignal Web setExternalUserId called with userId:', options.userID);
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.login(options.userID);
      });
    }
  }

  async clearExternalUserId(): Promise<void> {
    console.log('OneSignal Web clearExternalUserId called');
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.logout();
      });
    }
  }

  async addTags(options: { tags: { [key: string]: string } }): Promise<void> {
    console.log('OneSignal Web addTags called with tags:', options.tags);
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.User.addTags(options.tags);
      });
    }
  }

  async removeTags(options: { tags: string[] }): Promise<void> {
    console.log('OneSignal Web removeTags called with tags:', options.tags);
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.User.removeTags(options.tags);
      });
    }
  }

  async addTag(options: { tag: string }): Promise<void> {
    console.log('OneSignal Web addTag called:', options.tag);
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        // Parse tag format "key=value" or just "key"
        const parts = options.tag.split('=');
        if (parts.length === 2) {
          this.oneSignalWindow!.OneSignal.User.addTags({ [parts[0]]: parts[1] });
        } else {
          this.oneSignalWindow!.OneSignal.User.addTags({ [options.tag]: true });
        }
      });
    }
  }

  async removeTag(options: { tag: string }): Promise<void> {
    console.log('OneSignal Web removeTag called:', options.tag);
    if (this.oneSignalWindow?.OneSignal) {
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.User.removeTags([options.tag]);
      });
    }
  }

  async addListener(
    eventName: string,
    listenerFunc: (...args: any[]) => void,
  ): Promise<PluginListenerHandle> {
    console.log('CapOneSignal Web: addListener registered for event:', eventName);
    
    const handle = await super.addListener(eventName, listenerFunc);
    
    // For notificationClicked, ensure we're listening to OneSignal events
    if (eventName === 'notificationClicked' && this.oneSignalWindow?.OneSignal) {
      console.log('CapOneSignal Web: Setting up OneSignal notification click listener');
      
      this.oneSignalWindow.OneSignal.push(() => {
        this.oneSignalWindow!.OneSignal.addListener('notificationClick', (notification: any) => {
          console.log('OneSignal Web: notificationClick event received', notification);
          
          const clickData: NotificationClickData = {
            notificationId: notification.notificationId,
            launchURL: notification.url,
            additionalData: notification.additionalData || {},
          };
          
          // Notify Capacitor listeners
          this.notifyListeners('notificationClicked', clickData);
        });
      });
    }
    
    return handle;
  }
}