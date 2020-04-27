package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class SelectedItemActivity extends AppCompatActivity {


    Toolbar tool_bar_selecteditem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        addControls();
        addEvents();
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

    }


}
