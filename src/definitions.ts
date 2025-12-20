export interface CapOneSignalPlugin {
  initialize(options: { appID: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
  setExternalUserId(options: { userID: string }): Promise<void>;
  clearExternalUserId(): Promise<void>;
}
