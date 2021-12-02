package ar.com.intrale.mobile.mercadopago;


import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.HashMap;

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
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "mercadopago");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override @SuppressWarnings("unchecked")
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("startPayment")) {
      startPayment(call, result);
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  @Override @SuppressWarnings("unchecked")
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @SuppressWarnings("unchecked")
  private void startPayment(MethodCall call, Result result) {
      HashMap<String, Object> arguments = (HashMap<String, Object>) call.arguments;
      String publicKey = (String) arguments.get(MERCADO_PAGO_PUBLIC_KEY_ARG);
      String preferenceId = (String) arguments.get(MERCADO_PAGO_PREFERENCE_ID_ARG);
      new MercadoPagoCheckout.Builder(publicKey, preferenceId)
              .build().startPayment(context, MERCADO_PAGO_REQUEST_CODE);
    }
  
  @Override
  public void  onAttachedToActivity(ActivityPluginBinding binding ) {
	    activity = binding.getActivity();
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
