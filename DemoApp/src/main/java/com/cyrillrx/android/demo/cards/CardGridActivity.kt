package com.cyrillrx.android.demo.cards

import androidx.recyclerview.widget.GridLayoutManager
import com.cyrillrx.android.demo.R

/**
 * @author Cyril Leroux
 *         Created 11/12/2014.
 */
class CardGridActivity : BaseCardActivity() {

    override val layoutResId = R.layout.activity_card_list

    override fun createLayoutManager() = GridLayoutManager(this@CardGridActivity, 3)

    override fun createAdapter() = CardAdapter(DataSource.DATA_SET, CardAdapter.ScrollType.VERTICAL)
}
