package com.rg17.credittransfer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    DetailsAdapter detailsAdapter;
    SQLiteDatabase db;
    DatabaseAdapter myDb;
    ListView l;
    String[] days = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseAdapter(this);
        l = (ListView)findViewById(R.id.list_view);
        long id;
        id= myDb.insertInitialData(1,"Urmi","Sharma","urmi@gmail.com",1000);
        id= myDb.insertInitialData(2,"Ria","Pandey","ria@gmail.com",1000);
        id= myDb.insertInitialData(3,"Juhi","Gianani","juhi@gmail.com",1000);
        id= myDb.insertInitialData(4,"Pal","Karkera","pal@gmail.com",1000);
        id= myDb.insertInitialData(5,"Kajal","Jain","kajal@gmail.com",1000);
        id= myDb.insertInitialData(6,"Varsha","Hegde","varsha@gmail.com",1000);
        id= myDb.insertInitialData(7,"Jessika","Merchant","jessi@gmail.com",1000);
        id= myDb.insertInitialData(8,"Akshita","Dave","akku@gmail.com",1000);
        id= myDb.insertInitialData(9,"Sami","Mistry","sami@gmail.com",1000);
        id= myDb.insertInitialData(10,"Ruhi","Gupta","ruhi@gmail.com",1000);

        detailsAdapter = new DetailsAdapter(this,R.layout.user_details_row);
        Cursor cursor = myDb.getInitialDetails();
        String firstName, lastName;
        int currentCredit;
        while(cursor.moveToNext())
        {
            int indexFirstName = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_12);
            int indexLastName = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_13);
            int indexCurrentCredit = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_15);
            firstName = cursor.getString(indexFirstName);
            lastName = cursor.getString(indexLastName);
            currentCredit = cursor.getInt(indexCurrentCredit);
            Details details = new Details(firstName,lastName,currentCredit);
            detailsAdapter.add(details);
        }

        l.setAdapter(detailsAdapter);
        l.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        TextView t1,t2,t3;
        String fName, lName,c;
        int cr;
        t1 = (TextView)view.findViewById(R.id.text_first_name);
        t2 = (TextView)view.findViewById(R.id.text_last_name);
        fName = t1.getText().toString();
        lName = t2.getText().toString();
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("fName",fName);
        intent.putExtra("lName",lName);
        startActivity(intent);
    }

    boolean doubleTap = false;
    @Override
    public void onBackPressed()
    {
        if(doubleTap)
        {
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(this,"Press Back again to exit the app", Toast.LENGTH_SHORT).show();
            doubleTap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    doubleTap = false;
                }
            },3000);
        }
    }
}

