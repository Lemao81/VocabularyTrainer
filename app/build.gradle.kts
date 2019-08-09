plugins {
    kotlin(PluginIds.android)
    id(PluginIds.androidApplication)
    id(PluginIds.kotlinAndroidExtensions)
    id(PluginIds.kotlinSerialization)
}

android {
    configureAndroidExtension(this)
    defaultConfig.applicationId = App.applicationId
    minifyRelease(this)
    optimizeBuildTime(project, this)
    configureAppDevProdFlavors(this)
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.androidxConstraintLayout)
    implementation(Libs.andutils)
    implementation(Libs.resutils)
    implementation(Libs.androidxNavigationFragment)
    implementation(Libs.androidxNavigationUi)
    implementation(Libs.androidxLifecycleViewModel)
    implementation(Libs.koinAndroidxViewModel)
    implementation(Libs.joda)
    implementation(Libs.kotlinSerialization)

    implementation(project(Modules.common))
    implementation(project(Modules.database))
}
