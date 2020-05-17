package nguyenduynghia.com.dictionaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import iamthaoly.com.models.RecentWordsAdapter;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityRecentBinding;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityYourWordsBinding;

public class RecentActivity extends AppCompatActivity {
    ActivityRecentBinding binding;
    SearchView searchView;
    public static SQLiteDatabase database;
    public static String wordTable="data";
    RecentWordsAdapter recentAdapter;
    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static ArrayList<Word> wordsLove;
    static boolean isInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRecentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddControls();
//        if(isInit == false) initWordsLove();
//        AddEvents();
    }
    public void initWordsLove()
    {
        isInit = true;
        ArrayList<Word> temp = wordsLove;
        wordsLove = new ArrayList<>();
        loadAllWords();

    }
    private void AddControls() {
        recentAdapter = new RecentWordsAdapter(RecentActivity.this,R.layout.item_in_list);
    }
    public void loadAllWords() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);
        recentAdapter.clear();
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
        binding.toolBarRecent.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolBarRecent.setTitle(getString(R.string.recentWords));
//        convertListToAdapter(wordsLove,wordAdapter);
        binding.lvRecent.setAdapter(recentAdapter);
        binding.lvRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(RecentActivity.this,SelectedItemActivity.class);
                Word tuCanTra = (Word) binding.lvRecent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORD", (Serializable) tuCanTra);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
