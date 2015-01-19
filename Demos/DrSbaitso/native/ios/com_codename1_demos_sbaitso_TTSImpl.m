#import "com_codename1_demos_sbaitso_TTSImpl.h"
#import <AVFoundation/AVFoundation.h>

@implementation com_codename1_demos_sbaitso_TTSImpl

-(void)say:(NSString*)param{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    AVSpeechSynthesisVoice *voice = [AVSpeechSynthesisVoice voiceWithLanguage:@"en-GB"];
    AVSpeechUtterance *utterance = [AVSpeechUtterance speechUtteranceWithString:param];
    AVSpeechSynthesizer *syn = [[[AVSpeechSynthesizer alloc] init]autorelease];
    utterance.rate = 0;
    utterance.voice = voice;
    [syn speakUtterance:utterance];
    [pool release];
}

-(BOOL)isSupported{
    return YES;
}

@end
