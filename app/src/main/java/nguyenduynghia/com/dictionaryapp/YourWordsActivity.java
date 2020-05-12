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
import android.widget.SearchView;
import android.widget.Toast;

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
        wordAdapter=new WordAdapter(YourWordsActivity.this,R.layout.item);
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
        convertListToAdapter(wordsLove,wordAdapter);
        binding.lvWordsLove.setAdapter(wordAdapter);
    }

    private void lookUps() {
        //MenuItem mnuSearch1=findViewById(R.id.mnuSearch1);
        searchView= findViewById(R.id.mnuSearch1);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor=database.query(wordTable,null,"word like ?",
                        new String[]{"%"+newText+"%"},null,null,null);

                wordAdapter.clear();

                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String word=cursor.getString(1);
                    String mean=cursor.getString(2);

                    Word w=new Word(id, word,mean);
                    wordAdapter.add(w);
                }
                cursor.close();
                return false;
            }
        });
    }
    public void searchByVoice(MenuItem item) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please talk!");

        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Not supported", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 113 && resultCode == RESULT_OK && data != null)
        {
            //phan tu dau tien la dung nhat.
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //txtSpeechInput.setText(result.get(0));
            searchView.onActionViewExpanded();
            searchView.setQuery(result.get(0), false);
        }
    }
}
