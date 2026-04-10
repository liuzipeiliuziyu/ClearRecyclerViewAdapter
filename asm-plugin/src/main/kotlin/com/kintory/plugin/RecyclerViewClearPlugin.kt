package com.kintory.plugin

import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RecyclerViewClearPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("recyclerViewClear", RecyclerViewClearExtension::class.java)
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        
        androidComponents.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                RecyclerViewClearFactory::class.java,
                InstrumentationScope.ALL 
            ) { params ->
                params.packageFilters.set(extension.packageFilters)
            }
            variant.instrumentation.setAsmFramesComputationMode(
                FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
            )
        }
    }
}

