package com.dummyproject.utils

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PeekingLinearLayoutManager : LinearLayoutManager {
    private var ratio = 0.50f
    private val horizontalSpace
        get() = width - paddingStart - paddingEnd

    private val verticalSpace get() = width - paddingTop - paddingBottom

    @Suppress("Unused")
    @JvmOverloads
    constructor(context: Context?, ratio : Float = 0.50F,  @RecyclerView.Orientation orientation: Int = RecyclerView.HORIZONTAL, reverseLayout: Boolean = false) : super(context, orientation, reverseLayout) {
        this.ratio = ratio

    }


    @Suppress("Unused")
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun generateDefaultLayoutParams() = scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) = scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) = scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) = layoutParams.apply {
        when(orientation) {
            HORIZONTAL -> width = (horizontalSpace * ratio).toInt()
            VERTICAL -> height = (verticalSpace * ratio).toInt()
        }
    }
}
