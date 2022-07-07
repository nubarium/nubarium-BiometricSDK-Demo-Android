package com.nubarium.examples.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;
import com.nubarium.app.demo.R;


public class Intro extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNextArrowColor(Color.RED);
        setColorDoneText(Color.RED);
        setColorSkipButton(Color.BLUE);
        //setColo
        //setNextArrowColor();
        setNavBarColor(Color.MAGENTA);
        setBackArrowColor(Color.MAGENTA);
        setStatusBarColor(Color.MAGENTA);
        setIndicatorColor(Color.BLUE, Color.GREEN);
        //setDoneText("");
        //setSkipText("");
        //setSkipButtonEnabled();
        //setStatusBarColor();
        //setTheme();
        //setBarColor();
/*
        addSlide(AppIntroFragment.createInstance("Welcome!",
                "This is a demo example in java of AppIntro library, with a custom background on each slide!",
                R.drawable.ic_slide1));

        addSlide(AppIntroFragment.createInstance(
                "Clean App Intros",
                "This library offers developers the ability to add clean app intros at the start of their apps.",
                R.drawable.ic_slide2
        ));

        addSlide(AppIntroFragment.createInstance(
                "Simple, yet Customizable",
                "The library offers a lot of customization, while keeping it simple for those that like simple.",
                R.drawable.ic_slide3
        ));

        addSlide(AppIntroFragment.createInstance(
                "Explore",
                "Feel free to explore the rest of the library demo!",
                R.drawable.ic_slide4
        ));
*/
        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

        // Show/hide status bar
        showStatusBar(true);

        //Speed up or down scrolling
        setScrollDurationFactor(2);

        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        setColorTransitionsEnabled(true);

        //Prevent the back button from exiting the slides
        setSystemBackButtonLocked(true);

        //Activate wizard mode (Some aesthetic changes)
        setWizardMode(true);

        //Show/hide skip button
        setSkipButtonEnabled(false);

        //Enable immersive mode (no status and nav bar)
        setImmersiveMode();

        //Enable/disable page indicators
        setIndicatorEnabled(true);

        //Dhow/hide ALL buttons
        setButtonsEnabled(true);
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}