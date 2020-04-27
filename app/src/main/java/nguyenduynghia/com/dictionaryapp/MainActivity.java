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


public class MainActivity extends AppCompatActivity {


    Button btnLookUps;
    //SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //processCopy();
        addControls();
        addEvents();
    }
    private void addControls() {
        btnLookUps=findViewById(R.id.btnLookUps);
        //search_view=findViewById(R.id.search_view);
        //wordAdapter=new WordAdapter(MainActivity.this,R.layout.item);

    }

    private void addEvents() {


        btnLookUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListWordActivity.class);
                startActivity(intent);
            }
        });

    }








}
