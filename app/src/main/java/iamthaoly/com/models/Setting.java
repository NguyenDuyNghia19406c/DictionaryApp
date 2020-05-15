package iamthaoly.com.models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import nguyenduynghia.com.dictionaryapp.R;

public class Setting extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radEnglish, radVietnamese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControls();
        addEvents();
    }

    private void addEvents() {
        radioGroup.clearCheck();

    }

    private void addControls() {
        radioGroup = findViewById(R.id.languages);
        radEnglish = findViewById(R.id.radEnglish);
        radVietnamese = findViewById(R.id.radVietnamese);
    }

    public void changeLanguage(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radEnglish:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radVietnamese:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
