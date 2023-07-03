import { NativeModules, Platform } from 'react-native';

const FolderSelector = NativeModules.FolderSelector;

export function showFolderSelector(a: number, b: number): Promise<number> {
  return FolderSelector.showFolderSelector(a, b);
}
