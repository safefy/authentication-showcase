package th.co.knightfrank.data_java.data.entities.users

enum class RoleName(val identifier: Int) {
    EMPTY(0),
    SYSTEM_ADMIN(1),
    MANAGER(2),
    ASSISTANT_MANAGER(3),
    ADMIN(4),
    TECHNICIAN_MANAGER(5),
    TECHNICIAN(6),
    CUSTOMER(7);

    companion object {
        fun from(identifier: Int): RoleName {
            return when (identifier) {
                1 -> SYSTEM_ADMIN
                2 -> MANAGER
                3 -> ASSISTANT_MANAGER
                4 -> ADMIN
                5 -> TECHNICIAN_MANAGER
                6 -> TECHNICIAN
                7 -> CUSTOMER
                else -> EMPTY
            }
        }
    }
}