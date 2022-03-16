package com.dummyproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dummyproject.R
import com.dummyproject.`interface`.OnKeyBoard
import com.dummyproject.databinding.FragmentMenuBinding
import com.dummyproject.utils.snackbar

class CustomKeyBoardFragment : Fragment(),View.OnClickListener {

//    interface OnKeyBoard {
//        fun onOnDeletePressed(v: View?)
//        fun onKeyPressed(value: Int)
//        fun onOkPressed(v: View?)
//    }

    lateinit var binding: FragmentMenuBinding
    private var typedText = ""
    private var listener: OnKeyBoard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialized()
        listener()
    }

    private fun initialized() {
//        listener = context as OnKeyBoard
    }

    private fun listener() {
        binding.llytOne.setOnClickListener(this)
        binding.llytTwo.setOnClickListener(this)
        binding.llytThree.setOnClickListener(this)
        binding.llytFour.setOnClickListener(this)
        binding.llytFive.setOnClickListener(this)
        binding.llytSix.setOnClickListener(this)
        binding.llytSeven.setOnClickListener(this)
        binding.llytEight.setOnClickListener(this)
        binding.llytNine.setOnClickListener(this)
        binding.llytZero.setOnClickListener(this)
        binding.ok.setOnClickListener(this)
        binding.tvDelete.setOnClickListener(this)
    }

    companion object {
        var instance: CustomKeyBoardFragment? = null

        @JvmStatic
        fun newInstance(): CustomKeyBoardFragment {
            if (instance == null)
                instance = CustomKeyBoardFragment()
            return instance!!
        }
    }

    private fun takeValue(num: String) {
        onKeyPressed(num.toInt())
    }

    private fun appendText(key: String) {
        typedText += key
        binding.etMobileNumber.setText(typedText)
    }

    private fun clearText() {
        var str: String = typedText
        if (str != null && str.length > 0) {
            str = str.substring(0, str.length - 1)
        }
        typedText = str
        binding.etMobileNumber.setText(typedText)
    }

    private fun onOnDeletePressed() {
        clearText()
    }

    private fun onKeyPressed(value: Int) {
        if(  binding.etMobileNumber.text.toString().length != 10)
            appendText(value.toString())
        else {
            snackbar(context!!, "Sorry, Length is over")
        }
    }

    private fun onOkPressed() {
        snackbar(context!!, "Number is :  $typedText")
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.llytOne -> {
                takeValue("1")
            }
            R.id.llytTwo -> {
                takeValue("2")
            }
            R.id.llytThree -> {
                takeValue("3")
            }
            R.id.llytFour -> {
                takeValue("4")
            }
            R.id.llytFive -> {
                takeValue("5")
            }
            R.id.llytSix -> {
                takeValue("6")
            }
            R.id.llytSeven -> {
                takeValue("7")
            }
            R.id.llytEight -> {
                takeValue("8")
            }
            R.id.llytNine -> {
                takeValue("9")
            }
            R.id.llytZero -> {
                takeValue("0")
            }
            R.id.ok -> {
                onOkPressed     ()
            }
            R.id.tvDelete -> {
                onOnDeletePressed()
            }
            else -> { }
        }
    }
}