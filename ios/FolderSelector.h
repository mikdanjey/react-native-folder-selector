
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNFolderSelectorSpec.h"

@interface FolderSelector : NSObject <NativeFolderSelectorSpec>
#else
#import <React/RCTBridgeModule.h>

@interface FolderSelector : NSObject <RCTBridgeModule>
#endif

@end
