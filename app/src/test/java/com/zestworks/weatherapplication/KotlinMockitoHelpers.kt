package com.zestworks.weatherapplication

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
 * when null is returned.
 */
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()


/**
 * Returns Mockito.eq() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 *
 * Generic T is nullable because implicitly bound by `Any?`.
 */
fun <T> eq(obj: T): T = Mockito.eq<T>(obj)