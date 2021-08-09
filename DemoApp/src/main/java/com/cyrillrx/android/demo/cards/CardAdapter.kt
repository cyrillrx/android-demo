package com.cyrillrx.android.demo.cards

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.android.demo.CustomPopup
import com.cyrillrx.android.demo.R
import com.cyrillrx.logger.Logger

/**
 * @author Cyril Leroux
 *         Created 14/12/2014.
 */
class CardAdapter(
    private val dataSet: Array<String>,
    private val scrollType: ScrollType = ScrollType.VERTICAL,
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    enum class ScrollType { HORIZONTAL, VERTICAL }

    private var customPopup: CustomPopup? = null
    private var touchX = 0f
    private var touchY = 0f
    private val touchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            Logger.debug("ContentViewHolder", "onTouch: $event")
            touchX = event.rawX
            touchY = event.rawY
        }
        false
    }

    private val dispatchTouchListener = OnTouchListener { _, event ->
        val popup = customPopup ?: return@OnTouchListener false

        when (event.action) {
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_MOVE,
            -> popup.isShowing && popup.dispatchTouchEvent(event)
            else -> false
        }
    }

    class ViewHolder(var cardView: CardView) : RecyclerView.ViewHolder(cardView)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(getItemRes(), parent, false)
        //TODO Set the view's size, margins, paddings and layout parameters here
        return ViewHolder(v as CardView)
    }

    private fun getItemRes(): Int = when (scrollType) {
        ScrollType.HORIZONTAL -> R.layout.card_item_h
        ScrollType.VERTICAL -> R.layout.card_item_v
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        (viewHolder.cardView.findViewById<View>(R.id.info_text) as TextView).text = dataSet[position]
        viewHolder.itemView.setOnTouchListener(touchListener)
        viewHolder.itemView.setOnLongClickListener { v -> onLongClick(v) }
    }

    override fun getItemCount(): Int = dataSet.size

    private fun onLongClick(v: View): Boolean {
        val applicationContext = v.context.applicationContext
        val popup = getPopup(v.context).also { customPopup = it }

        // Displaying the popup at the specified location, + offsets.
        if (popup.show(touchX, touchY)) {
            return false
        }

        // After a long click, dispatch down and move actions to the popup
        v.setOnTouchListener(dispatchTouchListener)

        // Prevent parent and its ancestors to intercept touch events
        v.parent.requestDisallowInterceptTouchEvent(true)
        popup.setResultListener(object : CustomPopup.OnDismissListener {
            override fun onDismiss(@IdRes viewId: Int) {
                resetTouch()
                when (viewId) {
                    R.id.btn_1 -> Toast.makeText(applicationContext, "Btn1", Toast.LENGTH_SHORT).show()
                    R.id.btn_2 -> Toast.makeText(applicationContext, "Btn2", Toast.LENGTH_SHORT).show()
                    R.id.btn_3 -> Toast.makeText(applicationContext, "Btn3", Toast.LENGTH_SHORT).show()
                    else -> {
                    }
                }
            }

            private fun resetTouch() {
                // Reset touch Listener
                v.setOnTouchListener(touchListener)
                // Allow parent and its ancestors to intercept touch events
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
        })
        return true
    }

    /**
     * Create the popup and add it to the WindowManager.
     * If the popup already exists, it's not recreated.
     *
     * @return The Contextual popup.
     */
    private fun getPopup(context: Context): CustomPopup = customPopup ?: createPopup(context).also { customPopup = it }

    private fun createPopup(context: Context): CustomPopup {
        val popup = CustomPopup(context)

        (context as Activity).windowManager
            .addView(popup,
                WindowManager.LayoutParams(
                    WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                    WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                    PixelFormat.TRANSPARENT
                ))

        return popup
    }
}
