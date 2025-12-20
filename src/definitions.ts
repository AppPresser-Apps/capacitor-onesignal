export interface CapOneSignalPlugin {
  initialize(options: { appId: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
  setExternalUserId(options: { userId: string }): Promise<void>;
  clearExternalUserId(): Promise<void>;
}
