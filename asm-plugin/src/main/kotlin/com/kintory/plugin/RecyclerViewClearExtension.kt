package com.kintory.plugin

import org.gradle.api.provider.ListProperty

interface RecyclerViewClearExtension {
    val packageFilters: ListProperty<String>
}