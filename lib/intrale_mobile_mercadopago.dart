import 'dart:async';

import 'package:flutter/services.dart';

class IntraleMobileMercadopago {
  static const MethodChannel _channel = MethodChannel('mercadopago');

  static Future<String?> startPayment({required String publicKey, required String preferenceId}) async {
    print("Ejecutando platformVersion");

    final String? version = await _channel.invokeMethod(
        'startPayment', // call the native function
        <String, dynamic>{
          "publicKey": publicKey,
          "preferenceId": preferenceId
        });

    print("Finalizando platformVersion");
    return version;
  }
}
