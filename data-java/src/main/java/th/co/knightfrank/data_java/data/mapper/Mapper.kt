package th.co.knightfrank.data_java.data.mapper

interface Mapper<in SOURCE, out TARGET> {
    fun transform(source: SOURCE): TARGET
}