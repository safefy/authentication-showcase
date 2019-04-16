package th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.listsectionviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import th.co.knightfrank.kf_care_android.R
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class UserRoomListViewAdapter(private val listener: (roomID: Int) -> Unit, private val context: Context) : SectionAdapter.SectionViewAdapter<UserRoomListSectionData, UserRoomListViewAdapter.ViewHolder> {

    override fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_user_room, parent, false))
    }

    override fun bindViewHolder(viewHolder: ViewHolder, item: UserRoomListSectionData) {
        val viewModel = item
        val data = viewModel.userRoomInfoParcel
        val userRoomData = data._roomInfo

        viewHolder.setRoomNo(userRoomData?._roomNo, context)

        viewHolder.setOnClickListener {
            listener.invoke(userRoomData?._roomID!!)
        }
    }

    class ViewHolder(itemView: View) : SectionAdapter.ViewHolder(itemView) {
        private val text_view_room_no: TextView = itemView.findViewById(R.id.text_view_room_no)

        fun setRoomNo(roomNo: String?, context: Context) {
            text_view_room_no.text = String.format(
                    context.getString(R.string.room_name),
                    roomNo
            )
        }

        fun setOnClickListener(listener: () -> Unit) {
            itemView.setOnClickListener {
                listener.invoke()
            }
        }
    }

}