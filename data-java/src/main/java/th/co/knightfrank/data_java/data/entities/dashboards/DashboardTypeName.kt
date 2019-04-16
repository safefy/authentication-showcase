package th.co.knightfrank.data_java.data.entities.dashboards


enum class DashboardTypeName(val identifier: String) {
    EMPTY(""),
    ALL("All"),
    ANNOUNCE("Announce"),
    JOB_REQUEST("JobRequest"),
    PARCEL("Parcel"),
    BILLING("Billing"),
    MESSAGE("Message"),
    BILLING_WATER("BillingWater"),
    BILLING_CENTRAL_EXPENSE("BillingCentralExpense");

    companion object {
        fun from(identifier: String): DashboardTypeName {
            return when (identifier) {
                "All" -> ALL
                "Announce" -> ANNOUNCE
                "JobRequest" -> JOB_REQUEST
                "Parcel" -> PARCEL
                "Billing" -> BILLING
                "Message" -> MESSAGE
                "BillingWater" -> BILLING_WATER
                "BillingCentralExpense]" -> BILLING_CENTRAL_EXPENSE
                else -> EMPTY
            }
        }
    }
}