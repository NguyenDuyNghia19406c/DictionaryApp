package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SelectedItemActivity extends AppCompatActivity {

    Toolbar tool_bar_selecteditem;
    TabHost tabHost;
    Word tuCanTra;
    TextView txtWord, txtMeaning;
    ImageButton btnBritishSpeaker, btnAmericanSpeaker;
    TextToSpeech speaker;
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
        //Speak
        btnBritishSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speak("british");
            }
        });
        btnAmericanSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("american");
            }
        });
    }

    private void speak(final String voice) {
        if(speaker != null) speaker.stop();
        speaker = new TextToSpeech(SelectedItemActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result;
                    if(voice.equals("british"))
                        result = speaker.setLanguage(Locale.UK);
                    else
                        result = speaker.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    private void ConvertTextToSpeech() {
        String text = tuCanTra.getWord();
        speaker.setSpeechRate(0.8f);
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void addControls() {
        tool_bar_selecteditem=findViewById(R.id.tool_bar_selecteditem);

        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);
//        txtMeaning.setMovementMethod(new ScrollingMovementMethod());

        btnBritishSpeaker = findViewById(R.id.btnBritishSpeaker);
        btnAmericanSpeaker = findViewById(R.id.btnAmericanSpeaker);
    }

    private void addTabs() {
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        //tạo các tab
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator(getResources().getString(R.string.definition));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);
//        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
//        tab2.setIndicator("Từ đồng nghĩa");
//        tab2.setContent(R.id.tab2);
//        tabHost.addTab(tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator(getResources().getString(R.string.images));
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);
    }


}
