object PluginIds {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinSerialization = "kotlinx-serialization"
    const val android = "android"
    const val kotlinKapt = "kotlin-kapt"
    const val kapt = "kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val gms = "com.google.gms.google-services"
    const val archivaUpload = "custom-archivaupload"
    const val maven = "maven"
    const val safeargs = "androidx.navigation.safeargs.kotlin"
    const val androidJUnit5 = "de.mannodermaus.android-junit5"
}

object Urls {
    const val mavenLocalSnapshot = "http://localhost:8080/repository/snapshots"
    const val mavenLocalInternal = "http://localhost:8080/repository/internal"
    const val fabric = "https://maven.fabric.io/public"
    const val jitpack = "https://jitpack.io"
    const val maven = "https://maven.google.com"
    const val autoDsl = "https://dl.bintray.com/juanchosaravia/autodsl"
}

object Constants {
    const val androidTestRunner = "android.support.test.runner.AndroidJUnitRunner"
    const val androidxTestRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object ResValueConstants {
    object Type {
        const val STRING = "string"
    }

    object Name {
        const val APP_NAME = "app_name"
    }
}

object ResConfigConstants {
    const val EN = "en"
    const val XHDPI = "xhdpi"
}

object Extensions {
    const val android = "android"
}

object BuildTypes {
    const val release = "release"
    const val debug = "debug"
}

object GlobalProductFlavors {
    const val dev = "dev"
    const val prod = "prod"
}

object Paths {
    const val fromProjectBaseFiles = "../base/baseFiles/gradleBaseFiles/"
    const val baseProject = "$fromProjectBaseFiles/base/base-project.gradle"

    const val fromModuleBaseFiles = "../../base/baseFiles/gradleBaseFiles/"
    const val baseAndroidLibrary = "${fromModuleBaseFiles}base/base-android-library.gradle"
    const val baseAndroidLibraryKts = "${fromModuleBaseFiles}base/base-android-library.gradle.kts"
    const val baseJavaLibrary = "${fromModuleBaseFiles}base/base-java-library.gradle"
    const val baseKotlinAndroidExtension = "${fromModuleBaseFiles}base/base-kotlin-android-extension.gradle"
    const val baseKapt = "${fromModuleBaseFiles}base/base-kapt.gradle"
    const val baseDatabinding = "${fromModuleBaseFiles}base/base-databinding.gradle"
    const val baseFlavors = "${fromModuleBaseFiles}base/base-flavors.gradle"
    const val baseArchivaUpload = "${fromModuleBaseFiles}base/base-archivaupload.gradle"
    const val baseArchivaUploadKts = "${fromModuleBaseFiles}base/base-archivaupload.gradle.kts"
    const val baseMaven = "${fromModuleBaseFiles}base/base-maven.gradle"
    const val ktlintGr = "${fromModuleBaseFiles}ktlint.gradle"
    const val ktlint = "${fromModuleBaseFiles}ktlint.gradle.kts"
}