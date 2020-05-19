package iamthaoly.com.models;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nguyenduynghia.com.dictionaryapp.R;
import nguyenduynghia.com.dictionaryapp.SelectedItemActivity;
import nguyenduynghia.com.dictionaryapp.Word;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityRecentBinding;

public class RecentActivity extends AppCompatActivity {
    ActivityRecentBinding binding;
    SearchView searchView;
    public static SQLiteDatabase database;
    public static String wordTable="data";
    RecentWordsAdapter recentAdapter;
    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static ArrayList<Word> recentList;
    static boolean isInit = false;
    SharedPreferences settingPreferences;
    boolean theme_boolean;
    MenuItem mnuClearAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        binding=ActivityRecentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddControls();
        if(!isInit) initRecentWords();
        displayEmptyImage();
        AddEvents();
    }
    private void setThemeForActivity() {
        settingPreferences = getSharedPreferences("THEME", 0);
        theme_boolean = settingPreferences.getBoolean("theme_boolean", false);
        if(theme_boolean) setTheme(R.style.DarkTheme);
        else setTheme(R.style.LightTheme);
    }
    public void initRecentWords()
    {
        isInit = true;
        if(recentList == null)
        {
            recentList = new ArrayList<>();
            loadAllWords();
        }
        else
        {
            loadAllWords();
        }

    }
    private void displayEmptyImage() {
        if(recentList.size() == 0)
        {
            binding.layoutEmptyImage.setVisibility(View.VISIBLE);

        }
        else
        {
            binding.layoutEmptyImage.setVisibility(View.GONE);
        }
    }
    private void AddControls() {
        recentAdapter = new RecentWordsAdapter(RecentActivity.this, R.layout.item);
    }
    public void loadAllWords() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);
        recentAdapter.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            String recent = "";
            if(cursor.getString(4)!=null)
                recent = cursor.getString(4);
            Word w=new Word(id, word,mean);
            if(recent.equals("true")) recentList.add(w);
        }
        cursor.close();
    }
    private void convertListToAdapter(List<Word> words, RecentWordsAdapter wordAdapter) {
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
        binding.toolBarRecent.inflateMenu(R.menu.menu_recent);
        Menu menu = binding.toolBarRecent.getMenu();
        mnuClearAll = menu.findItem(R.id.mnuClearAll);
        convertListToAdapter(recentList,recentAdapter);
        binding.lvRecent.setAdapter(recentAdapter);
        binding.lvRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(RecentActivity.this, SelectedItemActivity.class);
                Word tuCanTra = (Word) binding.lvRecent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORD", (Serializable) tuCanTra);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mnuClearAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(recentList.size() > 0) recentAdapter.clearAll();
                return false;
            }
        });
    }

}
