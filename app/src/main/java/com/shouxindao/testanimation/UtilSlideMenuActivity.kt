package com.shouxindao.testanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shouxindao.testanimation.R.id.*
import com.xuyao.prancelib.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_util_slide_menu.*

class UtilSlideMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_util_slide_menu)
        slideView.setMenuWidth((ScreenUtils.getScreenWidth(this) * 0.8f))
        open.setOnClickListener {

            if (slideView.isOpen) slideView.close() else slideView.open()
        }

        close.setOnClickListener {
            if(slideView.isOpen) slideView.close() else slideView.open()
        }
    }


}
