package com.unava.dia.trellolightmvi.ui.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        bind()
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun bind()

    fun showToast(message: String, appContext: Context) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    fun restartActivity(appContext: AppCompatActivity) {
        val intent = Intent(appContext, MainActivity::class.java)
        appContext.startActivity(intent)
        appContext.finish()
    }

    fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
        if (addStack) {
            this.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                            R.id.main_fragment_container,
                            fragment
                    ).commit()
        } else {
            this.supportFragmentManager.beginTransaction()
                    .replace(
                            R.id.main_fragment_container,
                            fragment
                    ).commit()
        }
    }

}