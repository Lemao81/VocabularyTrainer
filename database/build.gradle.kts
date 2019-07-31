plugins {
    id(PluginIds.androidLibrary)
    kotlin(PluginIds.android)
    kotlin(PluginIds.kapt)
}

android {
    configureAndroidExtension(this)
    minifyRelease(this)
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.androidxRoomRuntime)

    kapt(Libs.androidxRoomCompiler)
}