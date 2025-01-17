package com.joolt.aliaslit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private List<String> words_arr = new ArrayList<>();
    private Random random = new Random();
    private int result;
    TextView word_1, word_2, word_3, word_4, word_5, timerTextView;
    public SettingsManager settingsManager;
    private Handler handler = new Handler();
    private int seconds = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word_1 = findViewById(R.id.word_1);
        word_2 = findViewById(R.id.word_2);
        word_3 = findViewById(R.id.word_3);
        word_4 = findViewById(R.id.word_4);
        word_5 = findViewById(R.id.word_5);
        timerTextView = findViewById(R.id.timerTextView);

        readWordsFromAssets(this);

        setTexts();

        check(word_1);
        check(word_2);
        check(word_3);
        check(word_4);
        check(word_5);

        if(!isRunning){
            startTimer();
        }
    }

    public void readWordsFromAssets(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("wordlist.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    words_arr.add(word);
                }
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void check(TextView textView) {
        textView.setTag(true);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled = (boolean) textView.getTag();
                if (isEnabled) {
                    textView.setTag(false);
                    textView.setAlpha(0.5f);
                    result++;
                    if (result % 5 == 0) {
                        words_arr.clear();
                        readWordsFromAssets(getApplicationContext());
                        setTexts();
                    }
                } else {
                    textView.setTag(true);
                    textView.setAlpha(1.0f);
                    result--;
                }
            }
        });
    }

    public void setTexts(){
        for (int i = 0; i < 5; i++) {
            int textViewId = getResources().getIdentifier("word_" + (i + 1), "id", getPackageName());
            TextView textView = findViewById(textViewId);

            if (textView != null) {
                int randomIndex = random.nextInt(words_arr.size());
                String randomWord = words_arr.get(randomIndex);
                textView.setText(randomWord);

                words_arr.remove(randomIndex);
            }

            textView.setTag(true);
            textView.setAlpha(1.0f);
        }
    }

    private void startTimer() {
        isRunning = true;
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        isRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                seconds++;
                int minutes = seconds / 60;
                int secs = seconds % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, secs));
                handler.postDelayed(this, 1000);
                if (seconds == settingsManager.getTime()) {
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                }
            }
        }
    };
}
