package com.dummyproject.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun showToast(context: Context, str: String){
    Toast.makeText(context, str, Toast.LENGTH_LONG).show()
}


fun snackbar(context: Context, message: String) {
    val snackbar =
        Snackbar.make(
            (context as Activity).findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
    snackbar.show()
}


fun getRandomNumber(min: Int, max: Int): Int {
    return Random().nextInt(max - min + 1) + min
}


fun hideKeyboard(context: Context) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun printLogs(tag: String, message: String) {
    Log.d(tag, message)
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
        .isConnected
}

