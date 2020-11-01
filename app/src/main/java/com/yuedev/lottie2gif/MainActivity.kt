package com.yuedev.lottie2gif

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.airbnb.lottie.LottieAnimationView
import com.waynejo.androidndkgif.GifEncoder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private var isFirst = true

    private var lastTime = 0L

    private var lastBitmap: Bitmap? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gifEncoder = GifEncoder()
        val dir = getExternalFilesDir(null)

        val path = dir?.path + "/test.gif"
        val file = File(path)
        if (!file.exists()) file.createNewFile()


        lottieView.setAnimation(R.raw.onboarding_animation)

        lottieView.isDrawingCacheEnabled = true


        lottieView.addAnimatorUpdateListener {


            lastBitmap?.let {
                if (isFirst) {
                    gifEncoder.init(it.width, it.height, path, GifEncoder.EncodingType.ENCODING_TYPE_NORMAL_LOW_MEMORY)
                    isFirst = false

                }

                gifEncoder.encodeFrame(it, (SystemClock.currentThreadTimeMillis() - lastTime).toInt())

            }

            lastTime = SystemClock.currentThreadTimeMillis()
            lastBitmap = lottieView.drawingCache
        }

        lottieView.playAnimation()

        lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Toast.makeText(this@MainActivity, "Done! :${path}", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })


//


    }
}