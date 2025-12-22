import { WebPlugin } from '@capacitor/core';

import type { CapOneSignalPlugin } from './definitions';

export class CapOneSignalWeb extends WebPlugin implements CapOneSignalPlugin {

  async requestPermission(): Promise<{ accepted: boolean }> {
    console.log('OneSignal Web requestPermission called');
    if (typeof Notification !== 'undefined' && Notification.requestPermission) {
      const result = await Notification.requestPermission();
      return { accepted: result === 'granted' };
    }
    return { accepted: false };
  }

  async initialize(options: { appID: string; }): Promise<void> {
    console.log('OneSignal Web initialize called with appID:', options.appID);
    // Web-specific initialization logic can be added here
  }

  async setLogLevel(options: { level: string; }): Promise<void> {
    console.log('OneSignal Web setLogLevel called with level:', options.level);
    // Web-specific setLogLevel logic can be added here (no-op for web)
  }

  async setExternalUserId(options: { userID: string; }): Promise<void> {
    console.log('OneSignal Web setExternalUserId called with userId:', options.userID);
    // Web-specific setExternalUserId logic can be added here
  }
  
  async clearExternalUserId(): Promise<void> {
    console.log('OneSignal Web clearExternalUserId called');
    // Web-specific clearExternalUserId logic can be added here
  }
}
