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
    implementation(Libs.androidxLifecycleLiveData)

    implementation(project(Modules.commonj))
}