package com.githubExamples.mvvm.architecture.ui.base

import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.githubExamples.mvvm.acrhitecture.R
import java.lang.ref.WeakReference


abstract class BaseNavigator(val fragmentManager: FragmentManager) {
    val mFragmentManager: WeakReference<FragmentManager> = WeakReference(fragmentManager)

    protected fun showFragmentWithAnimation(
        fragment: Fragment,
        fragmentTag: String,
        addToBackStack: Boolean,
        mainContaner: Int
    ) {
        showFragmentWithTag(fragment, fragmentTag, addToBackStack, true, mainContaner)
    }

    private fun showFragmentWithTag(
        fragment: Fragment,
        fragmentTag: String,
        addToBackStack: Boolean,
        withAnimation: Boolean,
        mainContaner: Int
    ) {
        if (mFragmentManager.get() != null) {
            if (!isFragmentOnTop(fragmentTag, mainContaner)) {
                val fragmentTransaction = mFragmentManager.get()!!
                    .beginTransaction()
                if (withAnimation) {
                    fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right
                    )
                }

                fragmentTransaction.replace(mainContaner, fragment, fragmentTag)
                if (addToBackStack) {

                    fragmentTransaction.addToBackStack(fragmentTag)
                }
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    private fun findFragmentByTag(tag: String): Fragment? {
        var fragment: Fragment? = null
        if (mFragmentManager.get() != null) {
            fragment = mFragmentManager.get()!!.findFragmentByTag(tag)
        }
        return fragment
    }

    private fun isFragmentOnTop(
        fragmentTag: String,
        mainContaner: Int
    ): Boolean {
        val fragmentManager = mFragmentManager.get()
        var fragmentOnTop = false
        if (fragmentManager != null && !TextUtils.isEmpty(fragmentTag)) {
            val count = fragmentManager.backStackEntryCount
            fragmentOnTop = if (count > 0) {
                val tag = fragmentManager.getBackStackEntryAt(count - 1).name
                tag != null && tag == fragmentTag
            } else {
                val fragment = fragmentManager.findFragmentById(mainContaner)
                null != fragment && fragmentTag == fragment.tag
            }
        }
        return fragmentOnTop
    }

    protected fun getTopFragmentTag(): String? {
        var tag: String? = null
        if (mFragmentManager.get() != null) {
            val count = mFragmentManager.get()!!.backStackEntryCount
            if (count > 0) {
                val entry = mFragmentManager.get()!!.getBackStackEntryAt(count - 1)
                tag = entry.name
            }
        }
        return tag
    }

    protected fun findFragment(tag: String): Fragment? {
        var fragment: Fragment? = null
        if (mFragmentManager.get() != null) {
            fragment = mFragmentManager.get()!!.findFragmentByTag(tag)
        }
        return fragment
    }

    fun goBack() {
        if (mFragmentManager.get() != null) {
            mFragmentManager.get()!!.popBackStack()
        }
    }


    fun clearStackFromScreen(tag: String) {
        if (mFragmentManager.get() != null) {
            mFragmentManager.get()!!.popBackStack(tag, POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun removeFromBackStack(tag: String?) {
        val fragmentManager = mFragmentManager.get()
        fragmentManager?.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


}
