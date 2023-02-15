package com.recordOfMemory.src.main.calendar

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.src.main.calendar.recycler.CalendarHeaderRecyclerHolder
import com.recordOfMemory.src.main.calendar.retrofit.models.DiaryName
import com.recordOfMemory.src.main.calendar.retrofit.models.Record

class StickyHeaderItemDecoration(
    private val context: Context,
    private val getItemList: () -> ArrayList<DiaryName>) : RecyclerView.ItemDecoration() {
    private val dividerHeight = dipToPx(context, 0.8f)
    private val dividerPoint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.parseColor("#FFFDFA")
    }

    private val sectionItemWidth: Int by lazy {
        getScreenWidth(context)
    }
    private val sectionItemHeight: Int by lazy {
        dipToPx(context, 30f)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager

        if (layoutManager !is LinearLayoutManager) {
            return
        }
        if (LinearLayoutManager.VERTICAL != layoutManager.orientation) {
            return
        }

        val list = getItemList()
        println("list: $list")
        if(list.isEmpty()) {
            return
        }

        val position = parent.getChildAdapterPosition(view)
        if (0 == position) {
            outRect.top = sectionItemHeight
            return
        }
        val currentModel = getItemList()[position]
        println("currentModel: $currentModel")

        val previousModel = getItemList()[position]
        println("previousModel: $previousModel")

        if(currentModel.diaryId != previousModel.diaryId) {
            outRect.top = sectionItemHeight
        }
        else {
            outRect.top = dividerHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount

        for(i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
            val itemModel = getItemList()[position]
            if(getItemList().isNotEmpty() &&
                (0 == position || itemModel.diaryId != getItemList()[position - 1].diaryId)) {

                val top = childView.top - sectionItemHeight
                drawSectionView(c, itemModel.diaryName, top)
            }
            else {
                drawDivider(c, childView)
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val list = getItemList()

        if(list.isEmpty()) {
            return
        }

        val childCount = parent.childCount

        if(childCount == 0) {
            return
        }

        val firstView = parent.getChildAt(0)
        val position = parent.getChildAdapterPosition(firstView)
        val text = list[position].diaryName
        val itemModel = list[position]

        val condition = itemModel.diaryId != list[position + 1].diaryId

//        if(firstView.bottom <= sectionItemHeight && condition) {
//            drawSectionView(c, text, firstView.bottom - sectionItemHeight)
//        }
//        else {
//        }
            drawSectionView(c, text, if(firstView.bottom <= sectionItemHeight && condition) {
                firstView.bottom - sectionItemHeight
            }
            else {
                0
            })
    }

    private fun drawDivider(canvas: Canvas, childView: View) {
        canvas.drawRect(
            0f,
            (childView.top - dividerHeight).toFloat(), //top
            childView.right.toFloat(), //right
            childView.top.toFloat(), //bottom
            dividerPoint
        )
    }
    private fun drawSectionView(canvas: Canvas, diaryName: String, top: Int) {
        val view = CalendarHeaderRecyclerHolder(context)
        view.setDiaryName(diaryName)

        val bitmap = getViewGroupBitmap(view)
        val bitmapCanvas = Canvas(bitmap)
        view.draw(bitmapCanvas)
        canvas.drawBitmap(bitmap, 0f, top.toFloat(), null)
    }

    private fun getViewGroupBitmap(viewGroup: ViewGroup) : Bitmap {
        val layoutParams = ViewGroup.LayoutParams(sectionItemWidth, sectionItemHeight)
        viewGroup.layoutParams = layoutParams

        viewGroup.measure(
            View.MeasureSpec.makeMeasureSpec(sectionItemWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(sectionItemHeight, View.MeasureSpec.EXACTLY)
        )

        viewGroup.layout(0, 0, sectionItemWidth, sectionItemHeight)

        val bitmap = Bitmap.createBitmap(viewGroup.width, viewGroup.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        viewGroup.draw(canvas)

        return bitmap
    }


    private fun dipToPx(context: Context, dipValue: Float): Int {
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }

    private fun getScreenWidth(context: Context): Int {
        val outMetrics = DisplayMetrics()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(outMetrics)
        } else {
            val windowManager = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            windowManager?.getMetrics(outMetrics)
        }
        return outMetrics.widthPixels
    }

//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDrawOver(c, parent, state)
//
//        val topChild = parent.getChildAt(0) ?: return
//
//        val topChildPosition = parent.getChildAdapterPosition(topChild)
//        if (topChildPosition == RecyclerView.NO_POSITION) {
//            return
//        }
//
//        /* 헤더 */
//        val currentHeader: View = sectionCallback.getHeaderLayoutView(parent, topChildPosition) ?: return
//
//        /* View의 레이아웃 설정 */
//        fixLayoutSize(parent, currentHeader, topChild.measuredHeight)
//
//        val contactPoint = currentHeader.bottom
//
//        val childInContact: View = getChildInContact(parent, contactPoint) ?: return
//
//        val childAdapterPosition = parent.getChildAdapterPosition(childInContact)
//        if (childAdapterPosition == -1)
//            return
//
//        when {
//            sectionCallback.isHeader(childAdapterPosition) ->
//                moveHeader(c, currentHeader, childInContact)
//            else ->
//                drawHeader(c, currentHeader)
//        }
//    }
//
//    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
//        var childInContact: View? = null
//        for (i in 0 until parent.childCount) {
//            val child = parent.getChildAt(i)
//            if (child.bottom > contactPoint) {
//                if (child.top <= contactPoint) {
//                    childInContact = child
//                    break
//                }
//            }
//        }
//        return childInContact
//    }
//
//    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
//        c.save()
//        c.translate(0f, nextHeader.top - currentHeader.height.toFloat())
//        currentHeader.draw(c)
//        c.restore()
//    }
//
//    private fun drawHeader(c: Canvas, header: View) {
//        c.save()
//        c.translate(0f, 0f)
//        header.draw(c)
//        c.restore()
//    }
//
//    /**
//     * Measures the header view to make sure its size is greater than 0 and will be drawn
//     * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
//     */
//    private fun fixLayoutSize(parent: ViewGroup, view: View, height: Int) {
//        val widthSpec = View.MeasureSpec.makeMeasureSpec(
//            parent.width,
//            View.MeasureSpec.EXACTLY
//        )
//        val heightSpec = View.MeasureSpec.makeMeasureSpec(
//            parent.height,
//            View.MeasureSpec.EXACTLY
//        )
//        val childWidth: Int = ViewGroup.getChildMeasureSpec(
//            widthSpec,
//            parent.paddingLeft + parent.paddingRight,
//            view.layoutParams.width
//        )
//        val childHeight: Int = ViewGroup.getChildMeasureSpec(
//            heightSpec,
//            parent.paddingTop + parent.paddingBottom,
//            height
//        )
//        view.measure(childWidth, childHeight)
//        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
//    }
//
//    interface SectionCallback {
//        fun isHeader(position: Int): Boolean
//        fun getHeaderLayoutView(list: RecyclerView, position: Int): View?
//    }
}
