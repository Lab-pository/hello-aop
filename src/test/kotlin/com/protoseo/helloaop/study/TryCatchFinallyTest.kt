package com.protoseo.helloaop.study

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TryCatchFinallyTest {

    @Test
    fun `예외가 발생한 경우`() {
        Assertions.assertThatThrownBy { createId(0L) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `정상적인 상황인 경우`() {
        val id = createId(1L)
        Assertions.assertThat(id).isEqualTo(1L)
    }

    private fun createId(id: Long): Long {
        try {
            return check(id)
        } catch (ex: Exception) {
            println("Exception catch")
            throw IllegalArgumentException()
        } finally {
            println("Finally statement")
        }
    }

    private fun check(id: Long): Long {
        if (id <= 0) {
            throw IllegalArgumentException()
        }
        return id
    }
}
