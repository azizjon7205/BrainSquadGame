package com.b12.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.b12.game.activities.FragmentHolder;
import com.b12.game.activities.GameActivity1;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.emitters.StreamEmitter;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class LevelCompeletActivity extends AppCompatActivity {

    private ImageView stars, retry, menu, next;
    private SharedPreferences sharedPreferences;
    private TextView textView, completeTxt;
    private int nextLevelNumber, currentLevelStar, particlesPerSecond;
    private String level, totalStars;
    private SharedPreferences.Editor editorLevelNumber;
    private KonfettiView konfettiView;
    private long emittingTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);
        stars = findViewById(R.id.complete_stars);
        retry = findViewById(R.id.complete_retry);
        menu = findViewById(R.id.complete_home);
        next = findViewById(R.id.complete_next);
        textView = findViewById(R.id.complete_text);
        completeTxt = findViewById(R.id.level_complate_txt);
        level = getIntent().getStringExtra("LEVELNUMBER");
        sharedPreferences = getSharedPreferences("LEVELS", MODE_PRIVATE);
        editorLevelNumber = getSharedPreferences("LEVELS", MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("LEVELS", MODE_PRIVATE);
        currentLevelStar = sharedPreferences.getInt(level, 0);

        if (level.equals("16")) {
            completeTxt.setText("GAME COMPLETE");
            next.setVisibility(View.GONE);
            fireWork();
        } else {
            completeTxt.setText("LEVEL COMPLETE");
        }
        setLevelStars();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextlevel();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editorLevelNumber = getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
                editorLevelNumber.putInt("levelCount", 1);
                editorLevelNumber.apply();
                Intent intent = new Intent(LevelCompeletActivity.this, GameActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editorLevelNumber = getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
                editorLevelNumber.putString("levelnum", level);
                editorLevelNumber.putInt("levelCount", 1);
                editorLevelNumber.putInt("playerHealth", 3);
                editorLevelNumber.apply();
                Intent intent = new Intent(LevelCompeletActivity.this, FragmentHolder.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fireWork() {
        konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(false)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(0f, konfettiView.getWidth() + 50f, 0f, 0f).streamFor(particlesPerSecond = 300, emittingTime = StreamEmitter.INDEFINITE);
    }

    private void setLevelStars() {
        int health = getIntent().getIntExtra("PLAYERHEALTH", 0);
        int minus = Integer.parseInt(level) - 1;
        if (Integer.parseInt(level) == 1) {
            if (health == 0 || health == 1 || health == 2) {
                stars.setImageResource(R.drawable.complete_star_0);
                textView.setText(R.string.string_failed);
                textView.setTextColor(Color.parseColor("#F24E1E"));
            } else if (health == 3) {
                stars.setImageResource(R.drawable.complete_star_3);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_3);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            }
        } else if (Integer.parseInt(level) == 2) {
            if (health == 0 || health == 1) {
                stars.setImageResource(R.drawable.complete_star_0);
                textView.setText(R.string.string_failed);
                textView.setTextColor(Color.parseColor("#F24E1E"));
            } else if (health == 2) {
                stars.setImageResource(R.drawable.complete_star_2);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_2);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            } else if (health == 3) {
                stars.setImageResource(R.drawable.complete_star_3);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_3);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            }
        } else {
            if (health == 3) {
                stars.setImageResource(R.drawable.complete_star_3);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_3);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            }
            if (health == 2) {
                stars.setImageResource(R.drawable.complete_star_2);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_2);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            }
            if (health == 1) {
                stars.setImageResource(R.drawable.complete_star_1);
                editorLevelNumber.putInt(Integer.toString(minus), R.drawable.stars_1);
                textView.setText(R.string.string_congratulations);
                unLockNextLevel();
            }
            if (health == 0) {
                stars.setImageResource(R.drawable.complete_star_0);
                textView.setText(R.string.string_failed);
                textView.setTextColor(Color.parseColor("#F24E1E"));
            }
        }
        editorLevelNumber.apply();
    }

    private void openNextlevel() {
        int nextLevel = Integer.parseInt(level) + 1;
        sharedPreferences = getSharedPreferences("STATUS", MODE_PRIVATE);
        boolean nextlevelStatus = sharedPreferences.getBoolean(level, true);

        if (nextlevelStatus) {
            SharedPreferences.Editor editorLevelNumber = getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
            editorLevelNumber.putString("levelnum", Integer.toString(nextLevel));
            editorLevelNumber.putInt("levelCount", 1);
            editorLevelNumber.putInt("playerHealth", 3);
            editorLevelNumber.apply();
            Intent intent = new Intent(LevelCompeletActivity.this, FragmentHolder.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Not enough stars!", Toast.LENGTH_SHORT).show();
        }
    }

    private void unLockNextLevel() {
        sharedPreferences = getSharedPreferences("TOTALSTARS", MODE_PRIVATE);
        totalStars = sharedPreferences.getString("TOTALSTARS", "");
        if (Integer.parseInt(level) == 5) {
            if (Integer.parseInt(totalStars) >= 8) {
                SharedPreferences.Editor editorStatus = getSharedPreferences("STATUS", MODE_PRIVATE).edit();
                editorStatus.putBoolean("5", true);
                editorStatus.apply();
            }
        } else if (Integer.parseInt(level) == 9) {
            if (Integer.parseInt(totalStars) >= 16) {
                SharedPreferences.Editor editorStatus = getSharedPreferences("STATUS", MODE_PRIVATE).edit();
                editorStatus.putBoolean("9", true);
                editorStatus.apply();
            }
        } else if (Integer.parseInt(level) == 13) {
            if (Integer.parseInt(totalStars) >= 24) {
                SharedPreferences.Editor editorStatus = getSharedPreferences("STATUS", MODE_PRIVATE).edit();
                editorStatus.putBoolean("13", true);
                editorStatus.apply();
            }
        } else {
            SharedPreferences.Editor editorStatus = getSharedPreferences("STATUS", MODE_PRIVATE).edit();
            editorStatus.putBoolean(level, true);
            editorStatus.apply();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editorLevelNumber = getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
        editorLevelNumber.putInt("levelCount", 1);
        editorLevelNumber.apply();
        Intent intent = new Intent(LevelCompeletActivity.this, GameActivity1.class);
        startActivity(intent);
        finish();
    }
}