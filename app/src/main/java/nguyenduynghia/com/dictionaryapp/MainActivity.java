package nguyenduynghia.com.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import iamthaoly.com.models.Setting;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    enum ActivityOpen{
        ListWordActivity,
        SplashActivity
    }
    ActivityMainBinding binding;
    Button btnLookUps, btnSettings;
    public static ActivityOpen opening;
    //SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //processCopy();
        addControls();
        addEvents();
    }



    private void addControls() {
        btnLookUps=findViewById(R.id.btnLookUps);
        //search_view=findViewById(R.id.search_view);
        //wordAdapter=new WordAdapter(MainActivity.this,R.layout.item);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void addEvents() {
        btnLookUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opening=ActivityOpen.ListWordActivity;
                Intent intent=new Intent(MainActivity.this,LoadingActivity.class);
                startActivity(intent);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });
    }


}
