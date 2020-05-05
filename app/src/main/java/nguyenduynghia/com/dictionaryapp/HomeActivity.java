package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import nguyenduynghia.com.model.Word;
import nguyenduynghia.com.model.WordAdapter;

import static java.lang.Thread.sleep;

public class HomeActivity extends AppCompatActivity {

    Button btnLookUps,btnSettings,btnRecentWords;
    static List<Word> words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControls();
        addEvents();

    }

    private void addControls() {
        btnLookUps=findViewById(R.id.btnLookUps);
        btnSettings=findViewById(R.id.btnSettings);
        btnRecentWords=findViewById(R.id.btnRecent);
        //words=new ArrayList<>();


        MainActivity.wordAdapter=new WordAdapter(HomeActivity.this,R.layout.item);
    }

    private void addEvents() {

        btnRecentWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, RecentWordActivity.class);
                loadRecentWords();
                startActivity(intent);

            }
        });

        btnLookUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,ListWordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRecentWords() {
        MainActivity.database=openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=MainActivity.database.query(MainActivity.wordTable,null,
                "History like ?",new String[]{"recent"},null,null,null);
        HomeActivity.words.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            Word w=new Word(id, word,mean);

            HomeActivity.words.add(w);
        }
        cursor.close();
    }

}
