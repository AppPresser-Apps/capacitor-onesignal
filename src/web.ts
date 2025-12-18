import { WebPlugin } from '@capacitor/core';

import type { CapOneSignalPlugin } from './definitions';

export class CapOneSignalWeb extends WebPlugin implements CapOneSignalPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
