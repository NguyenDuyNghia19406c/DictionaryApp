package iamthaoly.com.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;

import com.yariksoffice.lingver.Lingver;
import com.yariksoffice.lingver.store.PreferenceLocaleStore;

import java.util.Locale;

import iamthaoly.com.models.BaseActivity;
import nguyenduynghia.com.dictionaryapp.MainActivity;
import nguyenduynghia.com.dictionaryapp.R;

public class Setting extends BaseActivity {
    RadioGroup radioGroup, darkModeGroup;
    RadioButton radEnglish, radVietnamese;
    RadioButton radDarkOn, radDarkOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.settings));
        addControls();
        addEvents();
    }
    private void setThemeForActivity() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }

    private void addEvents() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radEnglish:
//                        Locale.setDefault(new Locale("en"));
                        Setting.this.setNewLocale("en");
                        break;
                    case R.id.radVietnamese:
//                        Locale.setDefault(new Locale("vi"));
                        Setting.this.setNewLocale("vi");
                        break;
                }
            }
        });
        darkModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radDarkOn:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        restart();
                        break;
                    case R.id.radDarkOff:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        restart();
                        break;
                }
            }
        });
    }
    private void addControls() {
        radioGroup = findViewById(R.id.languages);
        radEnglish = findViewById(R.id.radEnglish);
        radVietnamese = findViewById(R.id.radVietnamese);
        String lang = Lingver.getInstance().getLanguage();
        if(lang.equals("en")) radioGroup.check(R.id.radEnglish);
        else radioGroup.check(R.id.radVietnamese);

        darkModeGroup = findViewById(R.id.darkMode);
        radDarkOn = findViewById(R.id.radDarkOn);
        radDarkOff = findViewById(R.id.radDarkOff);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            darkModeGroup.check(R.id.radDarkOn);
        else
            darkModeGroup.check(R.id.radDarkOff);
    }

    private void followSystemLocale() {
        Lingver.getInstance().setFollowSystemLocale(this);
        restart();
    }
    private void setNewLocale(String lang) {
        Lingver.getInstance().setLocale(this, lang);
        restart();
    }
    private void restart() {
        Intent intent = new Intent(Setting.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);

//        Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
