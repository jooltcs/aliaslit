package com.joolt.aliaslit;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<String> words_arr = new ArrayList<>();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readWordsFromAssets(this);

        for (int i = 0; i < 5; i++) {
            int textViewId = getResources().getIdentifier("word_" + (i + 1), "id", getPackageName());
            TextView textView = findViewById(textViewId);

            if (textView != null) {
                int randomIndex = random.nextInt(words_arr.size());
                String randomWord = words_arr.get(randomIndex);
                textView.setText(randomWord);

                words_arr.remove(randomIndex);
            }
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
}
