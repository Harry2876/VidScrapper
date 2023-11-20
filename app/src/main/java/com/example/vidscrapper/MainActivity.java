package com.example.vidscrapper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.furkankaplan.fkblurview.FKBlurView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

private TextView greetingText;
private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up the background blur
        FKBlurView backgroundblur = findViewById(R.id.blurview);
        backgroundblur.setBlur(this, backgroundblur, 100);

        //Setting up the greeting text
        greetingText = findViewById(R.id.greetingtext);

        //Adding handler to update the time
        handler = new Handler();
        updateGreeting();
        handler.postDelayed(updateGreetingRunnable, 3600000);
        

    }

    private void updateGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting = getGreeting(hour);
        greetingText.setText(greeting);
    }

    private String getGreeting(int hour) {
        String greeting;

        if (hour >= 0 && hour < 12) {
            greeting = "Good Morning";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        return greeting;
    }

    private final Runnable updateGreetingRunnable = new Runnable() {
        @Override
        public void run() {
            updateGreeting();

            //scheduling the next update
            handler.postDelayed(this, 3600000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //remove callbacks to prevent memory leaks
        handler.removeCallbacks(updateGreetingRunnable);
    }
}