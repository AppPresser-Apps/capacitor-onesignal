export interface CapOneSignalPlugin {
  initialize(options: { appId: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
  setExternalUserId(options: { userID: string }): Promise<void>;
  clearExternalUserId(): Promise<void>;
}
