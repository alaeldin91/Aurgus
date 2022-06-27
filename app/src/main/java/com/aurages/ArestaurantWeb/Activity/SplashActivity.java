package com.aurages.ArestaurantWeb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurages.ArestaurantWeb.Model.local.DataManager;
import com.aurages.ArestaurantWeb.R;

public class SplashActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "aurages_rest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView txt_aurges=(TextView)findViewById(R.id.textView);

        DataManager.init(getApplicationContext());
        Animation an2= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        txt_aurges.startAnimation(an2);
        TextView txt_Resturant=(TextView)findViewById(R.id.textView5);

        txt_Resturant.startAnimation(an2);
        ImageView  logo =(ImageView)findViewById(R.id.imageView4);
      Animation  anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
      logo.startAnimation(anim);
        SharedPreferences sp = getApplication().getSharedPreferences(SHARED_PREF_NAME, getApplication().MODE_PRIVATE);
        final String login= sp.getString("islogin", "");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            chooseAppRoute();
            }
        }, 5000);


    }

    private void chooseAppRoute() {

        String token = DataManager.read(DataManager.token,"");

        if (token.isEmpty()){
            startActivity(new Intent(SplashActivity.this,NewLoginActivity.class));
        }
        else {
            Log.d("here's the token",token.toString());
            startActivity(new Intent(SplashActivity.this,PinCodeAc.class));

        }
    }

}
