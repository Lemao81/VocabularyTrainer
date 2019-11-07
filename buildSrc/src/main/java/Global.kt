import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

fun BaseAppModuleExtension.configureAndroidAppExtension(isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension()
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(isWithMinifiedConfig)
        val devFlavor = productFlavors.getByName(GlobalProductFlavors.dev)
        devFlavor.applicationIdSuffix = ".${GlobalProductFlavors.dev}"
    }
    defaultConfig.applicationId = App.applicationId
}

fun BaseExtension.configureAndroidLibraryExtension(isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension()
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(isWithMinifiedConfig)
    }
}

fun BaseExtension.configureAndroidExtension() {
    compileSdkVersion(Android.compileSdkVersion)
    defaultConfig {
        versionCode = App.versionCode
        versionName = App.versionName
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        multiDexEnabled = true
        testInstrumentationRunner = Constants.androidTestRunner
        javaCompileOptions { annotationProcessorOptions.includeCompileClasspath = true }
    }

    dexOptions.preDexLibraries = true
    packagingOptions.pickFirst("protobuf.meta")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun BaseExtension.configureDevProdFlavors(isWithMinifiedConfig: Boolean = true) {
    flavorDimensions(Dimensions.main)
    productFlavors {
        create(GlobalProductFlavors.dev) {
            if (isWithMinifiedConfig) {
                resConfigs(ResConfigConstants.EN, ResConfigConstants.DE, ResConfigConstants.XHDPI)
            }
        }
        create(GlobalProductFlavors.prod)
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
        if (name.startsWith("lint")) {
            enabled = false
        }
    }
}

fun ignoreReleaseBuild(project: Project) {
    project.afterEvaluate {
        val android = extensions.findByName(Extensions.android) as? BaseExtension
        android?.variantFilter {
            if (buildType.name == BuildTypes.release) {
                setIgnore(true)
            }
        }
    }
}

fun BaseExtension.minifyRelease() {
    buildTypes {
        getByName(BuildTypes.release) {
            isMinifyEnabled = true
            proguardFiles(this@minifyRelease.getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

fun BaseExtension.optimizeBuildTime(project: Project) {
    if (project.hasProperty("devBuild")) {
        splits {
            abi.isEnable = false
            density.isEnable = false
        }
        aaptOptions.cruncherEnabled = false
    }
}

inline fun <reified T> BaseExtension.addBuildConfigField(name: String, value: T) {
    buildTypes.forEach { it.buildConfigField(getTypeName<T>(), name, value.toString()) }
}

inline fun <reified T> BaseExtension.addDebugBuildConfigField(name: String, value: T) {
    buildTypes.findByName(BuildTypes.debug)?.buildConfigField(getTypeName<T>(), name, value.toString())
}

inline fun <reified T> BaseExtension.addDevBuildConfigField(name: String, value: T) {
    productFlavors.findByName(GlobalProductFlavors.dev)?.buildConfigField(getTypeName<T>(), name, value.toString())
}

inline fun <reified T> getTypeName(): String? {
    return when {
        T::class == Int::class -> "int"
        T::class == Float::class -> "float"
        else -> T::class.simpleName
    }
}