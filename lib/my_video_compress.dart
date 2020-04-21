import 'dart:async';

import 'package:flutter/services.dart';

class MyVideoCompress {
  static const MethodChannel _channel =
      const MethodChannel('plugins.flutter.io/my_video_compress');

  static Future<String> videoCompress(String srcPath) async {
    String path =
        await _channel.invokeMethod<String>("videoCompress", <String, dynamic>{
      'srcPath': srcPath,
    });
    return path;
  }
}
