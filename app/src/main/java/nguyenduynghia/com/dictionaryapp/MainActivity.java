package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nguyenduynghia.com.dictionaryapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    enum ActivityOpen{
        ListWordActivity,
        YourWordsActivity
    }
    ActivityMainBinding binding;
    Button btnLookUps;
    public static ActivityOpen opening;
    public static WordAdapter wordsLoveAdapter;
    //SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //processCopy();
        addControls();
        addEvents();
    }
    private void addControls() {
        btnLookUps=findViewById(R.id.btnLookUps);
        wordsLoveAdapter=new WordAdapter(MainActivity.this,R.layout.item);
        //search_view=findViewById(R.id.search_view);
        //wordAdapter=new WordAdapter(MainActivity.this,R.layout.item);

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
        binding.btnYourWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opening=ActivityOpen.YourWordsActivity;
                Intent intent=new Intent(MainActivity.this,LoadingActivity.class);
                startActivity(intent);
            }
        });

    }








}
