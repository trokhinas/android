package ru.startandroid.androidlessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNewGame, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_buttons();

    }

    public void init_buttons()
    {
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnNewGame.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnExit:
                finish();
                break;
            case R.id.btnNewGame:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
        }

    }
}
