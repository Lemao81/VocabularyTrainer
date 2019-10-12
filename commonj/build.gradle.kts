plugins {
    java
    `java-library`
    id(PluginIds.kotlin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.kotlinStd8)
    implementation(Libs.joda)
    implementation(Libs.koinCore)
}