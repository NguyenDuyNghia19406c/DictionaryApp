package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedItemActivity extends AppCompatActivity {

    Toolbar tool_bar_selecteditem;
    TabHost tabHost;
    Word tuCanTra;
    TextView txtWord, txtMeaning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);

        Intent intent = getIntent();
        tuCanTra = (Word) intent.getSerializableExtra("WORD");

        addTabs();
        addControls();
        addEvents();

//        Lấy từ người dùng chọn bên ListWordActivity


        addMeaning();
    }

    private void addMeaning() {
        txtWord.setText(tuCanTra.getWord());
        txtMeaning.setText(tuCanTra.getMean());
    }

    private void addEvents() {
        tool_bar_selecteditem.inflateMenu(R.menu.menu_item);
        tool_bar_selecteditem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        tool_bar_selecteditem=findViewById(R.id.tool_bar_selecteditem);

        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);
    }

    private void addTabs() {
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        //tạo các tab
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Định nghĩa");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Từ đồng nghĩa");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Hình ảnh");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);
    }


}
