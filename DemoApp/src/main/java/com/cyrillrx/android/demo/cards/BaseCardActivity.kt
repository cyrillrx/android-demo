package com.cyrillrx.android.demo.cards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.android.demo.R

/**
 * @author Cyril Leroux
 *         Created 09/08/2021.
 */
abstract class BaseCardActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        findViewById<RecyclerView>(R.id.recycler_view).apply {

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = createLayoutManager()

            // specify an adapter
            adapter = createAdapter()
        }
    }

    abstract fun createLayoutManager(): RecyclerView.LayoutManager

    abstract fun createAdapter(): RecyclerView.Adapter<*>
}
