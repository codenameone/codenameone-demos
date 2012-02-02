#import "com_codename1_nativedemo_NativeCallsImpl.h"
#import <UIKit/UIKit.h>

@implementation com_codename1_nativedemo_NativeCallsImpl

-(void)testVoid{
}

-(void)testMultipleArgs:(int)param param1:(char)param1 param2:(int)param2 param3:(short)param3 param4:(double)param4 param5:(float)param5 param6:(BOOL)param6 param7:(NSString*)param7 param8:(NSData*)param8{
}

-(void*)createNativeButton:(NSString*)param{
    UIButton *myButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [myButton setTitle:param forState:UIControlStateNormal];
    return myButton;
}

-(NSData*)getArrayData{
    return nil;
}

-(BOOL)isSupported{
    return YES;
}

@end
