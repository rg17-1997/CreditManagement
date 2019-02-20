package com.rg17.credittransfer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView t1,t2,t3,t4,t5;
    Button b;
    String firstName,lastName,email,userid,currentcredit;
    int userID,currentCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent i = getIntent();
        String fName = i.getExtras().getString("fName");
        String lName = i.getExtras().getString("lName");
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        Cursor cursor = databaseAdapter.getDetails(fName,lName);
        firstName = null;
        lastName = null;
        email = null;
        currentcredit = null;
        userid = null;
        int count = 0;
        while(cursor.moveToNext() && count !=1)
        {
            int indexUserID = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_11);
            int indexFirstName = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_12);
            int indexLastName = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_13);
            int indexEmail = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_14);
            int indexCurrentCredit = cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COL_15);
            userID = cursor.getInt(indexUserID);
            userid = ""+userID;
            firstName = cursor.getString(indexFirstName);
            lastName = cursor.getString(indexLastName);
            email = cursor.getString(indexEmail);
            currentCredit = cursor.getInt(indexCurrentCredit);
            currentcredit = ""+currentCredit;
            count++;

        }
        t1 = (TextView)findViewById(R.id.t1);
        t2 = (TextView)findViewById(R.id.t2);
        t3 = (TextView)findViewById(R.id.t3);
        t4 = (TextView)findViewById(R.id.t4);
        t5 = (TextView)findViewById(R.id.t5);
        t1.setText(userid);
        t2.setText(firstName);
        t3.setText(lastName);
        t4.setText(email);
        t5.setText(currentcredit);
        b = (Button)findViewById(R.id.b);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this,ThirdActivity.class);
        intent.putExtra("sourcefName",firstName);
        intent.putExtra("sourcelName",lastName);
        intent.putExtra("currentCredit",currentCredit);
        startActivity(intent);
    }
}
