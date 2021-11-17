import 'dart:async';

import 'package:flutter/services.dart';

class IntraleMobileMercadopago {
  static const MethodChannel _channel = MethodChannel('mercadopago');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
