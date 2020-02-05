package com.example.viewstatemvp.model.network

data class Music(val next: String, val results: Array<Results>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Music

        if (next != other.next) return false
        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = next.hashCode()
        result = 31 * result + results.contentHashCode()
        return result
    }
}

data class Results(
    val image: Image,
    val price: String,
    val name: String,
    val currency: Currency
) {
    var res_id: Int = 0
}

data class Image(val width: String, val url: String, val height: String) {
    var image_id: Int? = null
}

data class Currency(val id: String) {
    var cur_id: Int? = null
}