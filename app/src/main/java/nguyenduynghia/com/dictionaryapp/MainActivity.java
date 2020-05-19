package nguyenduynghia.com.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import iamthaoly.com.models.RecentActivity;
import iamthaoly.com.models.Setting;
import nguyenduynghia.com.dictionaryapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    public enum ActivityOpen{
        ListWordActivity,
        YourWordsActivity
    }
    ActivityMainBinding binding;
    Button btnLookUps, btnSettings;
    public static ActivityOpen opening;
    String DATABASE_NAME="TuDienAnhviet.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    //SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        processCopy();
        addControls();
        addEvents();
    }

    private void addControls() {
        btnLookUps=findViewById(R.id.btnLookUps);
        //search_view=findViewById(R.id.search_view);
        //wordAdapter=new WordAdapter(MainActivity.this,R.layout.item);
        btnSettings = findViewById(R.id.btnSettings);
    }
    private void setThemeForActivity() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }
    private void addEvents() {
        btnLookUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opening=ActivityOpen.ListWordActivity;
                Intent intent=new Intent(MainActivity.this,LoadingActivity.class);
                startActivity(intent);
            }
        });
        binding.btnYourWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                opening=ActivityOpen.YourWordsActivity;
                Intent intent=new Intent(MainActivity.this, YourWordsActivity.class);
                startActivity(intent);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });
        binding.btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecentActivity.class);
                startActivity(intent);
            }
        });
    }
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
//                Toast.makeText(this,
//                        "Copying sucess from Assets folder",
//                        Toast.LENGTH_LONG).show();
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

}
