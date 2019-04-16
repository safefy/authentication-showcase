package th.co.knightfrank.data_java.data.entities.billings

enum class PaymentStatusName(val identifier: Int) {
    EMPTY(0),
    OWE(1),
    PAID(2),
    BILLED(3);

    companion object {
        fun from(identifier: Int): PaymentStatusName {
            return when (identifier) {
                1 -> OWE
                2 -> PAID
                3 -> BILLED
                else -> EMPTY
            }
        }
    }
}