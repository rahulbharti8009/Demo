package com.dummyproject.utils

import android.Manifest
import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File


object Permission {
    var PERMISSION_REQUEST_CODE = 101
    var CALL_PHONE = 102
    var GALLERY = 1
    var CAMERA = 2
    var MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 3
    val currentAPIVersion = Build.VERSION.SDK_INT

    fun locactionPermission(context: Context): Boolean {

        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_CODE
                    )

                } else {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_CODE
                    )
                }
                return false
            } else {
                return true
            }
        } else
            return true
    }

    fun locationPermission(context: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context,
                        Manifest.permission.READ_CONTACTS
                    )) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        1
                    )

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
        } else return true
        return true
    }

    fun isCameraPermission(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf<String>(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), CAMERA
                )
                return false
            }
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkStoragePermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )) {

                    val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("Write Storage permission is necessary to Download Images and Videos!!!")
                    alertBuilder.setPositiveButton(
                        R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            ActivityCompat.requestPermissions(
                                activity, arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ), MY_PERMISSIONS_REQUEST_WRITE_STORAGE
                            )
                        })
                    val alert: AlertDialog = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE
                    )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    fun checkFolder(context: Context) = getDirectory(context)


    private fun getDirectory(context: Context): File? {
       var file = context.getExternalFilesDir("Demo")
        //        file  = new File(Environment.getExternalStorageState(),"Foody");
        if (!file!!.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(context, "Unable to create directory", Toast.LENGTH_SHORT).show()
            }
            file.mkdirs()
        }else{
            Log.d("folder", "Already Created")
        }
        return file
    }

}