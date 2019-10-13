import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

fun BaseAppModuleExtension.configureAndroidAppExtension(isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension()
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(isWithMinifiedConfig)

        val devFlavor = this.productFlavors.getByName(Flavors.dev)
        devFlavor.applicationIdSuffix = ".dev"
        devFlavor.resValue(ResValueConstants.Type.STRING, ResValueConstants.Name.APP_NAME, App.devName)
        val prodFlavor = this.productFlavors.getByName(Flavors.prod);
        prodFlavor.resValue(ResValueConstants.Type.STRING, ResValueConstants.Name.APP_NAME, App.prodName)
    }
    this.defaultConfig.applicationId = App.applicationId
}

fun BaseExtension.configureAndroidLibraryExtension(isShouldConfigureDevProdFlavors: Boolean = true, isWithMinifiedConfig: Boolean = true) {
    configureAndroidExtension()
    if (isShouldConfigureDevProdFlavors) {
        configureDevProdFlavors(isWithMinifiedConfig)
    }
}

fun BaseExtension.configureAndroidExtension() {
    this.compileSdkVersion(Android.compileSdkVersion)
    this.defaultConfig {
        versionCode = App.versionCode
        versionName = App.versionName
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        multiDexEnabled = true
        testInstrumentationRunner = Constants.androidTestRunner
        javaCompileOptions { annotationProcessorOptions.includeCompileClasspath = true }
    }

    this.dexOptions.preDexLibraries = true
    this.packagingOptions.pickFirst("protobuf.meta")

    this.compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun BaseExtension.configureDevProdFlavors(isWithMinifiedConfig: Boolean = true) {
    this.flavorDimensions(Dimensions.main)
    this.productFlavors {
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

fun BaseExtension.minifyRelease() {
    this.buildTypes {
        getByName(BuildTypes.release) {
            isMinifyEnabled = true
            proguardFiles(this@minifyRelease.getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

fun BaseExtension.optimizeBuildTime(project: Project) {
    if (project.hasProperty("devBuild")) {
        this.splits {
            abi.isEnable = false
            density.isEnable = false
        }
        this.aaptOptions.cruncherEnabled = false
    }
}

inline fun <reified T> BaseExtension.addDebugBuildConfigField(name: String, value: T) {
    this.buildTypes.findByName(BuildTypes.debug)?.buildConfigField(T::class.simpleName, name, value.toString())
}