package com.dummyproject.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.dummyproject.R
import com.dummyproject.custom_view.AdvanceDrawerLayout
import com.dummyproject.databinding.ActivityHomeControllerBinding
import com.dummyproject.fragment.*
import com.dummyproject.utils.BaseActivity
import com.dummyproject.utils.Constant.Companion.contact
import com.dummyproject.utils.Constant.Companion.customKeyboard
import com.dummyproject.utils.Constant.Companion.home
import com.dummyproject.utils.Constant.Companion.mobileTopUp
import com.dummyproject.utils.Constant.Companion.person
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

class HomeControllerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var fm: FragmentManager
    private lateinit var transaction: FragmentTransaction
    private var drawer: AdvanceDrawerLayout? = null
    private var navigationView: NavigationView? = null
    private  var backStack: MutableList<String> = mutableListOf()

    lateinit var binding: ActivityHomeControllerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_controller)
        initialized()
        listener()
        isActiveTabs()
        isUpdateFragment(HomeControllerFragment(), getString(R.string.home))
    }

    // init
    override fun initialized() {
        drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()
        navigationView = binding.navView
        navigationView!!.setNavigationItemSelectedListener(this)
        drawer!!.useCustomBehavior(Gravity.START)
        drawer!!.useCustomBehavior(Gravity.END)
        binding.toolbar.setNavigationIcon(R.drawable.icon_setting)
    }

    override fun listener() {
        navigationView?.getHeaderView(0)!!.findViewById<RelativeLayout>(R.id.rlyProfile)?.setOnClickListener(this)
        navigationView?.getHeaderView(0)!!.findViewById<AppCompatImageView>(R.id.ivBackArrow)?.setOnClickListener(this)
        navigationView?.getHeaderView(0)!!.findViewById<RelativeLayout>(R.id.rlyPaidCallingPriority)?.setOnClickListener(this)
        binding.rltMenu.setOnClickListener(this)
        binding.rltPerson.setOnClickListener(this)
        binding.rltHome.setOnClickListener(this)
        binding.rltTopUp.setOnClickListener(this)
        binding.rltMoney.setOnClickListener(this)
    }

     private fun isUpdateFragment(fragment: Fragment, title: String) {
        fm = supportFragmentManager
        transaction = fm.beginTransaction()

        transaction.apply {
            replace(R.id.frameContainer, fragment)
            addToBackStack(fragment.javaClass.simpleName)
//            addToBackStack(null)
            commit()
        }
        binding.toolbarName.text = title
        backStack.add(title)
    }


    //
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    //  handle back Stack
    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
            return
        }

        if (backStack.size == 1) {
            finish()
            return
        }

        if (backStack.size > 1) {
            try {
                backStack.removeAt(backStack.size - 1)
                if (backStack.size != 0) {
                    fm.popBackStack()
                }
                binding.toolbarName.text = backStack[backStack.size - 1]
                isReflectTabIcon(binding.toolbarName.text.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return
        }
        super.onBackPressed()
    }

    private fun isReflectTabIcon(title: String) {
        when (title) {
            customKeyboard -> {
                customKeyBoard()
            }
            person -> {
                person()
            }
            home -> {
                home()
            }
            mobileTopUp -> {
                topUp()
            }
            contact -> {
                money()
            }
        }
    }


   fun mobileTop(){
      isUpdateFragment(ChooseTopUpFragment(), mobileTopUp)
      topUp2()
  }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBackArrow ->{
                drawer!!.closeDrawer(GravityCompat.START)
            }
            R.id.rlyProfile -> {
//                showToast(this, "hello")
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.rlyPaidCallingPriority -> {
//                showToast(this, "hello")
                startActivity(Intent(this, PaidCallPriorityActivity::class.java))
            }
            R.id.rltMenu -> {
                isUpdateFragment(CustomKeyBoardFragment(), customKeyboard)
                customKeyBoard()
            }
            R.id.rltPerson -> {
                isUpdateFragment(PersonFragment(), person)
                person()
            }
            R.id.rltHome -> {
                isUpdateFragment(HomeControllerFragment(), home)
                home()
            }
            R.id.rltTopUp -> {
                isUpdateFragment(MobileTopUpFragment(), mobileTopUp)
                topUp()
            }
            R.id.rltMoney -> {
                isUpdateFragment(ContactFragment(), contact)
                money()
            }
            else -> {
            }
        }
    }

    private fun isActiveTabs() {
        isInActiveTabs()
        binding.ivHome.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vHome.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)
    }

    private fun isInActiveTabs() {
        binding.ivMenu.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
            setColorFilter(
                ContextCompat.getColor(
                    this@HomeControllerActivity,
                    R.color.color_inactive_icon
                )
            )
        }
        binding.vMenu.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.color_home_bg)

        binding.ivPerson.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
            setColorFilter(
                ContextCompat.getColor(
                    this@HomeControllerActivity,
                    R.color.color_inactive_icon
                )
            )
        }
        binding.vPerson.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.color_home_bg)


        binding.ivHome.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
            setColorFilter(
                ContextCompat.getColor(
                    this@HomeControllerActivity,
                    R.color.color_inactive_icon
                )
            )
        }
        binding.vHome.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.color_home_bg)

        binding.ivTopUp.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
            setColorFilter(
                ContextCompat.getColor(
                    this@HomeControllerActivity,
                    R.color.color_inactive_icon
                )
            )
        }
        binding.vTopUp.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.color_home_bg)

        binding.ivMoney.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
            setColorFilter(
                ContextCompat.getColor(
                    this@HomeControllerActivity,
                    R.color.color_inactive_icon
                )
            )
        }
        binding.vMoney.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.color_home_bg)
    }

    private fun customKeyBoard() {
        binding.rltSearch.visibility = View.VISIBLE
        binding.toolbarName.visibility = View.GONE

        isInActiveTabs()
        binding.ivMenu.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vMenu.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.VISIBLE
        binding.ivArow.visibility = View.VISIBLE

        binding.tvTandC.visibility = View.GONE

    }

    private fun person() {
        binding.rltSearch.visibility = View.VISIBLE
        binding.toolbarName.visibility = View.GONE

        isInActiveTabs()
        binding.ivPerson.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vPerson.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.VISIBLE
        binding.ivArow.visibility = View.VISIBLE

        binding.tvTandC.visibility = View.GONE

    }

    private fun home() {
        binding.rltSearch.visibility = View.VISIBLE
        binding.toolbarName.visibility = View.GONE

        isInActiveTabs()
        binding.ivHome.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vHome.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.VISIBLE
        binding.ivArow.visibility = View.VISIBLE

        binding.tvTandC.visibility = View.GONE

    }

    private fun topUp() {
        binding.rltSearch.visibility = View.GONE
        binding.toolbarName.visibility = View.VISIBLE

        isInActiveTabs()
        binding.ivTopUp.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vTopUp.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.VISIBLE
        binding.ivArow.visibility = View.VISIBLE

        binding.tvTandC.visibility = View.GONE

    }

    private fun topUp2() {
        binding.rltSearch.visibility = View.GONE
        binding.toolbarName.visibility = View.VISIBLE

        isInActiveTabs()
        binding.ivTopUp.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vTopUp.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.GONE
        binding.ivArow.visibility = View.GONE

        binding.tvTandC.visibility = View.VISIBLE
    }



    private fun money() {
        binding.rltSearch.visibility = View.GONE
        binding.toolbarName.visibility = View.VISIBLE
        isInActiveTabs()
        binding.ivMoney.apply {
            background = ContextCompat.getDrawable(context, R.drawable.circle_white)
            setColorFilter(ContextCompat.getColor(this@HomeControllerActivity, R.color.red))
        }
        binding.vMoney.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.red)

        binding.tvEnglish.visibility = View.GONE
        binding.ivArow.visibility = View.GONE

        binding.tvTandC.visibility = View.VISIBLE
    }


}