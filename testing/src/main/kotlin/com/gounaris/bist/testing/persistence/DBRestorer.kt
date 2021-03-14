package com.gounaris.bist.testing.persistence

interface DBRestorer {
    fun takeSnapshot()
    fun restore()
}