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
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.vasyancoder.remindme.NoteApp
import com.vasyancoder.remindme.R
import com.vasyancoder.remindme.databinding.ActivityMainBinding
import com.vasyancoder.remindme.databinding.DialogAddingNoteBinding
import com.vasyancoder.remindme.ui.adapter.ViewPagerAdapter
import com.vasyancoder.remindme.ui.fragment.MainFragment
import com.vasyancoder.remindme.ui.utils.getMeasuredWidthForNewText
import com.vasyancoder.remindme.ui.viewmodel.NoteItemViewModel
import com.vasyancoder.remindme.ui.viewmodel.ViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this)[NoteItemViewModel::class.java]
    }

    private val component by lazy {
        (application as NoteApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
                                replace(R.id.nav_host_fragment_content_main, MainFragment())
                            }
                        }
                    }
                }
            }
        })
        binding.contentMain.viewPager2.adapter = adapter

        binding.fab.setOnClickListener { view ->

            // TODO: Implement dialog alert
            // TODO: Create fragment open

            val dialogBinding = DialogAddingNoteBinding.inflate(layoutInflater)

            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle(getString(R.string.adding_note))
                .setView(dialogBinding.root)
                .setPositiveButton(getString(R.string.add_note)) { _, _ ->
                    viewModel.addNote(
                        title = dialogBinding.titleEditText.text.toString(),
                        contentNote = dialogBinding.contentNoteEditText.text.toString()
                    )
                    Snackbar.make(
                        view,
                        getString(R.string.note_saved),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

//            supportFragmentManager.commit {
//                setCustomAnimations(
//                    android.R.anim.fade_in,
//                    android.R.anim.fade_out,
//                    android.R.anim.fade_in,
//                    android.R.anim.fade_out,
//                )
//                replace(R.id.nav_host_fragment_content_main, NoteFragment())
//            }

//            lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.noteIsAdded.collect {
//                        if (it != null) {
//                            if (it) {
//                                Snackbar.make(
//                                    view,
//                                    getString(R.string.note_added_successfully),
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Snackbar.make(
//                                    view,
//                                    getString(R.string.error_adding_note),
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//                    }
//                }
//            }
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