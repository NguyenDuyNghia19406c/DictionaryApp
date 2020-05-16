package iamthaoly.com.models;

import android.app.Application;
import android.content.Context;

import com.yariksoffice.lingver.Lingver;
import com.yariksoffice.lingver.store.PreferenceLocaleStore;

import java.util.Locale;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Locale locale = new Locale("vi");
        PreferenceLocaleStore store = new PreferenceLocaleStore((Context)this, locale);
        Lingver lingver = Lingver.init(this, store);
    }
}
