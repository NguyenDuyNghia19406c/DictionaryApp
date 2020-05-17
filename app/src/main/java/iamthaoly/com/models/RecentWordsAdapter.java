package iamthaoly.com.models;

import android.app.Activity;
import android.content.ContentValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import nguyenduynghia.com.dictionaryapp.ListWordActivity;
import nguyenduynghia.com.dictionaryapp.MainActivity;
import nguyenduynghia.com.dictionaryapp.R;
import nguyenduynghia.com.dictionaryapp.Word;
import nguyenduynghia.com.dictionaryapp.YourWordsActivity;

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
        final ImageView imgSetLove=view.findViewById(R.id.imgSetLove);
        final Word word=getItem(position);
        txtWord.setText(word.getWord());
        if(word.isLove())
            imgSetLove.setImageResource(R.drawable.ic_unlove);
        else
            imgSetLove.setImageResource(R.drawable.ic_love);

        txtWord.setText(word.getWord());

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecentActivity.recentWords.remove(word);
                ContentValues values = new ContentValues();
                values.put("recent", 0);
                ListWordActivity.database.update(ListWordActivity.wordTable, values, "recent=?", new String[]{word.getWord()});
                String message = "\"" + word.getWord() + "\" " + context.getResources().getString(R.string.deleleted2);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
        imgSetLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(word.isLove()) //Đang Love click vô sẽ thành Unlove
                    xuLyUnlove();
                else
                    xuLyLove();
            }

            private void xuLyLove() {
                word.setLove(true);
                YourWordsActivity.wordsLove.add(word);
                imgSetLove.setImageResource(R.drawable.ic_unlove);
                updateLoveOrUnloveToDatabase(word);
                //Thông báo cho user là đã thêm từ vào list yêu thích
                String message = "\"" + word.getWord() + "\" " + context.getResources().getString(R.string.added);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            private void xuLyUnlove() {
                word.setLove(false);
                YourWordsActivity.wordsLove.remove(word);
                if(MainActivity.opening.equals(MainActivity.ActivityOpen.YourWordsActivity))
                    remove(word);
                imgSetLove.setImageResource(R.drawable.ic_love);
                updateLoveOrUnloveToDatabase(word);
                String message = "\"" + word.getWord() + "\" " + context.getResources().getString(R.string.deleted);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            private void updateLoveOrUnloveToDatabase(Word word) {
                ContentValues values = new ContentValues();
                if(word.isLove())
                    values.put("History", "Love");
                else
                    values.put("History", "Unlove");
                ListWordActivity.database.update(ListWordActivity.wordTable, values, "word=?", new String[]{word.getWord()});

            }

        });

        return view;
    }
}
