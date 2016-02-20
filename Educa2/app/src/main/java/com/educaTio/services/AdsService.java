package com.educaTio.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.educaTio.utils.ActiveSession;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdsService extends Service {

    private InterstitialAd mInterstitialAd;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public AdsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);

        mInterstitialAd = new InterstitialAd(ActiveSession.getAppContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-7415385960815513/2900636185");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        IntentFilter filter = new IntentFilter();
        filter.addAction("show_ads");
        registerReceiver(mReceiver, filter);

        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("show_ads"), 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);

        return START_STICKY;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        alarmManager.cancel(pendingIntent);
        unregisterReceiver(mReceiver);
        stopSelf();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("show_ads")){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, pendingIntent);
            }

        }
    };

}
