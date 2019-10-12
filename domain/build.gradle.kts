plugins {
    java
    `java-library`
    id(PluginIds.kotlin)
    id(PluginIds.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.jutils)
    implementation(Libs.kotlinStd8)
    implementation(Libs.joda)
    implementation(Libs.kotlinSerialization)
    implementation(Libs.koinCore)

    implementation(project(Modules.commonj))

    testImplementation(Libs.jUnit5Api)
    testImplementation(Libs.jUnit5Params)
    testImplementation(Libs.mockk)
    testImplementation(Libs.kotlinCoroutine)
    testImplementation(Libs.assertJ)
    testRuntimeOnly(Libs.jUnit5Engine)
}