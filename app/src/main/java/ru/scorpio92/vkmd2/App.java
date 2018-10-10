package ru.scorpio92.vkmd2;

import android.os.Environment;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import ru.scorpio92.vkmd2.data.repository.db.base.AbstractDatabaseDataSource;
import ru.scorpio92.vkmd2.data.repository.db.dao.AppDatabase;
import ru.scorpio92.vkmd2.data.repository.internal.base.AbstractLocalDataSource;

import static ru.scorpio92.vkmd2.BuildConfig.DB_FOLDER;
import static ru.scorpio92.vkmd2.BuildConfig.DB_NAME;


public class App extends AbstractApplication {

    public static final String APP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BuildConfig.APP_FOLDER;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Fabric.with(this, new Crashlytics());
        MultiDex.install(getApplicationContext());
    }

    public static void finish() {
        AppDatabase.closeDB();
    }

    @Override
    public void onInitApp() {
        AbstractLocalDataSource.initialize(getApplicationContext(), null);
        AbstractDatabaseDataSource.initDatabase(getApplicationContext(), AppDatabase.class, APP_DIR + "/" + DB_FOLDER + "/" + DB_NAME);
    }

    @Override
    public void finishApp() {
        AbstractLocalDataSource.close();
        AbstractDatabaseDataSource.closeDatabase();
    }
}