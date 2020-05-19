package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

import nguyenduynghia.com.dictionaryapp.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {

    ActivityLoadingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        binding=ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(500);  //Delay of 1 seconds
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent();
                    switch (MainActivity.opening){
                        case ListWordActivity:
                            intent=new Intent(LoadingActivity.this, ListWordActivity.class);
                            binding.txtNameActivity.setText(getString(R.string.loading2));
                            break;
                        case YourWordsActivity:
                            intent=new Intent(LoadingActivity.this, YourWordsActivity.class);
                            binding.txtNameActivity.setText(getString(R.string.loading2));
                            break;
                    }
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
    private void setThemeForActivity() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }
}
