package th.co.knightfrank.data_java.data.entities.billings

enum class BillingTypeName(val identifier: Int) {
    EMPTY(0),
    BILLING_WATER(1),
    BILLING_CENTRAL_EXPENSE(2);

    companion object {
        fun from(identifier: Int): BillingTypeName {
            return when (identifier) {
                1 -> BILLING_WATER
                2 -> BILLING_CENTRAL_EXPENSE
                else -> EMPTY
            }
        }
    }
}