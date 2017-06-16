package kr.or.dgit.cursoradapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ProductList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ListView listView = (ListView) findViewById(R.id.list );

        SQLiteDatabase db = ProductDbHelper. getInstance (this).getWritableDatabase();
        //Cursor cursor = db.query("product", new String[]{"_id","name"}, null,null,null,null,null);
        Cursor cursor = db.query("product", new String[]{"_id","name","price"}, null,null,null,null,null);
        startManagingCursor(cursor);
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout. simple_list_item_2 ,
                cursor,
                //new String[] {"_id", "name" },
                new String[] {"name", "price" },
                new int[] { android.R.id.text1 ,   android.R.id.text2 },
                0
        );
        listView.setAdapter(mAdapter);
    }
}
