package nguyenduynghia.com.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import nguyenduynghia.com.dictionaryapp.databinding.ActivityYourWordsBinding;

public class YourWordsActivity extends AppCompatActivity {

    ActivityYourWordsBinding binding;
    SearchView searchView;
    SQLiteDatabase database;
    String wordTable="data";
    WordAdapter wordAdapter;
    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static ArrayList<Word> wordsLove=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityYourWordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddControls();
        AddEvents();


    }

    private void AddControls() {
        wordAdapter=new WordAdapter(YourWordsActivity.this,R.layout.item_in_list);
    }

    private void convertListToAdapter(List<Word> words, WordAdapter wordAdapter) {
        for (Word word: words) {
            wordAdapter.add(word);
        }
    }

    private void AddEvents() {
        binding.toolBarList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolBarList.setTitle(getString(R.string.yourWords));
        convertListToAdapter(wordsLove,wordAdapter);
        binding.lvWordsLove.setAdapter(wordAdapter);
        binding.lvWordsLove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(YourWordsActivity.this,SelectedItemActivity.class);
                Word tuCanTra = (Word) binding.lvWordsLove.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORD", (Serializable) tuCanTra);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
