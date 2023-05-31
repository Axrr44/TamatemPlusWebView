package com.tamatem.tamatempluswebview.presentation.fragments

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tamatem.tamatempluswebview.R
import com.tamatem.tamatempluswebview.databinding.FragmentSplashScreenBinding

//This is just a nice touch to show Tamatem logo in the beginning of the app.
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getFragmentView(inflater, container)

    private fun getFragmentView(inflater: LayoutInflater, container: ViewGroup?)
            : View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAnimatedTamatemLogo()
        navigateToHomeFragment()
    }

    private fun navigateToHomeFragment() {
        // Just a delay to stop on the Splash Screen before proceeding to the Home Page.
        val timer = object : CountDownTimer(3000, 500) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                findNavController().navigate(R.id.homePageFragment)
            }
        }
        timer.start()
    }

    private fun showAnimatedTamatemLogo() {
        //Animating the logo with Fade-in animation.
        binding.apply {
            val logoFadeAnimation = AnimationUtils.loadAnimation(activity, R.anim.logo_animation)
            ivLogo.startAnimation(logoFadeAnimation)
        }
    }
}