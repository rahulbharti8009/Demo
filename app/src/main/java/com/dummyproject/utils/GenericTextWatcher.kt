package com.dummyproject.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class GenericTextWatcher(var context : Context,private val editText: Array<EditText>,
                         private val view: View,
                         private val btnOtpVerify: Button
) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
//        val text: String = editable.toString()
//        when(view.id){
//            R.id.etOtpOne ->{
//                if (text.length == 1)
//                    editText[1].requestFocus()
//            }
//            R.id.etOtpTwo ->{
//                if (text.length == 1) {
//                editText[2].requestFocus()
//            } else if (text.length == 0) {
//                editText[0].requestFocus()
//            }
//            }
//            R.id.etOtpThree ->{
//                if (text.length == 1) {
//                    editText[3].requestFocus()
//                } else if (text.length == 0) {
//                    editText[1].requestFocus()
//                }
//            }
//            R.id.etOtpFour ->{
//                if (text.length == 1) {
//                    editText[3].requestFocus()
//                    btnOtpVerify.isEnabled = true
//                    btnOtpVerify.backgroundTintList = ContextCompat.getColorStateList(context , R.color.red)
//                } else if (text.length == 0) {
//                    editText[2].requestFocus()
//                    btnOtpVerify.isEnabled = false
//                    btnOtpVerify.backgroundTintList = ContextCompat.getColorStateList(context , R.color.black_9)
//                }
//            }
//        }
    }
}