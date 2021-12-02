import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class IntraleMobileMercadopago {
  static const MethodChannel _channel = MethodChannel('mercadopago');

  static Future<String?> startPayment(
      {required String publicKey, required String preferenceId}) async {
    debugPrint("Ejecutando platformVersion");
    final String? version = await _channel.invokeMethod(
        'startPayment', // call the native function
        <String, dynamic>{
          "publicKey": publicKey,
          "preferenceId": preferenceId
        });
    debugPrint("Finalizando platformVersion");
    return version;
  }
}
