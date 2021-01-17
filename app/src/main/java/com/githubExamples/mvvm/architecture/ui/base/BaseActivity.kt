package com.githubExamples.mvvm.architecture.ui.base

import android.app.Application
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.lifecycle.LifecycleObserver
import com.githubExamples.mvvm.acrhitecture.R
import com.githubExamples.mvvm.architecture.utils.OFFLINE
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity(), BaseFragment.Callback {


    @LayoutRes
    abstract fun getLayoutId(): Int

    private var snackbar: Snackbar? = null

    @Inject
    lateinit var app: Application


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(getLifeCycleObserver())
    }


    override fun onPause() {
        super.onPause()
        lifecycle.removeObserver(getLifeCycleObserver())

    }

    abstract fun getLifeCycleObserver(): LifecycleObserver


    protected fun showError(rootView: View, message: String) {
        val snackbarText = SpannableStringBuilder()
        snackbarText.bold { appendln(OFFLINE) }
        snackbarText.append(message)

        snackbar = Snackbar.make(rootView, snackbarText, Snackbar.LENGTH_INDEFINITE).apply {
            view.layoutParams = (view.layoutParams as CoordinatorLayout.LayoutParams)
                .apply {
                    setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
                }

        }
        val snackBarView = snackbar?.view
        snackBarView?.setBackgroundColor(ContextCompat.getColor(this, R.color.errorColor))
        val snackBarTextView =
            snackbar?.view?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarTextView?.maxLines = 2
        snackbar?.show()
    }

    protected fun hideError() {
        snackbar?.dismiss()
    }

    abstract fun getParentLayForSnackBar(): View?
    override fun onNotifyError(errorMessage: String) {
        getParentLayForSnackBar()?.let {
            showError(it,message = errorMessage )
        }
    }

    override fun removeErrorsIfAny() {
        hideError()
    }

}




