package com.isbndb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    EditText keyword;
    Button search;
    String json=null;
    String searchQuery;
    public static final int BOOKS=1;
    public static final int AUTHORS=2;
    public static final int SUBJECTS=3;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyword=(EditText)findViewById(R.id.et_keyword);
        search=(Button)findViewById(R.id.bt_search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = keyword.getText().toString();
                Intent i=new Intent(MainActivity.this,SearchResultActivity.class);
                searchQuery=searchQuery.replaceAll(" ", "_");
                i.putExtra("query",searchQuery);
                i.putExtra("type",MainActivity.BOOKS);
                startActivity(i);
            }
        });
    }
}