import { NativeModules, Platform } from 'react-native';

const FolderSelectorAndroid = NativeModules.FolderSelectorAndroid;

export function showFolderSelector() {
  return FolderSelectorAndroid.showFolderSelector();
}
