package kr.co.app.recordOfMemory.util

import android.view.View

interface OnItemLongClickListener {
    fun onItemLongClick(view: View, position: Int): Boolean
}