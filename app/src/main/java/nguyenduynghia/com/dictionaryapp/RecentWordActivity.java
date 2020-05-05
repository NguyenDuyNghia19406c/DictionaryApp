package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

import nguyenduynghia.com.model.RecyclerItemClickListener;
import nguyenduynghia.com.model.RecyclerRecentWordAdapter;
import nguyenduynghia.com.model.Word;

public class RecentWordActivity extends AppCompatActivity {

    //ListView lvRecentWord;
    RecyclerView rvRecentWord;
    RecyclerRecentWordAdapter recentWordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_word);
        addControls();
        addEvents();
    }



    private void addControls() {
        //lvRecentWord=findViewById(R.id.lvRecentWord);
       // ListWordActivity.recentWordAdapter=new WordAdapter(RencentWordActivity.this,R.layout.item);
        //lvRecentWord.setAdapter(ListWordActivity.recentWordAdapter);
        rvRecentWord=findViewById(R.id.rvRecentWord);
        recentWordAdapter=new RecyclerRecentWordAdapter(RecentWordActivity.this,HomeActivity.words);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        rvRecentWord.setLayoutManager(layoutManager);
        rvRecentWord.setAdapter(recentWordAdapter);
    }

    private void addEvents() {
        rvRecentWord.addOnItemTouchListener(new RecyclerItemClickListener(this, rvRecentWord, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(RecentWordActivity.this,SelectedItemActivity.class);
                Word tuCanTra = (Word) HomeActivity.words.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORD", (Serializable) tuCanTra);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                ContentValues values=new ContentValues();
                values.put("History","");
                MainActivity.database.update(MainActivity.wordTable,values,"_id like ?",new String[]{String.valueOf(HomeActivity.words.get(position).getId())});

            }
        }));
    }




}
