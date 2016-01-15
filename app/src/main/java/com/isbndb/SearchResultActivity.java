package com.isbndb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchResultActivity extends AppCompatActivity {
    String query;
    String jsonString;
    TextView book[]=new TextView[10];
    TextView desc[]=new TextView[10];
    Intent singleBookIntent;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Bundle b=getIntent().getExtras();
        query=b.getString("query");
        query=query.replaceAll(" ", "_");
        type=b.getInt("type");

        book[0]=(TextView)findViewById(R.id.book_01);
        book[1]=(TextView)findViewById(R.id.book_02);
        book[2]=(TextView)findViewById(R.id.book_03);
        book[3]=(TextView)findViewById(R.id.book_04);
        book[4]=(TextView)findViewById(R.id.book_05);
        book[5]=(TextView)findViewById(R.id.book_06);
        book[6]=(TextView)findViewById(R.id.book_07);
        book[7]=(TextView)findViewById(R.id.book_08);
        book[8]=(TextView)findViewById(R.id.book_09);
        book[9]=(TextView)findViewById(R.id.book_10);
        desc[0]=(TextView)findViewById(R.id.book_01_desc);
        desc[1]=(TextView)findViewById(R.id.book_02_desc);
        desc[2]=(TextView)findViewById(R.id.book_03_desc);
        desc[3]=(TextView)findViewById(R.id.book_04_desc);
        desc[4]=(TextView)findViewById(R.id.book_05_desc);
        desc[5]=(TextView)findViewById(R.id.book_06_desc);
        desc[6]=(TextView)findViewById(R.id.book_07_desc);
        desc[7]=(TextView)findViewById(R.id.book_08_desc);
        desc[8]=(TextView)findViewById(R.id.book_09_desc);
        desc[9]=(TextView)findViewById(R.id.book_10_desc);

        singleBookIntent=new Intent(SearchResultActivity.this,SingleBookDetailsActivity.class);
        if(type==MainActivity.BOOKS) {
            new GetBooks("http://isbndb.com/api/v2/json/J2LK3L4F/books?q=" + query).execute();
        }else if(type==MainActivity.AUTHORS){
            new GetBooksByAuthor("http://isbndb.com/api/v2/json/J2LK3L4F/authors?q=" + query).execute();
        }else if (type==MainActivity.SUBJECTS){
            new GetBooksBySubjects("http://isbndb.com/api/v2/json/J2LK3L4F/subjects?q=" + query).execute();
        }

        book[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",1);
                singleBookIntent.putExtra("jsonString",jsonString);
                startActivity(singleBookIntent);
            }
        });
        book[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",2);startActivity(singleBookIntent);
            }
        });
        book[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",3);startActivity(singleBookIntent);
            }
        });
        book[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",4);startActivity(singleBookIntent);
            }
        });
        book[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",5);startActivity(singleBookIntent);
            }
        });
        book[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",6);startActivity(singleBookIntent);
            }
        });
        book[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",7);startActivity(singleBookIntent);
            }
        });
        book[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",8);startActivity(singleBookIntent);
            }
        });
        book[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",9);startActivity(singleBookIntent);
            }
        });
        book[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBookIntent.putExtra("index",10);
                startActivity(singleBookIntent);
            }
        });
    }


    class GetBooks extends AsyncTask<Void,Void,Void>{
        String url;
        ProgressDialog dialog;
        public GetBooks(String url) {
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(SearchResultActivity.this);
            dialog.setTitle("Feating Data. Please wait ...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JsonHandler jsonHandler=new JsonHandler();
                jsonString=jsonHandler.requestJsonByUrl(url,JsonHandler.GET);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject obj,rootObj;
            JSONArray arr,rootArr;
            super.onPostExecute(aVoid);

            try {
                rootObj=new JSONObject(jsonString.toString());
                rootArr=rootObj.getJSONArray("data");
                for(int i=0;i<10;i++){
                    obj=rootArr.getJSONObject(i);
                    book[i].setText(obj.getString("title_latin"));
                    desc[i].setText("ISBN: "+obj.getString("isbn13") + ", PUBLISHER: " + obj.getString("publisher_name"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            dialog.dismiss();
        }
    }

    class GetBooksByAuthor extends AsyncTask<Void,Void,Void>{
        String url;
        ProgressDialog dialog;
        public GetBooksByAuthor(String url){
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(SearchResultActivity.this);
            dialog.setTitle("Feating Data. Please wait ...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JsonHandler jsonHandler=new JsonHandler();
                jsonString=jsonHandler.requestJsonByUrl(url,JsonHandler.GET);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject obj,rootObj;
            JSONArray arr,rootArr;
            super.onPostExecute(aVoid);
            try {
                rootObj=new JSONObject(jsonString.toString());
                rootArr=rootObj.getJSONArray("data");
                obj=rootArr.getJSONObject(0);
                arr=obj.getJSONArray("book_ids");
                for(int i=0; i<arr.length() && i<10; i++){
                    book[i].setText(arr.getString(i));
                }
                dialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GetBooksBySubjects extends AsyncTask<Void,Void,Void>{
        String url;
        ProgressDialog dialog;
        public GetBooksBySubjects(String url){
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(SearchResultActivity.this);
            dialog.setTitle("Feating Data. Please wait ...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JsonHandler jsonHandler=new JsonHandler();
                jsonString=jsonHandler.requestJsonByUrl(url,JsonHandler.GET);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject obj,rootObj;
            JSONArray arr,rootArr;
            super.onPostExecute(aVoid);
            try {
                rootObj=new JSONObject(jsonString.toString());
                rootArr=rootObj.getJSONArray("data");
                obj=rootArr.getJSONObject(0);
                arr=obj.getJSONArray("book_ids");
                for(int i=0;i<10 && i< arr.length();i++){
                    book[i].setText(arr.getString(i));
                }
                dialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


