package com.kintory.plugin

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input

interface RecyclerViewClearParameters : InstrumentationParameters {
    @get:Input
    val packageFilters: ListProperty<String>
}