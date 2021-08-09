package com.cyrillrx.android.demo.actionbar

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cyrillrx.android.demo.R
import com.cyrillrx.android.widget.scroll.ObservableScrollView
import com.cyrillrx.android.widget.scroll.OnScrollChangedListener
import kotlin.math.min

/**
 * @author Cyril Leroux
 *         Created 22/11/2014.
 */
class FadingActionBarActivity : AppCompatActivity(), OnScrollChangedListener {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fading_action_bar)

        setAlpha(0)

       findViewById<ObservableScrollView>(R.id.scroll_view).onScrollChangedListener = this
    }

    override fun onScrollChanged(scrollView: ScrollView, x: Int, y: Int, oldX: Int, oldY: Int) {
        setAlpha(min(255, y))
    }

    private fun setAlpha(alphaValue: Int) {
        val color = ColorDrawable().apply {
            color = ContextCompat.getColor(this@FadingActionBarActivity, R.color.nav_primary)
            alpha = alphaValue
        }
        supportActionBar?.setBackgroundDrawable(color)
    }
}
