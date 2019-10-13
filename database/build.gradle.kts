plugins {
    id(PluginIds.androidLibrary)
    kotlin(PluginIds.android)
    kotlin(PluginIds.kapt)
}

android {
    configureAndroidLibraryExtension()
    minifyRelease()
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.androidxRoomRuntime)
    implementation(Libs.joda)
    implementation(Libs.andutils)

    implementation(project(Modules.common))
    implementation(project(Modules.commonj))
    implementation(project(Modules.domain))

    kapt(Libs.androidxRoomCompiler)
}