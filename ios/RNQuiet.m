#import "RNQuiet.h"

#import <QuietModemKit/QuietModemKit.h>

@implementation RNQuiet

RCT_EXPORT_MODULE()

static QMFrameTransmitter *tx;
static QMFrameReceiver *rx;

- (NSArray<NSString *> *)supportedEvents {
    return @[@"onMessageReceived"];
}

void stop() {
    if (rx != nil) {
        [rx close];
        rx = nil;
    }
    if (tx != nil) {
        [tx close];
        tx = nil;
    }
}

RCT_EXPORT_METHOD(start: (NSString *)key)
{
    stop();
    
    tx = [[QMFrameTransmitter alloc] initWithConfig:[[QMTransmitterConfig alloc] initWithKey:key]];

    [[AVAudioSession sharedInstance] requestRecordPermission:^(BOOL granted){
        rx = [[QMFrameReceiver alloc] initWithConfig:[[QMReceiverConfig alloc] initWithKey:key]];
        [rx setReceiveCallback:^(NSData *frame){
            printf("%s\n", [frame bytes]);
            [self sendEventWithName:@"onMessageReceived" body:[[NSString alloc] initWithData:frame encoding:NSUTF8StringEncoding]];
        }];
    }];

    CFRunLoopRun();
}

RCT_EXPORT_METHOD(send: (NSString *)msg)
{
    if (tx != nil) {
        NSData *frame = [msg dataUsingEncoding:NSUTF8StringEncoding];
        
        [tx send:frame];
        
        CFRunLoopRun();
    }
}

RCT_EXPORT_METHOD(stop)
{
    stop();
}

@end
