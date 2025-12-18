import { registerPlugin } from '@capacitor/core';

import type { CapOneSignalPlugin } from './definitions';

const CapOneSignal = registerPlugin<CapOneSignalPlugin>('CapOneSignal', {
  web: () => import('./web').then((m) => new m.CapOneSignalWeb()),
});

export * from './definitions';
export { CapOneSignal };
