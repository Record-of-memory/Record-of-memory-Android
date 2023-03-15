package kr.co.app.recordOfMemory.src.main.home.diary2.member.invite.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse

class Diary2InviteMemberRecyclerViewAdapter(val diary2InviteInterface: Diary2InviteInterface, val item: GetUserResponse)
    : RecyclerView.Adapter<Diary2InviteMemberRecyclerViewHolder>() {

    lateinit var diary2InviteMemberRecyclerViewHolder : Diary2InviteMemberRecyclerViewHolder
//    private var unFilteredList = ArrayList<GetUsersResponse>()
//    private var filteredList = ArrayList<GetUsersResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Diary2InviteMemberRecyclerViewHolder {
        diary2InviteMemberRecyclerViewHolder = Diary2InviteMemberRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_invite_member, parent, false)
        )
        return diary2InviteMemberRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: Diary2InviteMemberRecyclerViewHolder, position: Int) {
        holder.bindWithView(item)
        holder.itemButton.setOnClickListener {
            if (holder.itemButton.isEnabled) {
                diary2InviteInterface.onPostDiary2Invite(item.email)
                holder.itemButton.text = "완료"
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint.toString()
//                if(charString == "") {
//                    val filterResults = FilterResults()
//                    filterResults.values = ArrayList<GetMemberRecordResponse>()
//                    return filterResults
//                }
//                else {
//                    filteredList = if (charString.isEmpty()) {
//                        unFilteredList
//                    } else {
//                        val filteringList = ArrayList<GetUsersResponse>()
//                        for (item in unFilteredList) {
//                            if (item.email.contains(charString)) {
//                                filteringList.add(item)
//                            }
//                        }
//                        filteringList
//                    }
//                    val filterResults = FilterResults()
//                    filterResults.values = filteredList
//
//                    return filterResults
//                }
//            }
//
//            override fun publishResults(constraint: CharSequence, results: FilterResults) {
//                filteredList = results.values as ArrayList<GetUsersResponse>
//                println(filteredList)
//                diary2InviteInterface.onGetItemSize(filteredList.size)
//                notifyDataSetChanged()
//            }
//        }
//
//    }
}