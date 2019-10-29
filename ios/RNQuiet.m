#import "RNQuiet.h"

#import <AFNetworking/AFNetworking.h>
#import <QuietModemKit/QuietModemKit.h>

@implementation RNQuiet

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(startQuietTx)
{
    
    QMTransmitterConfig *txConf = [[QMTransmitterConfig alloc] initWithKey:@"ultrasonic-experimental"];

    QMFrameTransmitter *tx = [[QMFrameTransmitter alloc] initWithConfig:txConf];

    NSString *frame_str = @"Hello, World!";
    NSData *frame = [frame_str dataUsingEncoding:NSUTF8StringEncoding];
    [tx send:frame];

    CFRunLoopRun();

    [tx close];
    NSLog(@"done");
}

static QMFrameReceiver *rx;

void (^recv_callback)(NSData*) = ^(NSData *frame){
    printf("%s\n", [frame bytes]);
    NSLog(@"got a result");
};

void (^request_callback)(BOOL) = ^(BOOL granted){
    QMReceiverConfig *rxConf = [[QMReceiverConfig alloc] initWithKey:@"ultrasonic-experimental"];
    rx = [[QMFrameReceiver alloc] initWithConfig:rxConf];
    [rx setReceiveCallback:recv_callback];
};

RCT_EXPORT_METHOD(startQuietRx)
{
    NSLog(@"starting");
    [[AVAudioSession sharedInstance] requestRecordPermission:request_callback];

    CFRunLoopRun();

    if (rx != nil) {
        [rx close];
    }
    NSLog(@"closed");
}

@end
