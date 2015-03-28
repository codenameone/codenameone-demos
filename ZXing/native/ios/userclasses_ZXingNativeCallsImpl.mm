#import "userclasses_ZXingNativeCallsImpl.h"
#import "QRCodeReaderOC.h"
#import "CodenameOne_GLViewController.h"

@implementation userclasses_ZXingNativeCallsImpl

-(NSString*)getType{
    return nil;
}

-(void)scan{
    dispatch_sync(dispatch_get_main_queue(), ^{
        status = 0;
        ZXingWidgetController *widController = [[ZXingWidgetController alloc] initWithDelegate:self showCancel:YES OneDMode:NO];

        NSMutableSet *readers = [[NSMutableSet alloc ] init];

        QRCodeReader* qrcodeReader = [[QRCodeReader alloc] init];
        [readers addObject:qrcodeReader];
        [qrcodeReader release];
        
        widController.readers = readers;
        [readers release];
    
        /*NSBundle *mainBundle = [NSBundle mainBundle];
         widController.soundToPlay =
         [NSURL fileURLWithPath:[mainBundle pathForResource:@"beep-beep" ofType:@"aiff"] isDirectory:NO];*/
        [[CodenameOne_GLViewController instance] presentModalViewController:widController animated:YES];
        [widController release];
    });
}

#pragma mark -
#pragma mark ZXingDelegateMethods

- (void)zxingController:(ZXingWidgetController*)controller didScanResult:(NSString *)result {
    resultsToDisplay = result;
    [resultsToDisplay retain];
    status = 1;
    [[CodenameOne_GLViewController instance] dismissModalViewControllerAnimated:NO];
}

- (void)zxingControllerDidCancel:(ZXingWidgetController*)controller {
    status = 2;
  [[CodenameOne_GLViewController instance] dismissModalViewControllerAnimated:YES];
}


-(NSString*)getResult{
    return resultsToDisplay;
}

-(int)getStatus{
    return status;
}

-(BOOL)isSupported{
    return YES;
}

@end
