package com.cyrillrx.android.demo.cards

import androidx.recyclerview.widget.LinearLayoutManager
import com.cyrillrx.android.demo.R

/**
 * @author Cyril Leroux
 *         Created 11/12/2014.
 */
class CardListActivity : BaseCardActivity() {

    override val layoutResId = R.layout.activity_card_list_v

    override fun createLayoutManager() = LinearLayoutManager(this@CardListActivity)

    override fun createAdapter() = CardAdapter(DataSource.DATA_SET)
}
