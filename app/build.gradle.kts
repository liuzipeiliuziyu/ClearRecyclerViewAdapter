plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.kintory.plugin.recyclerview-clear")
}

android {
    namespace = "com.kintory.clearrecyclerviewadapter.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kintory.clearrecyclerviewadapter"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

recyclerViewClear {
    packageFilters.add("com.kintory.clearrecyclerviewadapter.app")
}

dependencies {
    implementation(project(":clear-recycler-view-adapter"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
}
