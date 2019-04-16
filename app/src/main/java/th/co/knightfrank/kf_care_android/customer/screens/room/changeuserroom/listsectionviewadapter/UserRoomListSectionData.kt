package th.co.knightfrank.kf_care_android.customer.screens.room.changeuserroom.listsectionviewadapter

import android.content.Context
import th.co.knightfrank.data_java.data.parcels.UserRoomInfoParcel
import th.co.knightfrank.kf_care_android.customer.ui.recyclerviews.SectionAdapter

data class UserRoomListSectionData(
        val userRoomInfoParcel: UserRoomInfoParcel,
        val context: Context
) : SectionAdapter.SectionData