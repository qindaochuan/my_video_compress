import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_video_compress/my_video_compress.dart';

void main() {
  const MethodChannel channel = MethodChannel('my_video_compress');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MyVideoCompress.platformVersion, '42');
  });
}
