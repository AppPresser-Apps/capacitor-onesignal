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

  async initialize(options: { appId: string; }): Promise<void> {
    console.log('OneSignal Web initialize called with appId:', options.appId);
    // Web-specific initialization logic can be added here
  }
}
