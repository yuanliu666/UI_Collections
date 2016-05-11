import android.app.Application;

/**
 * Created by YUAN on 5/10/2016.
 */




        import android.app.Application; import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

import app.com.uicollections.DaoMaster;
import app.com.uicollections.DaoSession;


public class myApplication extends Application{


    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static  SQLiteDatabase db;

    public static final String DB_NAME = "dbname.db";


    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }


    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    public static SQLiteDatabase getSQLDatebase(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            db = daoMaster.getDatabase();
        }
        return db;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}