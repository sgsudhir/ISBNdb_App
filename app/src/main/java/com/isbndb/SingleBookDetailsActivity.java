package com.isbndb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SingleBookDetailsActivity extends AppCompatActivity {
    int index;
    String jsonString;
    JSONObject rootObj,obj;
    JSONArray rootArr,arr;
    String title,author,publisher,isbn,edition,subjects;
    TextView titleView,autherView,publisherView,isbnView,ediView,subjectsView;
    Intent searchResultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book_details);
        Bundle b=getIntent().getExtras();
        index=b.getInt("index");
        index--;
        jsonString=b.getString("jsonString");
        try {
            rootObj=new JSONObject(jsonString.toString());
            rootArr=rootObj.getJSONArray("data");
            rootObj=rootArr.getJSONObject(index);
            title=rootObj.getString("title");
            publisher=rootObj.getString("publisher_name");
            isbn=rootObj.getString("isbn13");
            edition=rootObj.getString("edition_info");
            try {
                arr=rootObj.getJSONArray("subject_ids");
                subjects=arr.getString(0);
                arr=rootObj.getJSONArray("author_data");
                obj=arr.getJSONObject(0);
                author=obj.getString("name");
            }catch (Exception ee){
                ee.printStackTrace();
            }
            Log.d("Details",title + author + publisher + isbn + edition + subjects + " Success!!!");

        }catch (Exception e){
            e.printStackTrace();
        }
        titleView=(TextView)findViewById(R.id.title_desc);
        autherView=(TextView)findViewById(R.id.author_desc);
        publisherView=(TextView)findViewById(R.id.publisher_desc);
        isbnView=(TextView)findViewById(R.id.isbn_desc);
        ediView=(TextView)findViewById(R.id.edition_desc);
        subjectsView=(TextView)findViewById(R.id.subject_desc);
        titleView.setText(title);
        autherView.setText(author);
        publisherView.setText(publisher);
        isbnView.setText(isbn);
        ediView.setText(edition);
        subjectsView.setText(subjects);
        autherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultIntent = new Intent(SingleBookDetailsActivity.this, SearchResultActivity.class);
                searchResultIntent.putExtra("query", autherView.getText());
                searchResultIntent.putExtra("type", MainActivity.AUTHORS);
                startActivity(searchResultIntent);
            }
        });
        subjectsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultIntent = new Intent(SingleBookDetailsActivity.this,SearchResultActivity.class);
                searchResultIntent.putExtra("type",MainActivity.SUBJECTS);
                searchResultIntent.putExtra("query",subjectsView.getText());
                startActivity(searchResultIntent);
            }
        });
    }
}
