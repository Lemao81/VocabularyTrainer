plugins {
    id(PluginIds.androidLibrary)
    kotlin(PluginIds.android)
    kotlin(PluginIds.kapt)
    id(PluginIds.kotlinSerialization)
}

android {
    configureAndroidExtension(this)
    minifyRelease(this)
    configureLibraryDevProdFlavors(this)
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.joda)
    implementation(Libs.androidxLifecycleLiveData)
    implementation(Libs.kotlinSerialization)
}