package ar.com.intrale.mobile.mercadopago;


import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.logging.Logger;

import com.mercadopago.android.px.core.MercadoPagoCheckout;

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
    LOGGER.info("start onAttachedToEngine");
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "mercadopago");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
    LOGGER.info("end onAttachedToEngine");
  }

  @Override @SuppressWarnings("unchecked")
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    LOGGER.info("start onMethodCall");
    if (call.method.equals("startPayment")) {
      LOGGER.info("call startPayment");
      startPayment(call, result);
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
    LOGGER.info("end onMethodCall");
  }

  @Override @SuppressWarnings("unchecked")
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    LOGGER.info("start onDetachedFromEngine");
    channel.setMethodCallHandler(null);
    LOGGER.info("end onDetachedFromEngine");
  }

  @SuppressWarnings("unchecked")
  private void startPayment(MethodCall call, Result result) {
    LOGGER.info("start startPayment");
    HashMap<String, Object> arguments = (HashMap<String, Object>) call.arguments;
    String publicKey = (String) arguments.get(MERCADO_PAGO_PUBLIC_KEY_ARG);
    String preferenceId = (String) arguments.get(MERCADO_PAGO_PREFERENCE_ID_ARG);
    new MercadoPagoCheckout.Builder(publicKey, preferenceId)
            .build().startPayment(context, MERCADO_PAGO_REQUEST_CODE);
    LOGGER.info("end startPayment");
  }
  
  @Override
  public void  onAttachedToActivity(ActivityPluginBinding binding ) {
    LOGGER.info("start onAttachedToActivity");
    activity = binding.getActivity();
    LOGGER.info("end onAttachedToActivity");
	}
  
  @Override
  public void  onDetachedFromActivity() {
      //TODO("Not yet implemented")
  }
  
  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
      //TODO("Not yet implemented")
  }
  
  @Override
  public void  onDetachedFromActivityForConfigChanges() {
	    //TODO("Not yet implemented")
  }
}
