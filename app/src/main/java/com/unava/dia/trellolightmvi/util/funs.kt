package com.unava.dia.trellolightmvi.util

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.ui.main.MainActivity

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.main_fragment_container,
                fragment
            ).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                fragment
            ).commit()
    }

}