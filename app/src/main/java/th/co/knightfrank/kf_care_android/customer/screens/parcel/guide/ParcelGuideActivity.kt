package th.co.knightfrank.kf_care_android.customer.screens.parcel.guide

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import com.itsronald.widget.ViewPagerIndicator
import kotlinx.android.synthetic.main.activity_parcel_guide.*
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.activities.BaseActivity

class ParcelGuideActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ParcelGuideActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private var mSectionPagerAdapter: ParcelGuidViewPagerAdapter? = null
    private var mViewPager: ViewPager? = null

    override fun getLayoutId(): Int = R.layout.activity_parcel_guide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow_white)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mSectionPagerAdapter = ParcelGuidViewPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.view_pager)
        mViewPager?.adapter = mSectionPagerAdapter

        val indicator: ViewPagerIndicator? = findViewById(R.id.view_pager_indicator)
        indicator?.selectedDotColor = Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.orange)))
        indicator?.dotPadding = 20

        btn_next.setOnClickListener(this)
        btn_previous.setOnClickListener(this)

        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        btn_previous.visibility = View.GONE
                        btn_next.visibility = View.VISIBLE
                        btn_next.text = resources.getText(R.string.parcel_guide_next)
                    }
                    1 -> {
                        btn_previous.visibility = View.VISIBLE
                        btn_next.visibility = View.VISIBLE
                        btn_next.text = resources.getText(R.string.parcel_guide_next)
                    }
                    2 -> {
                        btn_previous.visibility = View.VISIBLE
                        btn_next.visibility = View.VISIBLE
                        btn_next.text = resources.getText(R.string.parcel_guide_finish)
                    }
                }
            }

        })
    }

    override fun onClick(v: View?) {
        val currentPosition = mViewPager?.currentItem

        when (v?.id) {
            R.id.btn_previous -> {
                mViewPager?.currentItem = currentPosition!!.minus(1)
            }
            R.id.btn_next -> {
                if (mViewPager?.currentItem == 2) {
                    this.finish()
                }

                mViewPager?.currentItem = currentPosition!!.plus(1)
            }
        }
    }

}
