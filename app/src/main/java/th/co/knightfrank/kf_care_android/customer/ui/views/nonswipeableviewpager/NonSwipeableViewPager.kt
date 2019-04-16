package th.co.knightfrank.kf_care_android.customer.ui.views.nonswipeableviewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}