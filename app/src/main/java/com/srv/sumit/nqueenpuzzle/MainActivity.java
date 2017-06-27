package com.srv.sumit.nqueenpuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener{
    EditText editText;
    Button button;
    int number_rows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.row);
        button = (Button) findViewById(R.id.submitrow);
        editText.addTextChangedListener(MainActivity.this);
        button.setOnClickListener(MainActivity.this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            number_rows = Integer.parseInt(s.toString());
            if(number_rows>=6 || number_rows<2){
                s.replace(0,s.length(),"2");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        number_rows = Integer.parseInt(String.valueOf(editText.getText()));
        Intent intent=new Intent(MainActivity.this,GameActivity.class);
        intent.putExtra("number_rows",number_rows);
        startActivity(intent);
        finish();
        //Toast.makeText(MainActivity.this,editText.getText(),Toast.LENGTH_SHORT).show();

    }
}
