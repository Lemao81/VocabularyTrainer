buildscript {
    repositories {
        google()
        jcenter()
        maven(Urls.mavenLocalInternal)
    }

    dependencies {
        classpath(Plugins.androidBuild)
        classpath(Plugins.kotlin)
        classpath(Plugins.kotlinSerialization)
        classpath(Plugins.safeArgs)
        classpath(Plugins.gms)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(Urls.mavenLocalInternal)
    }

    configureKotlinCompileTasks(this)
    disableLintTasks(this)
    ignoreReleaseBuild(this)
}

subprojects {
    apply(from = Paths.ktlint)
}

tasks.create<Delete>("clean") {
    group = "Cleanup"
    doLast { delete(rootProject.buildDir) }
}