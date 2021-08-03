package com.fakhry.loonly.core.utils

import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor

class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
) {
    fun diskIO(): Executor = diskIO
    fun networkIO(): Executor = networkIO
}
