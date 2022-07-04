package com.b12.game.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b12.game.R;
import com.b12.game.fragments.FragmentFirstGameAssignment;

public class FragmentHolder extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_fragment_holder);
        SplashActivity splashActivity = new SplashActivity();
        splashActivity.changeStatusBarColor(this);
        dialog = new Dialog(this);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstgame", true);
        if (firstStart) {
            onlyOnceStart();
        } else {
            mainGame();
        }
        SharedPreferences four = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean levelfour = prefs.getBoolean("firstgame", true);
        if (firstStart) {
            onlyOnceStart();
        } else {
            mainGame();
        }
    }

    private void onlyOnceStart() {
        dialog.setContentView(R.layout.fragment_start_first_game);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView cardView = dialog.findViewById(R.id.first_game_assignment_play_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstgame", false);
                editor.apply();
                dialog.dismiss();
                mainGame();
            }
        });
        dialog.show();
    }

    public void mainGame() {
        Fragment someFragment = new FragmentFirstGameAssignment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, someFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.dismiss();
        Intent intent = new Intent(FragmentHolder.this, GameActivity1.class);
        startActivity(intent);
        finish();
    }
}