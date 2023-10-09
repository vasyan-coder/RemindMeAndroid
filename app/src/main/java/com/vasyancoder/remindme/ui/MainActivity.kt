package com.vasyancoder.remindme.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.vasyancoder.remindme.R
import com.vasyancoder.remindme.databinding.ActivityMainBinding
import com.vasyancoder.remindme.ui.adapter.ViewPagerAdapter
import com.vasyancoder.remindme.ui.fragment.SecondFragment
import com.vasyancoder.remindme.ui.utils.getMeasuredWidthForNewText


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(this)
        binding.contentMain.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        animateTextChangeNextButton(getString(R.string.next))

                        binding.firstDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.active_dot)
                        binding.secondDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)
                        binding.thirdDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)

//                        animateChangeStateDotsProgressBar(position)
                        binding.fab.visibility = View.GONE
                        binding.nextButton.text = getString(R.string.next)
                        binding.nextButton.setOnClickListener {
                            binding.contentMain.viewPager2.setCurrentItem(1, true)
                        }
                    }

                    1 -> {
                        animateTextChangeNextButton(getString(R.string.next))

                        binding.firstDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)
                        binding.secondDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.active_dot)
                        binding.thirdDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)

                        binding.fab.visibility = View.GONE
                        binding.nextButton.setOnClickListener {
                            binding.contentMain.viewPager2.setCurrentItem(2, true)
                        }
                    }

                    2 -> {
                        animateTextChangeNextButton(getString(R.string.lets_remember))

                        binding.firstDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)
                        binding.secondDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.non_active_dot)
                        binding.thirdDot.background =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.active_dot)

                        binding.nextButton.setOnClickListener {
                            animateFadeOutContentMain()
                            binding.fab.visibility = View.VISIBLE
                            binding.firstDot.visibility = View.GONE
                            binding.secondDot.visibility = View.GONE
                            binding.thirdDot.visibility = View.GONE

                            supportFragmentManager.commit {
                                setCustomAnimations(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out,
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out,
                                )
                                replace(R.id.nav_host_fragment_content_main, SecondFragment())
                            }
                        }
                    }
                }
            }
        })
        binding.contentMain.viewPager2.adapter = adapter

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


    }

    private fun animateChangeStateDotsProgressBar(position: Int) {
        val typedValue1 = TypedValue()
        val typedValue2 = TypedValue()
        theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue1, true)
        val temp = getColor(androidx.appcompat.R.color.primary_material_light)
        theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue2, true)
        val startColor = typedValue1.data

        val endColor = typedValue2.data

        val animator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor,
            endColor
        )

        animator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            binding.secondDot.backgroundTintList = ContextCompat.getColorStateList(this, color)
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }
        })

        animator.duration = 500
        animator.start()
    }

    private fun animateTextChangeNextButton(newText: String) {

        val animatorButton = ValueAnimator.ofInt(
            binding.nextButton.measuredWidth,
            binding.nextButton.getMeasuredWidthForNewText(newText)
        )

        animatorButton.addUpdateListener { animation ->
            val newWidth = animation.animatedValue as Int
            val params = binding.nextButton.layoutParams
            params.width = newWidth
            params.height = binding.nextButton.height
            binding.nextButton.layoutParams = params
        }

        animatorButton.duration = 300
        animatorButton.start()

        if (binding.nextButton.text != newText) {
            val animatorText = ValueAnimator.ofInt(
                0,
                newText.length
            )

            animatorText.addUpdateListener { animation ->
                val newLength = animation.animatedValue as Int
                binding.nextButton.text = newText.substring(0, newLength)
            }

            animatorText.duration = 300
            animatorText.start()
        }
    }

    private fun animateFadeOutContentMain() {
//        val animatorLayout = ValueAnimator.ofFloat(
//            100f,
//            0f
//        )
//
//        animatorLayout.addUpdateListener { animation ->
//            val newValue = animation.animatedValue as Float
//            binding.contentMain.viewPager2.alpha = newValue
//        }
//
//        animatorLayout.duration = 300
//        animatorLayout.start()

        val animatorButton = ObjectAnimator.ofFloat(
            binding.nextButton,
            View.ALPHA,
            1f,
            0f
        )

        animatorButton.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.nextButton.visibility = View.GONE
            }
        })

        animatorButton.duration = 300
        animatorButton.start()

        binding.contentMain.viewPager2.visibility = View.GONE
    }
}