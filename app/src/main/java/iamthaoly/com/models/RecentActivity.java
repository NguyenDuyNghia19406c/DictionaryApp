package iamthaoly.com.models;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import nguyenduynghia.com.dictionaryapp.R;
import nguyenduynghia.com.dictionaryapp.Word;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityRecentBinding;

public class RecentActivity extends AppCompatActivity {
    ActivityRecentBinding binding;
    RecentWordsAdapter recentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddControls();
        AddEvents();
    }

    private void AddControls() {
        recentAdapter = new RecentWordsAdapter(RecentActivity.this, R.layout.item);
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
//        convertListToAdapter(recentWords, recentAdapter);
//        binding.lvRecent.setAdapter(recentAdapter);
//        binding.lvRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(RecentActivity.this, SelectedItemActivity.class);
//                Word tuCanTra = (Word) binding.lvRecent.getItemAtPosition(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("WORD", (Serializable) tuCanTra);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }
}
