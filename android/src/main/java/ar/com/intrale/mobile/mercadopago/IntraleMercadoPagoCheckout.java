package ar.com.intrale.mobile.mercadopago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mercadopago.android.px.configuration.AdvancedConfiguration;
import com.mercadopago.android.px.configuration.PaymentConfiguration;
import com.mercadopago.android.px.configuration.TrackingConfiguration;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.core.PrefetchService;
import com.mercadopago.android.px.internal.callbacks.CallbackHolder;
import com.mercadopago.android.px.internal.datasource.MercadoPagoPaymentConfiguration;
import com.mercadopago.android.px.internal.di.Session;
import com.mercadopago.android.px.internal.features.checkout.CheckoutActivity;
import com.mercadopago.android.px.internal.util.TextUtil;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import com.mercadopago.android.px.tracking.internal.MPTracker;
import com.mercadopago.android.px.tracking.internal.events.InitEvent;

import io.flutter.plugin.common.MethodChannel;

public class IntraleMercadoPagoCheckout {

    private MercadoPagoCheckout mercadoPagoCheckout;

    IntraleMercadoPagoCheckout(@NonNull String publicKey, @NonNull String preferenceId) {
        this.mercadoPagoCheckout = new MercadoPagoCheckout.Builder(publicKey, preferenceId).build();;
        CallbackHolder.getInstance().clean();
    }

    public void startPayment(@NonNull Context context, int requestCode, @NonNull MethodChannel.Result result) {
        this.startIntent(context, IntraleCheckoutActivity.getIntent(context, result), requestCode);
    }

    private void startIntent(@NonNull Context context, @NonNull Intent checkoutIntent, int requestCode) {
        Session session = Session.getInstance();
        session.init(mercadoPagoCheckout);

        /*if (this.prefetch != null && this.prefetch.getInitResponse() != null) {
            session.getInitRepository().lazyConfigure(this.prefetch.getInitResponse());
        }*/

        PrefetchService.onCheckoutStarted();
        MPTracker.getInstance().initializeSessionTime();
        (new InitEvent(session.getConfigurationModule().getPaymentSettings())).track();
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(checkoutIntent, requestCode);
        } else {
            checkoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(checkoutIntent);
        }

    }

    @NonNull
    public AdvancedConfiguration getAdvancedConfiguration() {
        return mercadoPagoCheckout.getAdvancedConfiguration();
    }

    @NonNull
    public String getPublicKey() {
        return mercadoPagoCheckout.getPublicKey();
    }

    @Nullable
    public String getPreferenceId() {
        return mercadoPagoCheckout.getPreferenceId();
    }

    @Nullable
    public CheckoutPreference getCheckoutPreference() {
        return mercadoPagoCheckout.getCheckoutPreference();
    }

    @NonNull
    public String getPrivateKey() {
        return mercadoPagoCheckout.getPrivateKey();
    }

    @NonNull
    public PaymentConfiguration getPaymentConfiguration() {
        return mercadoPagoCheckout.getPaymentConfiguration();
    }

    @NonNull
    public TrackingConfiguration getTrackingConfiguration() {
        return mercadoPagoCheckout.getTrackingConfiguration();
    }

}
