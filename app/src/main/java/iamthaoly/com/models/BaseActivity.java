package iamthaoly.com.models;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar == null)
        {
            Toast.makeText(this, "ActionBar is nulled!", Toast.LENGTH_SHORT).show();
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
