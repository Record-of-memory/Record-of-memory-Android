package kr.co.app.recordOfMemory.src.main.calendar.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.src.main.calendar.CalendarFragment
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.Information

class CalendarOutRecyclerViewAdapter(var items: CalendarFragment.itemListAdapterToList, val itemList: ArrayList<Information>)
    : RecyclerView.Adapter<CalendarOutRecyclerViewHolder>() { init {
    setHasStableIds(true)
}
//    }, Filterable{

    lateinit var calendarOutRecyclerViewHolder: CalendarOutRecyclerViewHolder


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarOutRecyclerViewHolder {
        calendarOutRecyclerViewHolder = CalendarOutRecyclerViewHolder(
            items,
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_calendar, parent, false)
        )

        return calendarOutRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: CalendarOutRecyclerViewHolder, position: Int) {
        holder.bindWithView(itemList[position])
//        holder.itemView.setOnClickListener {
//            val item = filteredList[position]
//            items.getItemId(item)
//        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}