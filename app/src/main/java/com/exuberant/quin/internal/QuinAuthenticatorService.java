package com.exuberant.quin.internal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class QuinAuthenticatorService extends Service {

    private QuinAccountAuthenticator accountAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        accountAuthenticator = new QuinAccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return accountAuthenticator.getIBinder();
    }
}
