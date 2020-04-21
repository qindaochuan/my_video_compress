import 'dart:async';

import 'package:flutter/services.dart';

class MyVideoCompress {
  static const MethodChannel _channel =
      const MethodChannel('my_video_compress');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
