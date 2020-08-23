package com.example.asday16

import android.animation.*
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.*
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val menus:Array<ImageView> by lazy {
        arrayOf(menu1,menu2,menu3,menu4,menu5,menu6)
    }

    private var isPopup: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Click the red button -> popup menu/ Take the menu
        menuBtn.setOnClickListener {

            for ((i,item) in menus.withIndex()){
               /**
                 // Rotate animation
                //Translate animation
                //AnimationSet -> (rotate,translate)
                AnimationSet(true).apply {
                    addAnimation(loadRotateAnim())
                    addAnimation(loadTranslateAnim(i,isPopup))
                    duration = 1000
                    fillAfter = true
                    //插值器
//                    interpolator = CycleInterpolator(2f)
//                    interpolator = AccelerateDecelerateInterpolator()
                    interpolator = BounceInterpolator()
//                    interpolator = LinearInterpolator()
                    item.startAnimation(this)
                }
                */


                //ObjectAnimator  多个值  1f->0.5f->1f
                //ViewPropertyAnimator  一个值  0f
                //rotate 360f
                //translate 100
                val transY = if(isPopup) -((i+1)*(menu1.height +50)).toFloat() else 0f
                item.animate()
                    .rotation(360f)
                    .translationY(transY)
                    .interpolator = BounceInterpolator()
            }

            /**
             * The tween is a visual animation which doesn't change the corresponding attributes to controls
             *      So we need the correct animation : Property Animation
             */
            isPopup = !isPopup
        }

        menu1.setOnClickListener {
            Log.v("dxj","打开相册")
        }

    }

    // Load the rotate animation
    private fun loadRotateAnim() = AnimationUtils.loadAnimation(this,R.anim.rotate_anim)

    //Load the Translate animation
    private fun loadTranslateAnim(index : Int,isPopup:Boolean):TranslateAnimation{
        var startY:Float = 0f
        var endY:Float = 0f
        if(isPopup){
           endY = -((index+1)*(menu1.height+70)).toFloat()
        }else{
            startY = -((index+1)*(menu1.height+70)).toFloat()
        }
        return TranslateAnimation(0f,0f,startY,endY)
    }






    fun test(){
        /**
        The tween is set animation to certain control which needs to provide set and get method
        x y width height
        translationY translationX
        alpha
        rotation
        scaleX scaleY
        Can be accessed by mBtn.x (y,width,height...)
         */

        mBtn.setOnClickListener {
            // Rotate animation
//            ObjectAnimator.ofFloat(mView,"rotationX",(mView.rotation+360f))
//            ObjectAnimator.ofFloat(mView,"rotationY",(mView.rotation+360f))
            ObjectAnimator.ofFloat(mView,"rotation",(mView.rotation+360f)).apply {
                duration =2000
                interpolator =BounceInterpolator()
                addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {

                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {

                    }

                })

                addPauseListener(object : Animator.AnimatorPauseListener {
                    override fun onAnimationPause(p0: Animator?) {
                    }

                    override fun onAnimationResume(p0: Animator?) {
                    }

                })

                addUpdateListener { object : ValueAnimator.AnimatorUpdateListener{
                    override fun onAnimationUpdate(p0: ValueAnimator?) {
//                        p0?.getAnimatedValue()
                    }

                } }

//                repeatCount = -1
                start()
//                cancel()
//                pause()
            }
        }

        mScale.setOnClickListener {
            /**
            val scaleX = ObjectAnimator.ofFloat(mView,"scaleY",0.1f,1.2f,1.0f,1.5f).apply {
            duration =1000
            }
            val scaleY = ObjectAnimator.ofFloat(mView,"scaleX",0.1f,1.2f,1.0f,1.5f).apply {
            duration =1000
            }

            AnimatorSet().apply {
            //                playSequentially(scaleX,scaleY)
            playTogether(scaleX,scaleY)
            start()
            }
             */

            val holderX = PropertyValuesHolder.ofFloat("scaleX",0.1f,1.2f,1.0f,1.5f)

            val holderY = PropertyValuesHolder.ofFloat("scaleY",0.1f,1.2f,1.0f,1.5f)

            ObjectAnimator.ofPropertyValuesHolder(mView,holderX,holderY).apply {
                duration = 1500
                start()
            }
        }

        mAlpha.setOnClickListener {
            ObjectAnimator.ofFloat(mView,"alpha",1f,0f,0f,1f).apply {
                duration = 1500
                start()
            }

        }

        mTranslate.setOnClickListener {
            /**
            val transY = ObjectAnimator.ofFloat(mView,"translationY",0f,200f,-250f,200f,0f).apply {
            duration = 1000
            }

            val transX = ObjectAnimator.ofFloat(mView,"translationX",0f,200f,-250f,200f,0f).apply {
            duration = 2000
            }

            AnimatorSet().apply {
            playTogether(transX,transY)
            }
             */

            /**
            val holderY = PropertyValuesHolder.ofFloat("translationY",0f,200f,-250f,200f,0f)
            val holderX = PropertyValuesHolder.ofFloat("translationX",0f,200f,-250f,200f,0f)

            ObjectAnimator.ofPropertyValuesHolder(mView,holderX,holderY).apply {
            duration = 1500
            start()
            }
             */

        }

        /**
        ViewPropertyAnimator & ObjectAnimator/AnimatorSet
        使用方便
        值单一                       多个值
        同时进行                 控制顺序或者同时
         */

        mConcise.setOnClickListener {
            /**
            // Use the concise version of ObjectAnimator -> ViewPropertyAnimator
            mView.animate().rotation(360f)

            mView.animate().alpha(0f)
            mView.animate().translationX(100f)
            mView.animate().translationY(100f)

            mView.animate().translationY(100f).translationX(100f).apply {
            duration = 1000
            }

            mView.animate().scaleX(0.5f).scaleY(0.5f).duration = 1000
             */

            mView.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .alpha(0.3f)
                .rotation(360f)
                .translationX(300f)
                .translationY(300f)
                .duration =2000

            /**
            // ValueAnimator 动画过程中各阶段的具体数据 -> 自定义控件
            ValueAnimator.ofFloat(0f,100f).apply {
            duration =1000
            addUpdateListener(object : ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(p0: ValueAnimator?) {
            Log.v("dxj","${p0.animatedValue}")
            }
            })
            start()
            }
             */
        }

    }
}