plugins {
    id(PluginIds.androidApplication)
    kotlin(PluginIds.android)
    id(PluginIds.kotlinKapt)
    id(PluginIds.kotlinAndroidExtensions)
    id(PluginIds.kotlinSerialization)
    id(PluginIds.safeargs)
}

android {
    configureAndroidAppExtension()
    minifyRelease()
    optimizeBuildTime(project)
    addDebugBuildConfigField("ENABLE_NOTIFICATION", false)

    dataBinding.isEnabled = true
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.jutils)
    implementation(Libs.andutils)
    implementation(Libs.resutils)
    implementation(Libs.firebaseutils)
    implementation(Libs.androidxAppcompat)
    implementation(Libs.androidxConstraintLayout)
    implementation(Libs.androidxNavigationFragment)
    implementation(Libs.androidxNavigationUi)
    implementation(Libs.androidxLifecycleViewModel)
    implementation(Libs.koinAndroidxViewModel)
    implementation(Libs.joda)
    implementation(Libs.kotlinSerialization)
    implementation(Libs.ankoCommons)

    implementation(project(Modules.common))
    implementation(project(Modules.commonj))
    implementation(project(Modules.domain))
    implementation(project(Modules.database))
}

apply(plugin = PluginIds.gms)
