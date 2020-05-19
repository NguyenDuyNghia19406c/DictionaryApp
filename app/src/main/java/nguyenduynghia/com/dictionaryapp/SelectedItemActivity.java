package nguyenduynghia.com.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectedItemActivity extends AppCompatActivity {

    Toolbar tool_bar_selecteditem;
    TabHost tabHost;
    Word tuCanTra;
    TextView txtWord, txtMeaning, txtIPA;
    ImageButton btnBritishSpeaker, btnAmericanSpeaker;
    MenuItem mnuLove;
    TextToSpeech speaker;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeForActivity();
        setContentView(R.layout.activity_selected_item);

        Intent intent = getIntent();
        tuCanTra = (Word) intent.getSerializableExtra("WORD");

        addTabs();
        addControls();
        addEvents();
//        Lấy từ người dùng chọn bên ListWordActivity
        addMeaning();
    }
    private void setThemeForActivity() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }
    private void addMeaning() {
        txtWord.setText(tuCanTra.getWord());

        //trim unnecessary word.
        String mean = tuCanTra.getMean();
        mean = mean.replace("@", "");
        mean = mean.replaceFirst(tuCanTra.getWord(), "");
        //get IPA
        int ipaBegin = mean.indexOf("/");
        int ipaEnd = mean.indexOf("/", ipaBegin + 1);
        if(ipaBegin == -1 || ipaEnd == -1)
        {
            txtIPA.setVisibility(View.GONE);
        }
        else
        {
            txtIPA.setVisibility(View.VISIBLE);
            String IPA = mean.substring(ipaBegin, ipaEnd + 1);
            mean = mean.replace(IPA, "");
            txtIPA.setText(IPA);
        }
        mean = mean.trim();
        tuCanTra.setMean(mean);
        txtMeaning.setText(tuCanTra.getMean());
    }

    private void addEvents() {
        tool_bar_selecteditem.inflateMenu(R.menu.menu_item);
        tool_bar_selecteditem.setTitle(tuCanTra.getWord());
        searchView = findViewById(R.id.mnuSearch2);
//        searchView.setIconified(false);
        tool_bar_selecteditem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Menu menu = tool_bar_selecteditem.getMenu();
        mnuLove = menu.findItem(R.id.mnuLove);
        if (tuCanTra.isLove())
        {
            mnuLove.setIcon(R.drawable.ic_unlove);
        }
        else
        {
            mnuLove.setIcon(R.drawable.ic_love);
        }
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


        mnuLove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(YourWordsActivity.wordsLove == null) YourWordsActivity.wordsLove = new ArrayList<>();
                if(tuCanTra.isLove()) //Đang Love click vô sẽ thành Unlove
                    xuLyUnlove();
                else
                    xuLyLove();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                highlight(newText);
                return false;
            }
        });
    }

    private void highlight(String text)
    {
        SpannableString spannableString = new SpannableString(txtMeaning.getText());
        //remove old highlighted word
        BackgroundColorSpan[] oldSpan =
                spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);
        for (BackgroundColorSpan bgSpan : oldSpan)
            spannableString.removeSpan(bgSpan);
        //highlight new word
        Pattern pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(txtMeaning.getText());
        while(m.find())
        {
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW),
                    m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        txtMeaning.setText(spannableString);
    }

    private void xuLyLove() {
        tuCanTra.setLove(true);
        YourWordsActivity.wordsLove.add(tuCanTra);
        mnuLove.setIcon(R.drawable.ic_unlove);
        updateLoveOrUnloveToDatabase(tuCanTra);
        //Thông báo cho user là đã thêm từ vào list yêu thích
        String message = "\"" + tuCanTra.getWord() + "\" " + this.getResources().getString(R.string.added);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void xuLyUnlove() {
        tuCanTra.setLove(false);
        YourWordsActivity.wordsLove.remove(tuCanTra);
        mnuLove.setIcon(R.drawable.ic_love);
        updateLoveOrUnloveToDatabase(tuCanTra);
        String message = "\"" + tuCanTra.getWord() + "\" " + this.getResources().getString(R.string.deleted);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void updateLoveOrUnloveToDatabase(Word word)
    {
        ContentValues values = new ContentValues();
        if(word.isLove())
            values.put("History", "Love");
        else
            values.put("History", "Unlove");
        if(YourWordsActivity.database != null)
            YourWordsActivity.database.update(YourWordsActivity.wordTable, values, "word=?", new String[]{word.getWord()});
        if(ListWordActivity.database != null)
            ListWordActivity.database.update(ListWordActivity.wordTable, values, "word=?", new String[]{word.getWord()});

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
//        mnuLove = menu.findViewById(R.id.mnuLove);


        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);
        txtIPA = findViewById(R.id.txtIPA);

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
