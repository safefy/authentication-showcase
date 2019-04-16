package th.co.knightfrank.data_java.data.entities.utils

enum class ImageSourceIDName(val identifier: Int) {
    EMPTY(0),
    ANNOUNCEMENT(1),
    JOB_REQUEST(2),
    PARCEL(3);

    companion object {
        fun from(identifier: Int): ImageSourceIDName {
            return when (identifier) {
                1 -> ANNOUNCEMENT
                2 -> JOB_REQUEST
                3 -> PARCEL
                else -> EMPTY
            }
        }
    }
}