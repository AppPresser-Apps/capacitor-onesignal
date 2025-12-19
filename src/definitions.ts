export interface CapOneSignalPlugin {
  initialize(options: { appId: string }): Promise<void>;
  requestPermission(options?: { fallbackToSettings?: boolean }): Promise<{ accepted: boolean }>;
}
