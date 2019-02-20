package com.rg17.credittransfer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText editText;
    Spinner spinner;
    Button b;
    int S_currentCredit;
    String sourcefName,sourcelName;
    Intent intent;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        intent = getIntent();
        sourcefName = intent.getStringExtra("sourcefName");
        sourcelName = intent.getStringExtra("sourcelName");
        spinner = (Spinner)findViewById(R.id.spinner);
        databaseAdapter = new DatabaseAdapter(ThirdActivity.this);
        ArrayList<String> fullNames = databaseAdapter.getNames(sourcefName,sourcelName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinner_text,fullNames);
        spinner.setAdapter(adapter);
        b = (Button)findViewById(R.id.transfer_button);
        b.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        editText = (EditText)findViewById(R.id.edit_text);
        String given = editText.getText().toString();
        final int givenCredit;
        if(given.equals(""))
        {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setMessage("Enter credit to be transferred");
            AlertDialog alertDialog = build.create();
            alertDialog.show();
        }
        else
        {
            S_currentCredit = databaseAdapter.getCurrentCredit(sourcefName,sourcelName);
            givenCredit = Integer.parseInt(given);
            if(givenCredit > S_currentCredit)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Given Credit exceeds your Current Credit ! ! !");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
                builder.setMessage("Confirm Transaction ?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        TextView t = (TextView)findViewById(R.id.spinner_text);
                        String[] name = t.getText().toString().split(" ");
                        String destfName = name[0];
                        String destlName = name[1];
                        int D_currenyCredit = databaseAdapter.getCurrentCredit(destfName,destlName);
                        S_currentCredit = S_currentCredit - givenCredit;
                        D_currenyCredit = D_currenyCredit + givenCredit;
                        databaseAdapter.updateCredit(S_currentCredit,sourcefName,sourcelName);
                        databaseAdapter.updateCredit(D_currenyCredit,destfName,destlName);


                        AlertDialog.Builder finalDialogue = new AlertDialog.Builder(ThirdActivity.this);
                        finalDialogue.setMessage("Credit Transferred Successfully");
                        finalDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Intent intent = new Intent(ThirdActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                        AlertDialog finalAlert = finalDialogue.create();
                        finalAlert.show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}
