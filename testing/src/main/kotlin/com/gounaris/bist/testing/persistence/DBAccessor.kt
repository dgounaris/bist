package com.gounaris.bist.testing.persistence

interface DBAccessor {
    fun singleFromTable(table: String): Boolean
    fun <T> fetchAllMatchingFromTable(table: String, clazz: Class<T>, matchFilter: (T) -> Boolean) : List<T>
}