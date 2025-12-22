export interface CapOneSignalPlugin {
  initialize(options: { appID: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
  setLogLevel(options: { level: string }): Promise<void>;
  setExternalUserId(options: { userID: string }): Promise<void>;
  clearExternalUserId(): Promise<void>;
} 
