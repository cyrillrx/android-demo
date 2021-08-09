package com.cyrillrx.android.demo.cards

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cyrillrx.android.demo.R

/**
 * @author Cyril Leroux
 *         Created 11/12/2014.
 */
class HorizontalCardGridActivity : BaseCardActivity() {

    override val layoutResId = R.layout.activity_card_list_h

    override fun createLayoutManager() = GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false)

    override fun createAdapter() = CardAdapter(DataSource.DATA_SET, CardAdapter.ScrollType.HORIZONTAL)
}
