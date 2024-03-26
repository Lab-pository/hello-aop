package com.protoseo.helloaop.annotation

import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.TYPE


@Target(TYPE, FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Logging()
