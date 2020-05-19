package nguyenduynghia.com.dictionaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nguyenduynghia.com.dictionaryapp.databinding.ActivityYourWordsBinding;

public class YourWordsActivity extends AppCompatActivity {

    ActivityYourWordsBinding binding;
    SearchView searchView;
    public static SQLiteDatabase database;
    public static String wordTable="data";
    WordAdapter wordAdapter;
    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static ArrayList<Word> wordsLove;
    static boolean isInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        binding=ActivityYourWordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddControls();
        if(isInit == false) initWordsLove();
        displayEmptyImage();
        AddEvents();
    }
    private void setThemeForActivity() {
        SharedPreferences settingPreferences = getSharedPreferences("THEME", 0);
        boolean theme_boolean = settingPreferences.getBoolean("theme_boolean", true);
        if(theme_boolean) setTheme(R.style.DarkTheme);
        else setTheme(R.style.LightTheme);
    }
    private void displayEmptyImage() {
        if(wordsLove.size() == 0)
        {
            binding.layoutEmptyImage.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.layoutEmptyImage.setVisibility(View.GONE);
        }
    }

    public void initWordsLove()
    {
        isInit = true;
        ArrayList<Word> temp = wordsLove;
        wordsLove = new ArrayList<>();
        loadAllWords();

    }
    private void AddControls() {
        wordAdapter=new WordAdapter(YourWordsActivity.this,R.layout.item_in_list);
    }
    public void loadAllWords() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);
        wordAdapter.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            String History="";
            if(cursor.getString(3)!=null)
                History=cursor.getString(3);
            boolean isLove=History.equals("Love");
            Word w=new Word(id, word,mean,isLove);
            if(isLove) wordsLove.add(w);
        }
        cursor.close();
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
