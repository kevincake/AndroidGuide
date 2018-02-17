package cn.ifreedomer.com.androidguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import cn.ifreedomer.com.androidguide.R;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mInterstitialAd != null) {
                mInterstitialAd.setAdListener(null);
            }
            enterMain();
        }
    };
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        add_AD();

    }

    public void add_AD() {


        MobileAds.initialize(this,

                "ca-app-pub-1684365898938671~1340621543");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1684365898938671/5028229895");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                handler.removeMessages(0);
                mInterstitialAd.show();

                enterMain();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdClosed() {

//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                handler.removeMessages(0);
                enterMain();
            }
        });

        handler.sendEmptyMessageDelayed(0, 5000);
    }

    private void enterMain() {
        Toast.makeText(this, R.string.two_finish, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                handler.removeMessages(0);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 2500);
    }

}
