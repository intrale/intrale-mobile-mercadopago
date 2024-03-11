package ar.com.intrale.mobile.mercadopago;


import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.logging.Logger;

import com.mercadopago.android.px.core.MercadoPagoCheckout;

import com.mercadopago.android.px.internal.callbacks.CallbackHolder;
import com.mercadopago.android.px.internal.callbacks.PaymentCallback;
import com.mercadopago.android.px.internal.callbacks.PaymentDataCallback;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentData;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import io.flutter.embedding.engine.plugins.activity.ActivityAware;

/** MercadopagoPlugin */
public class MercadopagoPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static final int MERCADO_PAGO_REQUEST_CODE = 11;
  public static final String MERCADO_PAGO_PUBLIC_KEY_ARG = "publicKey";
  public static final String MERCADO_PAGO_PREFERENCE_ID_ARG = "preferenceId";

  private Context context;
  private Activity activity;

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override @SuppressWarnings("unchecked")
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    LOGGER.info("MercadopagoPlugin: start onAttachedToEngine");
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "mercadopago");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
    LOGGER.info("MercadopagoPlugin: end onAttachedToEngine");
  }

  @Override @SuppressWarnings("unchecked")
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    LOGGER.info("MercadopagoPlugin: start onMethodCall");
    if (call.method.equals("startPayment")) {
      LOGGER.info("MercadopagoPlugin: call startPayment");
      startPayment(call, result);
      //result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
    LOGGER.info("MercadopagoPlugin: end onMethodCall");
  }

  @SuppressWarnings("unchecked")
  private void startPayment(MethodCall call, Result result) {
    LOGGER.info("MercadopagoPlugin: start startPayment");

    HashMap<String, Object> arguments = (HashMap<String, Object>) call.arguments;
    String publicKey = (String) arguments.get(MERCADO_PAGO_PUBLIC_KEY_ARG);
    String preferenceId = (String) arguments.get(MERCADO_PAGO_PREFERENCE_ID_ARG);
    LOGGER.info("MercadopagoPlugin: Build Mercado pago => publicKey:" + publicKey + ", preferenceId:" + preferenceId);

    IntraleMercadoPagoCheckout checkout = new IntraleMercadoPagoCheckout(publicKey, preferenceId);
    checkout.startPayment(context, MERCADO_PAGO_REQUEST_CODE, result);

    LOGGER.info("MercadopagoPlugin: end startPayment");
  }

  @Override @SuppressWarnings("unchecked")
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    LOGGER.info("MercadopagoPlugin: start onDetachedFromEngine");
    channel.setMethodCallHandler(null);
    LOGGER.info("MercadopagoPlugin: end onDetachedFromEngine");
  }

  @Override
  public void  onAttachedToActivity(ActivityPluginBinding binding ) {
    LOGGER.info("MercadopagoPlugin: start onAttachedToActivity");
    activity = binding.getActivity();
    LOGGER.info("MercadopagoPlugin: end onAttachedToActivity");
	}
  
  @Override
  public void  onDetachedFromActivity() {
    LOGGER.info("MercadopagoPlugin: onDetachedFromActivity");
  }
  
  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    LOGGER.info("MercadopagoPlugin: onReattachedToActivityForConfigChanges");
  }
  
  @Override
  public void  onDetachedFromActivityForConfigChanges() {
    LOGGER.info("MercadopagoPlugin: onDetachedFromActivityForConfigChanges");
  }
}
