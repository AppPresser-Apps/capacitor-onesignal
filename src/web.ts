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

  async addTags(options: { tags: { [key: string]: string }; }): Promise<void> {
    console.log('OneSignal Web addTags called with tags:', options.tags);
    // Web-specific addTags logic can be added here
  }

  async removeTags(options: { tags: string[]; }): Promise<void> {
    console.log('OneSignal Web removeTags called with tags:', options.tags);
    // Web-specific removeTags logic can be added here
  }

  async addTag(options: { tag: string; }): Promise<void> {
    console.log('OneSignal Web sendTag called: ', options.tag);
    // Web-specific sendTag logic can be added here
  }

  async removeTag(options: { tag: string; }): Promise<void> {
    console.log('OneSignal Web sendTags called: ', options.tag);
    // Web-specific sendTags logic can be added here
  }
}
