package com.cyrillrx.kitkattv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment

/**
 * Created on 19/11/14.
 */
class ContentBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_fragment)

        val browseFragment =  supportFragmentManager.findFragmentById(R.id.browse_fragment) as ContentBrowserFragment
        browseFragment.headersState = BrowseSupportFragment.HEADERS_ENABLED
        browseFragment.title = getString(R.string.app_name)
        browseFragment.badgeDrawable = resources.getDrawable(R.drawable.ic_launcher)
    }
}
