plugins {
    id(PluginIds.androidLibrary)
    kotlin(PluginIds.android)
    kotlin(PluginIds.kapt)
}

android {
    configureAndroidExtension(this)
    minifyRelease(this)
    configureLibraryDevProdFlavors(this)
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.androidxRoomRuntime)
    implementation(Libs.joda)

    implementation(project(Modules.common))

    kapt(Libs.androidxRoomCompiler)
}