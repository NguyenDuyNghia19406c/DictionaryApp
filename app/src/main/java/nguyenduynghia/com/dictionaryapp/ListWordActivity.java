package nguyenduynghia.com.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import iamthaoly.com.models.RecentActivity;

public class ListWordActivity extends AppCompatActivity {


    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    public static String wordTable="data";
    static WordAdapter wordAdapter;

    ListView lvWord;
    Toolbar tool_bar_list;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_word);
        processCopy();
        addControls();
        loadAllWords();
        addEvents();

    }

    public void loadAllWords() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);
        wordAdapter.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            String History="";
            if(cursor.getString(3)!=null)
                History=cursor.getString(3);
            boolean isLove= History.equals("Love");
            Word w=new Word(id, word,mean,isLove);
            wordAdapter.add(w);
        }
        cursor.close();
    }

    private void addEvents() {
        createMenu();

        lvWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ListWordActivity.this,SelectedItemActivity.class);
//                Lưu object word vào bundle cho activity
//              hiển thị nghĩa của từ (SelectedItemActivity)
//                Không dùng lvWord.getSelectedItem -> null
                Word tuCanTra = (Word) lvWord.getItemAtPosition(position);
//                RecentActivity.recentWords.add(tuCanTra);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORD", (Serializable) tuCanTra);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void createMenu() {
        tool_bar_list.inflateMenu(R.menu.menu_search);
        tool_bar_list.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lookUps();
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
                        new String[]{newText+"%"},null,null,null);

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

    private void addControls() {
        tool_bar_list=findViewById(R.id.tool_bar_list);
        lvWord=findViewById(R.id.lvWord);
        wordAdapter=new WordAdapter(ListWordActivity.this,R.layout.item_in_list);
        wordAdapter.clear();
        lvWord.setAdapter(wordAdapter);

    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this,
                        "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset()
    {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void traTu(String newText) {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);

        wordAdapter.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            Word w=new Word(id, word, mean);
            wordAdapter.add(w);
        }
        cursor.close();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
