package th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom

import android.content.Context
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.listsectionviewadapter.UserRoomListSectionData
import th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.listsectionviewadapter.UserRoomListViewAdapter
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

class ChangeUserRoomListAdapter(listener: (roomID: Int) -> Unit, context: Context) : SectionAdapter() {
    init {
        addSectionType(UserRoomListSectionData::class.java, UserRoomListViewAdapter(listener, context))
    }
}