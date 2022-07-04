package com.b12.game.fragments;

import static android.content.Context.MODE_PRIVATE;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.b12.game.LevelCompeletActivity;
import com.b12.game.R;
import com.b12.game.activities.SplashActivity;
import com.b12.game.adapters.FirstGameAdapter;
import com.b12.game.adapters.FirstGameItemCountAdapter;
import com.b12.game.getset.FirstGameItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentFirstGameAssignment extends Fragment implements FirstGameItemCountAdapter.OnAnswerClickListener {
    private FirstGameAdapter firstGameAdapter;
    private FirstGameItemCountAdapter firstGameItemCountAdapter;
    private ArrayList<Integer> gameItems;
    private ArrayList<Integer> tempItems;
    private ArrayList<FirstGameItem> answersList;
    private RecyclerView recyclerViewImages, recyclerViewCount;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private TextView levelTxt, circler_progress_txt, levelInLevel;
    private ImageView imageViewInCard, first_game_helth_count;
    private ProgressBar progressBarHorizontal;
    private CardView cardView;
    private Random rnd;
    private int counter = 10,horizontalCounter = 5, itemCount = 0, playerHealth, randomImagesCount = 5;
    private int levelCount, randomItem;
    private Handler handler;
    private String levelTxtBundle;
    private MediaPlayer mp;
    private SharedPreferences sharedPreferences;
    private RecyclerView.LayoutManager layoutManager;
    private Timer timer;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_game_assignment, container, false);
        SplashActivity splashActivity = new SplashActivity();
        splashActivity.changeStatusBarColor(getActivity());
        handler = new Handler();
        rnd = new Random();
        timer = new Timer();
        sharedPreferences = getActivity().getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE);
        levelTxtBundle = sharedPreferences.getString("levelnum", "");
        levelCount = sharedPreferences.getInt("levelCount", 0);
        playerHealth = sharedPreferences.getInt("playerHealth", 3);

        layoutManager = new GridLayoutManager(getContext(), 5);
        answersList = new ArrayList<>();
        tempItems = new ArrayList<>();
        gameItems = new ArrayList<>();
        tempItems.add(R.drawable.img_1);
        tempItems.add(R.drawable.img_2);
        tempItems.add(R.drawable.img_3);
        tempItems.add(R.drawable.img_4);
        first_game_helth_count = view.findViewById(R.id.first_game_helth_count);
        cardView = view.findViewById(R.id.first_game_assignment_image_view_card);
        circler_progress_txt = view.findViewById(R.id.progress_circular_text);
        progressBarHorizontal = view.findViewById(R.id.horizontal_progress_bar);
        recyclerViewImages = view.findViewById(R.id.first_game_recycler);
        recyclerViewCount = view.findViewById(R.id.first_game_item_count_recycler);
        imageViewInCard = view.findViewById(R.id.first_game_card_image);
        levelTxt = view.findViewById(R.id.first_game_level_txt);
        levelInLevel = view.findViewById(R.id.first_game_level_in_level_txt);
        progressBar = view.findViewById(R.id.progress_circular);
        relativeLayout = view.findViewById(R.id.first_game_answer_relative_layout);
        levelTxt.setText("Level " + levelTxtBundle);
        levelInLevel.setText(levelCount + "/" + levelTxtBundle);
        progressBarHorizontal.setVisibility(View.GONE);
        imageViewInCard.setVisibility(View.GONE);
        if (Integer.parseInt(levelTxtBundle) <= 3) {
            first_game_helth_count.setVisibility(View.GONE);
        }
        getPlayerHealthImage();

        countDownTimer();
        generateImage();

        return view;
    }

    private void getPlayerHealthImage() {
        if (Integer.parseInt(levelTxtBundle) > 3) {
            if (playerHealth == 3)
                first_game_helth_count.setImageResource(R.drawable.player_health_full);
            if (playerHealth == 2)
                first_game_helth_count.setImageResource(R.drawable.player_health_two);
            if (playerHealth == 1)
                first_game_helth_count.setImageResource(R.drawable.player_health_one);
            if (playerHealth == 0) {
                first_game_helth_count.setImageResource(R.drawable.player_health_null);
                endGame();
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void generateImage() {
        int minus = Integer.parseInt(levelTxtBundle) - 1;
        for (int i = 0; i < randomImagesCount + minus; i++) {
            int value = rnd.nextInt(4);
            gameItems.add(tempItems.get(value));
        }
        recyclerViewImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        recyclerViewImages.setHasFixedSize(true);
        recyclerViewImages.setLayoutManager(layoutManager);
        recyclerViewImages.setItemAnimator(null);
        firstGameAdapter = new FirstGameAdapter(gameItems);
        recyclerViewImages.setAdapter(firstGameAdapter);
    }

    private void countDownTimer() {
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                if (counter >= 0) {
                    circler_progress_txt.setText("" + counter);
                    progressBar.setProgress(counter);
                    counter--;
                    handler.postDelayed(this::run, 1000);
                } else {
                    handler.removeCallbacks(this::run);
                    progressBar.setVisibility(View.GONE);
                    recyclerViewImages.setVisibility(View.GONE);
                    circler_progress_txt.setVisibility(View.GONE);
                    progressBarHorizontal.setVisibility(View.VISIBLE);
                    imageViewInCard.setVisibility(View.VISIBLE);
                    recyclerViewCount.setVisibility(View.VISIBLE);
                    progressBarHorizontalTimer();
                    getRandomImageFromArray();
                }
            }
        }, 100);
    }


    private void progressBarHorizontalTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                horizontalCounter++;
                progressBarHorizontal.setProgress(horizontalCounter);
                if (horizontalCounter == 100) {
                    playerHealth = playerHealth - 1;
                    SharedPreferences.Editor editorLevelNumber = getActivity().getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
                    editorLevelNumber.putInt("playerHealth", playerHealth);
                    editorLevelNumber.apply();
                    playWrongSound();
                    nextLevel();
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 0, 50);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getRandomImageFromArray() {
        randomItem = gameItems.get(rnd.nextInt(gameItems.size()));
        for (int i = 0; i < gameItems.size(); i++) {
            if (gameItems.get(i).equals(randomItem)) {
                itemCount = itemCount + 1;
            }
        }
        Random rnd = new Random();
        answersList.add(new FirstGameItem(itemCount));
        while (answersList.size() < 4) {
            int count = (int) (0.65 * (double) gameItems.size());
            if(count < 5) count = 5;
            int random = rnd.nextInt(count) + 1;
            if (!answersList.contains(new FirstGameItem(random)))
                answersList.add(new FirstGameItem(random));
        }
        Collections.shuffle(answersList);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            imageViewInCard.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.big_icon_background));
        } else {
            imageViewInCard.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.big_icon_background));
        }
        imageViewInCard.setImageResource(randomItem);

        relativeLayout.setVisibility(View.VISIBLE);
        recyclerViewCount.setHasFixedSize(true);
        recyclerViewCount.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewCount.setItemAnimator(null);
        firstGameItemCountAdapter = new FirstGameItemCountAdapter(answersList, this);
        recyclerViewCount.setAdapter(firstGameItemCountAdapter);
    }

    @Override
    public void onAnswerClicked(int answer, LinearLayout layout) {
        timer.cancel();
        checkAnswer(answer, layout);
    }

    @SuppressLint("ResourceAsColor")
    private void checkAnswer(int answer, LinearLayout layout) {
        if (answer == itemCount) {
            layout.setBackgroundColor(Color.parseColor("#4ECB71"));
            imageViewInCard.setImageResource(R.drawable.succesfull_img);
            playSuccesSound();
            nextLevel();
        } else {
            playerHealth = playerHealth - 1;
            SharedPreferences.Editor editorLevelNumber = getActivity().getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
            editorLevelNumber.putInt("playerHealth", playerHealth);
            editorLevelNumber.putInt("playerHealth", playerHealth);
            editorLevelNumber.apply();
            layout.setBackgroundColor(Color.parseColor("#F24E1E"));
            imageViewInCard.setImageResource(R.drawable.wrong_img);
            playWrongSound();
            nextLevel();
        }
    }

    private void playWrongSound() {
        mp = MediaPlayer.create(getContext(), R.raw.wrong_sound);
        mp.start();
    }

    private void nextLevel() {
        if (levelCount < Integer.parseInt(levelTxtBundle)) {
            if (Integer.parseInt(levelTxtBundle) > 3) {
                if (playerHealth == 3)
                    first_game_helth_count.setImageResource(R.drawable.player_health_full);
                if (playerHealth == 2)
                    first_game_helth_count.setImageResource(R.drawable.player_health_two);
                if (playerHealth == 1)
                    first_game_helth_count.setImageResource(R.drawable.player_health_one);
                if (playerHealth == 0) {
                    first_game_helth_count.setImageResource(R.drawable.player_health_null);
                    endGame();
                }
            }
            SharedPreferences.Editor editorLevelNumber = getActivity().getSharedPreferences("LEVELSNUMBER", MODE_PRIVATE).edit();
            editorLevelNumber.putInt("levelCount", levelCount + 1);
            editorLevelNumber.apply();
            Fragment someFragment = new FragmentFirstGameAssignment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, someFragment);
            transaction.commit();
        } else {
            endGame();
        }
    }

    private void endGame() {
        Intent intent = new Intent(getContext(), LevelCompeletActivity.class);
        intent.putExtra("PLAYERHEALTH", playerHealth);
        intent.putExtra("LEVELNUMBER", levelTxtBundle);
        startActivity(intent);
        getActivity().finish();

    }

    private void playSuccesSound() {
        mp = MediaPlayer.create(getContext(), R.raw.succesfully_sound);
        mp.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timer.cancel();
        handler.removeCallbacksAndMessages(null);
    }

}
