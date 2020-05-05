package nguyenduynghia.com.model;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nguyenduynghia.com.dictionaryapp.MainActivity;
import nguyenduynghia.com.dictionaryapp.R;

public class RecyclerRecentWordAdapter extends RecyclerView.Adapter<RecyclerRecentWordAdapter.MyViewHolder> {
    private Context context;
    private List<Word>words;

    public RecyclerRecentWordAdapter(Context context,List<Word>words){
        this.context=context;
        this.words=words;
    }

    @NonNull
    @Override
    public RecyclerRecentWordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRecentWordAdapter.MyViewHolder holder, final int position) {

        final Word word=words.get(position);
        holder.txtWord.setText(word.getWord());

        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtWord;
        ImageView imgClose;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWord=itemView.findViewById(R.id.txtWord);
            imgClose=itemView.findViewById(R.id.imgClose);
        }
    }
}
