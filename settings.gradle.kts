@file:Suppress("PropertyName")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.polyfrost.cc/releases")
    }
    plugins {
        val pgtVersion = "0.1.28"
        id("cc.polyfrost.multi-version.root") version pgtVersion
    }
}

val mod_name: String by settings

rootProject.name = mod_name
rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.8.9-forge"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}