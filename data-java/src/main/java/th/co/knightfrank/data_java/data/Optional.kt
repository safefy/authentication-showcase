package th.co.knightfrank.data_java.data

class Optional<out T>
private constructor(private var data: T? = null) {

    companion object {
        fun <T> of(data: T?) = Optional(data)
        fun <T> empty() = Optional<T>(data = null)
    }

    fun get() = data

    fun isEmpty() = data == null
}