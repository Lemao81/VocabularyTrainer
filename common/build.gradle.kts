plugins {
    id(PluginIds.androidLibrary)
    kotlin(PluginIds.android)
    kotlin(PluginIds.kapt)
    id(PluginIds.kotlinSerialization)
}

android {
    configureAndroidLibraryExtension(this)
    minifyRelease(this)
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.kotlinCoroutine)
    implementation(Libs.joda)
    implementation(Libs.androidxLifecycleLiveData)
    implementation(Libs.kotlinSerialization)
}