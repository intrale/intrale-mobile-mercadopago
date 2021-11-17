import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:intrale_mobile_mercadopago/intrale_mobile_mercadopago.dart';

void main() {
  const MethodChannel channel = MethodChannel('mercadopago');

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
    expect(await IntraleMobileMercadopago.platformVersion, '42');
  });
}
