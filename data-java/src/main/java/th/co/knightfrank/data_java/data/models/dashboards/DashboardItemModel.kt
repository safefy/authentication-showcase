package th.co.knightfrank.data_java.data.models.dashboards

data class DashboardItemModel(
        val announceID: Int? = null,
        //val createAt: String? = null,
        val detail: String? = null,
        val endDate: String? = null,
        val imageUrlArrays: List<String>? = listOf<String>(),
        val isAccept: Boolean? = false,
        val isPin: Boolean? = false,
        val isRead: Boolean? = false,
        val location: String? = null,
        val startDate: String? = null,
        val title: String? = null
        //val updateAt: String? = null
)