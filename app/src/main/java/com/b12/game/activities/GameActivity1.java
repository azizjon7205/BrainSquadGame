package com.b12.game.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.b12.game.R;
import com.b12.game.adapters.FirstGameLevelsAdapter;
import com.b12.game.getset.Level;
import java.util.ArrayList;

public class GameActivity1 extends AppCompatActivity implements FirstGameLevelsAdapter.OnLevelClickListener {
    private ArrayList<Level> list;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences, sharedPreferencesStatus;
    private FirstGameLevelsAdapter adapter;
    private LinearLayout linearLayout;
    private int levels = 15;
    private TextView allStars;
    private String totalstars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        SplashActivity splashActivity = new SplashActivity();
        splashActivity.changeStatusBarColor(this);
        linearLayout = findViewById(R.id.game1_linear2);
        allStars = findViewById(R.id.allStars);

        sharedPreferences = getSharedPreferences("ALLSTARS", MODE_PRIVATE);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            creatingLevels();
        }

        sharedPreferences = getSharedPreferences("LEVELS", MODE_PRIVATE);
        sharedPreferencesStatus = getSharedPreferences("STATUS", MODE_PRIVATE);

        list = new ArrayList<>();
        levelsList();
        recyclerView = findViewById(R.id.levels_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(null);
        adapter = new FirstGameLevelsAdapter(list, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void creatingLevels() {
        SharedPreferences.Editor editorStars = getSharedPreferences("LEVELS", MODE_PRIVATE).edit();
        SharedPreferences.Editor editorStatus = getSharedPreferences("STATUS", MODE_PRIVATE).edit();
        SharedPreferences.Editor editorAllStars = getSharedPreferences("ALLSTARS", MODE_PRIVATE).edit();
        editorAllStars.putInt("ALLSTARS", 0);
        for (int i = 0; i <= levels; i++) {
            editorStars.putInt(Integer.toString(i), R.drawable.stars_0);
            if (i <= 0) {
                editorStatus.putBoolean(Integer.toString(i), true);
            } else {
                editorStatus.putBoolean(Integer.toString(i), false);
            }
        }
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
        editorAllStars.apply();
        editorStars.apply();
        editorStatus.apply();
    }

    private void levelsList() {
        for (int i = 0; i <= levels; i++) {
            int sharedItem = sharedPreferences.getInt(Integer.toString(i), 0);
            boolean sharedStatus = sharedPreferencesStatus.getBoolean(Integer.toString(i), true);
            list.add(new Level(Integer.toString(i + 1), sharedItem, sharedStatus));
        }
        calculateTotalStars(list);
    }

    @Override
    public void onLevelClicked(String level, boolean status) {
        if (status) {
            SharedPreferences.Editor editorLevelNumber = getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
            editorLevelNumber.putString("levelnum", level);
            editorLevelNumber.putInt("levelCount", 1);
            editorLevelNumber.putInt("playerHealth", 3);
            editorLevelNumber.apply();
            Intent intent = new Intent(GameActivity1.this, FragmentHolder.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Not enough stars!", Toast.LENGTH_SHORT).show();
        }

    }

    private void calculateTotalStars(ArrayList<Level> list) {
        int totalStarsCount = 0;
        for (Level item : list) {
            if (item.getLevelStars() == R.drawable.stars_1) {
                totalStarsCount = totalStarsCount + 1;
            } else if (item.getLevelStars() == R.drawable.stars_2) {
                totalStarsCount = totalStarsCount + 2;
            } else if (item.getLevelStars() == R.drawable.stars_3) {
                totalStarsCount = totalStarsCount + 3;
            }
        }
        SharedPreferences sharedpreferences = getSharedPreferences("TOTALSTARS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("TOTALSTARS", String.valueOf(totalStarsCount));
        editor.apply();
        sharedPreferences = getSharedPreferences("TOTALSTARS", MODE_PRIVATE);
        totalstars = sharedPreferences.getString("TOTALSTARS", "");
        allStars.setText(totalstars + "/45");
    }

}