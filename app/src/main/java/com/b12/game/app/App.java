package com.b12.game.app;

import android.app.Application;

import com.b12.game.Base;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Base.init(this);
    }
}
