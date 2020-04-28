package nguyenduynghia.com.dictionaryapp;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WordAdapter extends ArrayAdapter<Word> {


    Activity context;
    int resource;
    public WordAdapter(@NonNull Activity context, int resource) {
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
            }
        });

        return view;
    }
}
