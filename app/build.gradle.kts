plugins {
    id(PluginIds.androidApplication)
    kotlin(PluginIds.android)
    id(PluginIds.kotlinKapt)
    id(PluginIds.kotlinAndroidExtensions)
    id(PluginIds.kotlinSerialization)
    id(PluginIds.safeargs)
}

android {
    configureAndroidAppExtension(this)
    minifyRelease(this)
    optimizeBuildTime(project, this)

    dataBinding.isEnabled = true
}

configureKotlinCompileTasks(this)

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.jutils)
    implementation(Libs.andutils)
    implementation(Libs.resutils)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.androidxConstraintLayout)
    implementation(Libs.androidxNavigationFragment)
    implementation(Libs.androidxNavigationUi)
    implementation(Libs.androidxLifecycleViewModel)
    implementation(Libs.koinAndroidxViewModel)
    implementation(Libs.joda)
    implementation(Libs.kotlinSerialization)

    implementation(project(Modules.common))
    implementation(project(Modules.database))
}
