package com.tamatem.tamatempluswebview.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.tamatem.tamatempluswebview.R
import com.tamatem.tamatempluswebview.common.Constants
import com.tamatem.tamatempluswebview.databinding.FragmentHomePageBinding


class HomePageFragment: Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var browserBackButton: Button
    private lateinit var browserForwardButton: Button
    private lateinit var browseRefreshButton: Button
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getFragmentView(inflater, container)

    private fun getFragmentView(inflater: LayoutInflater, container: ViewGroup?)
            : View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        binding.btnOpenBrowser.setOnClickListener {
            openBrowserModal()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openBrowserModal() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.modal_browser)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        dialog.setCancelable(true)
        dialog.show()

        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val closeButton = dialog.findViewById<Button>(R.id.ivCloseButton)
        webView = dialog.findViewById(R.id.webView)
        browserBackButton = dialog.findViewById(R.id.ivBackButton)
        browserForwardButton = dialog.findViewById(R.id.ivForwardButton)
        browseRefreshButton = dialog.findViewById(R.id.ivRefreshButton)

        progressBar.visibility = View.VISIBLE

        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl(Constants.TAMATEM_PLUS_WEBSITE)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                updateBrowserButtonsState()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.GONE
            }
        }
        updateBrowserButtonsState()

        browserBackButton.setOnClickListener {
            if(webView.canGoBack())
                webView.goBack()
        }
        browserForwardButton.setOnClickListener {
            if(webView.canGoForward())
                webView.goForward()
        }
        browseRefreshButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            webView.reload()
        }
        closeButton.setOnClickListener{
            dialog.dismiss()
        }

        /*
        * - Master branch
- Don't use dark mode
        *
        * */
    }

    private fun updateBrowserButtonsState() {
        browserBackButton.isEnabled = webView.canGoBack()
        browserForwardButton.isEnabled = webView.canGoForward()
    }
}