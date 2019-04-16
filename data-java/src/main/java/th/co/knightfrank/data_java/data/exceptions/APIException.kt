package th.co.knightfrank.data_java.data.exceptions

class APIException(message: String,
                   val throwable: Throwable? = null) : Exception(message)