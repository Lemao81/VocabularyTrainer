buildscript {
    repositories {
        google()
        jcenter()
        maven(Urls.mavenLocalInternal)
    }

    dependencies {
        classpath(Plugins.androidBuild)
        classpath(Plugins.kotlin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(Urls.mavenLocalInternal)
    }

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