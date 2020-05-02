package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

import nguyenduynghia.com.dictionaryapp.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {

    ActivityLoadingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);  //Delay of 1 seconds
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent();
                    switch (MainActivity.opening){
                        case ListWordActivity:
                            intent=new Intent(LoadingActivity.this, ListWordActivity.class);
                            binding.txtNameActivity.setText("Loook ups word");
                    }
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
