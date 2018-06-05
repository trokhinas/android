package ru.startandroid.androidlessons;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    int numActive;
    boolean unlock;

    String TAG = "myLog";
    String TAG_click = "Click log:";

    final Integer numCards = 16;
    Integer cur = 0;
    final Integer rows = 4;
    final Integer cols = 4;



    TextView tvScore;
    Button btnReset;
    Button ActiveCard1, ActiveCard2;
    Button [][]cards;
    String [][]text;
    Toast warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);


        init_game();
    }

    public void init_game()
    {
        init_var();
        init_obj();
        generate_cards();
        fill_cards();
    }
    private void init_var()
    {
        ActiveCard1 = ActiveCard2 = null;
        unlock = true;
        numActive = 0;
        cur = 0;
        text = new String[rows][cols];
        for(int i = 0 ; i < rows ; i ++)
        {
            for(int j = 0 ; j < cols ; j ++)
                text[i][j] = " ";
        }
    }
    public void init_obj()
    {
        tvScore = (TextView) findViewById(R.id.tvScore);
        set_score();
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        warning = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);

        cards = new Button[rows][cols];
        cards[0][0] = (Button) findViewById(R.id.button1);
        cards[0][1] = (Button) findViewById(R.id.button2);
        cards[0][2] = (Button) findViewById(R.id.button3);
        cards[0][3] = (Button) findViewById(R.id.button4);

        cards[1][0] = (Button) findViewById(R.id.button5);
        cards[1][1] = (Button) findViewById(R.id.button6);
        cards[1][2] = (Button) findViewById(R.id.button7);
        cards[1][3] = (Button) findViewById(R.id.button8);

        cards[2][0] = (Button) findViewById(R.id.button9);
        cards[2][1] = (Button) findViewById(R.id.button10);
        cards[2][2] = (Button) findViewById(R.id.button11);
        cards[2][3] = (Button) findViewById(R.id.button12);

        cards[3][0] = (Button) findViewById(R.id.button13);
        cards[3][1] = (Button) findViewById(R.id.button14);
        cards[3][2] = (Button) findViewById(R.id.button15);
        cards[3][3] = (Button) findViewById(R.id.button16);

    }
    private void fill_cards()
    {
        for(int i = 0 ; i < rows ; i ++)
        {
            for(int j = 0 ; j < cols ; j ++) {
                cards[i][j].setOnClickListener(this);
                cards[i][j].setText(text[i][j]);
                cards[i][j].setTextColor(0000);
            }
        }
    }
    public void set_score()
    {
        tvScore.setText(cur.toString() + '/' + numCards.toString());
    }
    public void generate_cards()
    {
        String temp = " ";
        Log.d(TAG,"start generation");
        int pairs = numCards / 2;

        for(Integer cur = 1; cur <= pairs ; cur++)
        {
            boolean exit = false;
            int i,j;
            Random r = new Random(System.currentTimeMillis());
            while(exit == false)
            {
                i = r.nextInt(rows);j = r.nextInt(cols);
                if(text[i][j] == temp) {
                    text[i][j] = cur.toString();
                    exit = true;
                }
            }
            exit = false;
            while(exit == false)
            {
                i = r.nextInt(rows);j = r.nextInt(cols);
                if(text[i][j] == temp) {
                    text[i][j] = cur.toString();
                    exit = true;
                }
            }
        }
    }
    private void reset() {
        if(numActive == 1)
            unset_active(ActiveCard1);
        init_var();
        generate_cards();
        fill_cards();
        set_score();
    }
    public void process_click(View v)
    {
        if(numActive == 0 && unlock)
        {
            numActive++;
            ActiveCard1 = (Button)v;
            set_active(ActiveCard1);
        }
        else if(unlock)
        {
            numActive++;
            ActiveCard2 = (Button)v;
            set_active(ActiveCard2);
        }
        if(numActive == 2)
        {
            unlock = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    numActive = 0;
                    if(ActiveCard1.getText().toString() == ActiveCard2.getText().toString())
                    {
                        cur += 2;
                        set_score();
                        warning.setText("Угадал!");
                        warning.show();
                    }
                    else
                    {
                        unset_active(ActiveCard1);
                        unset_active(ActiveCard2);
                        warning.setText("Мимо!");
                        warning.show();
                    }
                    unlock = true;
                }
            }, 1000);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReset:
                Log.d(TAG_click, "btnReset");
                reset();
                break;
            default:
                Log.d(TAG_click, "another button");
                process_click(v);
        }

    }

    public void set_active(Button b)
    {
        b.setEnabled(false);
        b.setTextColor(Color.BLACK);
    }
    public void unset_active(Button b)
    {
        b.setEnabled(true);
        b.setTextColor(0000);
    }


}
