#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "ZXingWidgetController.h"

@interface userclasses_ZXingNativeCallsImpl : UIViewController <ZXingDelegate> {
  NSString *resultsToDisplay;
  int status;
}

-(NSString*)getType;
-(void)scan;
-(NSString*)getResult;
-(int)getStatus;
-(BOOL)isSupported;
@end
