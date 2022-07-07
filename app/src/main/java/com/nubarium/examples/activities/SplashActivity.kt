package com.nubarium.examples.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewTreeObserver
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.nubarium.app.demo.R

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_splash, null, false)

        // we want to keep the logo of the app centered and at the same position the theme sets the logo to.
        // since the theme positions the logo with respect to the entire window, we need to add top margin
        // to our image view which should be equal to the height of the status bar
        view.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                // set the margin to the image

                return true
            }
        })
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        setContentView(view)


        // simulate some async initialization like authenticating user from network
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("ABRO","Va");
            startActivity(Intent(this, MainActivity::class.java))
            finish();
        }, 5000)
    }

}