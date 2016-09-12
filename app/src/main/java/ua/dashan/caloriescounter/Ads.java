package ua.dashan.caloriescounter;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Ads {
    public static void showBanner(View v){
        AdView banner = (AdView)v.findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

    }
    public static void setupContentPadding(Activity activity,int padding){

    }
}
