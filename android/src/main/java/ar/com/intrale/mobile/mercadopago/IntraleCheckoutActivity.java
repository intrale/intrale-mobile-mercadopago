package ar.com.intrale.mobile.mercadopago;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.mercadopago.android.px.internal.features.checkout.CheckoutActivity;

import java.util.logging.Logger;

import io.flutter.plugin.common.MethodChannel;

public class IntraleCheckoutActivity extends CheckoutActivity {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static MethodChannel.Result result;

    public static Intent getIntent(@NonNull Context context, @NonNull MethodChannel.Result result) {
        LOGGER.info("IntraleCheckoutActivity: getIntent");
        IntraleCheckoutActivity.result = result;
        return (new Intent(context, IntraleCheckoutActivity.class)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOGGER.info("IntraleCheckoutActivity: onActivityResult start");
        LOGGER.info("IntraleCheckoutActivity: requestCode:" + requestCode);
        LOGGER.info("IntraleCheckoutActivity: resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==202) {
            result.success("202");
        }
        LOGGER.info("IntraleCheckoutActivity: onActivityResult end");
    }
}
