package utili;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class db extends SQLiteOpenHelper {
    private static int version = 1;
    private static String name = "HipotecaDb" ;
    private static SQLiteDatabase.CursorFactory factory = null;
    private SQLiteDatabase dataB;

    public db(Context context) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dataB = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
