package com.singlelab.gpf.interactive_games.piano_tiles.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.singlelab.gpf.R;
import com.singlelab.gpf.databinding.ActivityPianoTilesBinding;
import com.singlelab.gpf.interactive_games.piano_tiles.FragmentListener;

public class PianoTilesActivity extends AppCompatActivity implements FragmentListener {
    ActivityPianoTilesBinding bind;
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    GameoverFragment gameoverFragment;
    HomeFragment homeFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind = ActivityPianoTilesBinding.inflate(getLayoutInflater());
        View view = this.bind.getRoot();
        setContentView(view);

        this.fragmentManager = this.getSupportFragmentManager();
        this.homeFragment = new HomeFragment();
        this.mainFragment = new MainFragment();
        this.gameoverFragment = GameoverFragment.newInstance(0);
        this.settingsFragment = new SettingsFragment();

        changePage(0);

    }

    @Override
    public void changePage(int i) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (i == 0) {
            FragmentManager fm = getSupportFragmentManager();
            if (!fm.isDestroyed())
                for (int j = 0; j < fm.getBackStackEntryCount(); ++j) {
                    fm.popBackStack();
                }

            ft.replace(R.id.fragment_container, this.homeFragment).addToBackStack(null);
        } else if (i == 1) {
            ft.replace(R.id.fragment_container, this.mainFragment).addToBackStack(null);
        } else if (i == 2) {
            FragmentManager fm = getSupportFragmentManager();
            if (!fm.isDestroyed())
                for (int j = 0; j < fm.getBackStackEntryCount(); ++j) {
                    fm.popBackStack();
                }
            ft.replace(R.id.fragment_container, this.gameoverFragment).addToBackStack(null);
        } else if (i == 3) {
        } else if (i == 4) {
            ft.replace(R.id.fragment_container, this.settingsFragment).addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("countMyASD", String.valueOf(count));
        if (count == 0) {
            super.onBackPressed();
            super.onBackPressed();
        } else if (count == 1) {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public void setScore(int score) {
        this.gameoverFragment = GameoverFragment.newInstance(score);
    }

    @Override
    public void closeApplication() {
        this.finish();
    }
}