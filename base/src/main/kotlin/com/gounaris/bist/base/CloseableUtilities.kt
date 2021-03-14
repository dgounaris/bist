package com.gounaris.bist.base

inline fun <T: AutoCloseable, R> T.use(block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Throwable) {
        closed = true
        try {
            close()
        } catch (closeE: Throwable) {
            e.addSuppressed(closeE)
        }
        throw e
    } finally {
        if (!closed) close()
    }
}