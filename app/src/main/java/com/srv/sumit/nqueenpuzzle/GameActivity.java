package com.srv.sumit.nqueenpuzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameActivity extends AppCompatActivity implements OnClickListener{

    private int  SIZE;
    Button[][] buttons;
    List<Integer> list;
    TextView textView,textView1;
    private static int move=0;
    int inv_count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        SIZE = intent.getIntExtra("number_rows",3);
        buttons =new Button[SIZE][SIZE];
        textView=new TextView(this);
        textView1=new TextView(this);
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        move=preferences.getInt("move",0);
        textView.setTextSize(25);
        textView1.setTextSize(25);
        textView.setText("MOVE ");


        list=new ArrayList<>();
        for(int i=1;i<(SIZE*SIZE);i++){
            list.add(i);
        }
        Collections.shuffle(list);
        while(isSolvable()){
            Collections.shuffle(list);
        }
        list.add(0);
        LinearLayout.LayoutParams layoutParams=new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        LinearLayout.LayoutParams layoutParams1=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(2,2,2,2);
       layoutParams1.setMargins(40,18,2,60);
        textView.setLayoutParams(layoutParams1);
        layoutParams2.setMargins(2,18,40,60);
        textView1.setLayoutParams(layoutParams2);
        int k=0;
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    buttons[i][j]=new Button(GameActivity.this);
                    buttons[i][j].setTag(i+" "+j);
                    buttons[i][j].setLayoutParams(layoutParams);
                    String val=preferences.getString(i+" "+j,list.get(k++)+"");
                    buttons[i][j].setText(val);
                    buttons[i][j].setBackgroundColor(Color.parseColor("#A5D6A7"));
                    buttons[i][j].setTextColor(Color.parseColor("#212121"));
                    buttons[i][j].setOnClickListener(GameActivity.this);
                }
            }
            if(buttons[SIZE-1][SIZE-1].getText().equals("0")){
                buttons[SIZE-1][SIZE-1].setText("");
                textView1.setText("0");
            }
            else{
                textView1.setText(String.valueOf(move));
            }

        LinearLayout rows=new LinearLayout(GameActivity.this);
        rows.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<SIZE;i++){
            LinearLayout row=new LinearLayout(GameActivity.this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            rows.addView(row);
            for(int j=0;j<SIZE;j++){
                row.addView(buttons[i][j]);
            }
        }
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(textView);
        ll.addView(textView1);
        rows.addView(ll);
        setContentView(rows);
    }

    private boolean isSolvable() {
        for(int i=0;i<SIZE*SIZE-2;i++){
            for(int j=i+1;j<SIZE*SIZE-1;j++){
                if(list.get(i)>list.get(j)){
                    inv_count++;
                }
            }
        }
        if((SIZE%2==0 && inv_count%2==0)||(SIZE%2!=0 && inv_count%2==0)){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Button b= (Button) v;
        String xy[]=b.getTag().toString().split(" ");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        int[] xx={x-1,x,x+1,x};
        int[] yy={y,y-1,y,y+1};

        for(int k=0;k<4;k++){
            int i=xx[k];
            int j=yy[k];
            if(i>=0 && i<SIZE && j>=0 && j<SIZE && buttons[i][j].getText().equals("")){
                buttons[i][j].setText(b.getText().toString());
                b.setText("");
                textView1.setText(String.valueOf(++move));
                break;
            }

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Toast.makeText(this,"OnDestory() was called",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Save Game")
                .setMessage("Wanna Save the Game???")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        for(int i=0;i<SIZE;i++){
                            for(int j=0;j<SIZE;j++){
                                editor.putString(i+" "+j,buttons[i][j].getText().toString());
                            }
                        }
                        editor.putInt("move",move);
                        editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();

        // super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("move",move);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        move=savedInstanceState.getInt("move");
    }
}
