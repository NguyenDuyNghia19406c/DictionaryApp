package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

public class ListWordActivity extends AppCompatActivity {


    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String wordTable="data";
    static WordAdapter wordAdapter;

    ListView lvWord;
    Toolbar tool_bar_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_word);
        processCopy();
        addControls();
        loadAllWords();
        addEvents();
    }

    private void loadAllWords() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query(wordTable,null,null,null,null,null,null);

        wordAdapter.clear();
        while (cursor.moveToNext()){
            String word=cursor.getString(1);
            String mean=cursor.getString(2);

            Word w=new Word(word,mean);
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
        SearchView searchView= findViewById(R.id.mnuSearch1);

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
                    String word=cursor.getString(1);
                    String mean=cursor.getString(2);

                    Word w=new Word(word,mean);
                    wordAdapter.add(w);
                }
                cursor.close();
                return false;
            }
        });
    }

    private void addControls() {
        tool_bar_list=findViewById(R.id.tool_bar_list);
        lvWord=findViewById(R.id.lvWord);
        wordAdapter=new WordAdapter(ListWordActivity.this,R.layout.item);
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
            String word=cursor.getString(1);
            String mean=cursor.getString(2);
            Word w=new Word(word,mean);
            wordAdapter.add(w);

        }
        cursor.close();

    }

}
