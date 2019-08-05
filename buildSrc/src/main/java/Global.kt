import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

fun disableLintTasks(project: Project) {
    project.tasks.whenTaskAdded {
        if (this.name.startsWith("lint")) {
            this.enabled = false
        }
    }
}

fun ignoreReleaseBuild(project: Project) {
    project.afterEvaluate {
        val android = this.extensions.findByName(Extensions.android) as? BaseExtension
        android?.variantFilter {
            if (this.buildType.name == BuildTypes.release) {
                this.setIgnore(true)
            }
        }
    }
}

fun configureAndroidExtension(android: BaseExtension) {
    android.compileSdkVersion(Android.compileSdkVersion)
    android.defaultConfig {
        versionCode = App.versionCode
        versionName = App.versionName
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        multiDexEnabled = true
        testInstrumentationRunner = Const.androidTestRunner
        javaCompileOptions { annotationProcessorOptions.includeCompileClasspath = true }
    }

    android.dexOptions.preDexLibraries = true
    android.packagingOptions.pickFirst("protobuf.meta")

    android.compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun minifyRelease(android: BaseExtension) {
    android.buildTypes {
        getByName(BuildTypes.release) {
            isMinifyEnabled = true
            proguardFiles(android.getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

fun optimizeBuildTime(project: Project, android: BaseExtension) {
    if (project.hasProperty("devBuild")) {
        android.splits {
            abi.isEnable = false
            density.isEnable = false
        }
        android.aaptOptions.cruncherEnabled = false
    }
}

fun configureAppDevProdFlavors(android: BaseExtension, isWithMinifiedConfig: Boolean = true) {
    android.flavorDimensions(Dimensions.main)
    android.productFlavors {
        create(Flavors.dev) {
            applicationIdSuffix = ".dev"
            if (isWithMinifiedConfig) {
                resConfigs("en", "xhdpi")
            }
        }
        create(Flavors.prod) {
            applicationIdSuffix = ".prod"
        }
    }
}

fun configureLibraryDevProdFlavors(android: BaseExtension, isWithMinifiedConfig: Boolean = true) {
    android.flavorDimensions(Dimensions.main)
    android.productFlavors {
        create(Flavors.dev) {
            if (isWithMinifiedConfig) {
                resConfigs("en", "xhdpi")
            }
        }
        create(Flavors.prod)
    }
}