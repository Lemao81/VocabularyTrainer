plugins {
    id(PluginIds.androidApplication)
    id(PluginIds.kotlinAndroidExtensions)
    kotlin(PluginIds.android)
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

    implementation(project(Modules.common))
}
