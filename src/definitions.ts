export interface CapOneSignalPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
