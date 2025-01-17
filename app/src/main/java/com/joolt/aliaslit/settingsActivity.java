package com.joolt.aliaslit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class settingsActivity extends AppCompatActivity {
    private EditText time;
    private Button saveButton;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        settingsManager = new SettingsManager(this);

        time = findViewById(R.id.time);
        saveButton = findViewById(R.id.save_button);

        int savedTime = settingsManager.getTime();
        time.setText(String.valueOf(savedTime));

        saveButton.setOnClickListener(v -> {
            String timeInput = time.getText().toString();
            if (!timeInput.isEmpty()) {
                int timeValue = Integer.parseInt(timeInput);
                settingsManager.saveTime(timeValue);
            }
        });
    }
}
