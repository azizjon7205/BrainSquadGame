package com.b12.game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.b12.game.R;

public class MainActivity extends AppCompatActivity {

    private CardView exitCardView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SplashActivity splashActivity = new SplashActivity();
        splashActivity.changeStatusBarColor(this);
        exitCardView = findViewById(R.id.exit);
        exitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CardView cardView = findViewById(R.id.play_game_1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity1.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.play_game_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, SecondGameActivity.class);
                startActivity(intent1);
            }
        });

    }
}