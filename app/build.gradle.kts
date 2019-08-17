plugins {
    id(PluginIds.androidApplication)
    kotlin(PluginIds.android)
    id(PluginIds.kotlinKapt)
    id(PluginIds.kotlinAndroidExtensions)
    id(PluginIds.kotlinSerialization)
}

android {
    configureAndroidExtension(this)
    defaultConfig.applicationId = App.applicationId
    minifyRelease(this)
    optimizeBuildTime(project, this)
    configureAppDevProdFlavors(this)
    dataBinding.isEnabled = true
    productFlavors.getByName(Flavors.dev).resValue("string", "app_name", "VocabularyTrainer Dev")
    productFlavors.getByName(Flavors.prod).resValue("string", "app_name", "VocabularyTrainer")
}

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
