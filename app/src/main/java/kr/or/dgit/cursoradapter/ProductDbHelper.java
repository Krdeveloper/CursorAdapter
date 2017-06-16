package kr.or.dgit.cursoradapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by HKM on 2017-06-16.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final String TAG = ProductDbHelper.class.getSimpleName();
    private static final int VERSION = 2;
    private static final String DB_NAME = "test.db";
    private final Context context;
    private static ProductDbHelper instance ;

    public synchronized static ProductDbHelper getInstance(Context context) {
        if ( instance == null){
            instance = new ProductDbHelper(context);
        }
        return instance ;
    }
    private ProductDbHelper(Context context) {
        super(context, DB_NAME , null, VERSION );
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {//앱을 지우고 깔면 이게 수행
        for(int i = 1; i<= VERSION ; i++){
            applySqlFile(db, i, null);
        }
        applySqlFile(db, VERSION , "product_data.1.sql");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//버전1이 깔린 상태면 이게 수행
        for(int i = (oldVersion + 1); i<=newVersion; i++){
            applySqlFile(db, i, null);
        }
    }

    private void applySqlFile(SQLiteDatabase db, int version, String fileName){
        BufferedReader reader = null;
        String filename = fileName;
        if (filename==null) {
            filename = String. format ("%s.%d.sql", DB_NAME , version);
        }
        Log. d ( TAG , "File Name " + filename);

        try {
            final InputStream inputStream = context.getAssets().open(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            final StringBuilder statement = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Reading line -> " + line);
                }
                if (!TextUtils.isEmpty(line) && !line.startsWith("--")) {
                    statement.append(line.trim());
                }
                if (line.endsWith(";")) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Running statement -> " + statement);
                    }

                    db.execSQL(statement.toString());
                    statement.setLength(0);
                }
            }
        }catch (IOException e){
            Log. e ( TAG , "Could not apply SQL file -> " + e);
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log. w ( TAG , "Could not close reader", e);
                }
            }

        }
    }
}
