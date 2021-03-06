package iamthaoly.com.models;

import android.app.Activity;
import android.content.ContentValues;
import android.opengl.Visibility;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import nguyenduynghia.com.dictionaryapp.ListWordActivity;
import nguyenduynghia.com.dictionaryapp.R;
import nguyenduynghia.com.dictionaryapp.Word;

public class RecentWordsAdapter extends ArrayAdapter<Word> {

    Activity context;
    int resource;
    public RecentWordsAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=this.context.getLayoutInflater().inflate(this.resource,null);
        final TextView txtWord=view.findViewById(R.id.txtWord);
        final ImageView imgClose=view.findViewById(R.id.imgClose);
        final Word word=getItem(position);
        txtWord.setText(word.getWord());
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(word);
                RecentActivity.recentList.remove(word);
                updateRecentToDatabase(word);
            }
        });

        return view;

    }
    private void updateRecentToDatabase(Word tuCanTra) {
        ContentValues values = new ContentValues();
        values.put("recent", "false");
        RecentActivity.database.update(RecentActivity.wordTable, values, "word=?", new String[]{tuCanTra.getWord()});

    }
    public void clearAll()
    {
        int count = getCount();
//        Toast.makeText(context, "Count=" + count, Toast.LENGTH_SHORT).show();
        for(int i = count - 1; i >= 0; i--)
        {
            Word word = getItem(i);
            remove(word);
            RecentActivity.recentList.remove(word);
            updateRecentToDatabase(word);
        }
    }

}
