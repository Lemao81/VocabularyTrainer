import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.withType

fun configureAndroidAppExtension(android: BaseAppModuleExtension, isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension(android)
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(android, isWithMinifiedConfig)

        val devFlavor = android.productFlavors.getByName(Flavors.dev)
        devFlavor.applicationIdSuffix = ".dev"
        devFlavor.resValue(ResValueConstants.Type.STRING, ResValueConstants.Name.APP_NAME, App.devName)
        val prodFlavor = android.productFlavors.getByName(Flavors.prod);
        prodFlavor.resValue(ResValueConstants.Type.STRING, ResValueConstants.Name.APP_NAME, App.prodName)
    }
    android.defaultConfig.applicationId = App.applicationId
}

fun configureAndroidLibraryExtension(android: BaseExtension, isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension(android)
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(android, isWithMinifiedConfig)
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
        testInstrumentationRunner = Constants.androidTestRunner
        javaCompileOptions { annotationProcessorOptions.includeCompileClasspath = true }
    }

    android.dexOptions.preDexLibraries = true
    android.packagingOptions.pickFirst("protobuf.meta")

    android.compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun configureDevProdFlavors(android: BaseExtension, isWithMinifiedConfig: Boolean = true) {
    android.flavorDimensions(Dimensions.main)
    android.productFlavors {
        create(Flavors.dev) {
            if (isWithMinifiedConfig) {
                resConfigs(ResConfigConstants.EN, ResConfigConstants.XHDPI)
            }
        }
        create(Flavors.prod)
    }
}

fun configureKotlinCompileTasks(project: Project) {
    project.tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()

        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

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