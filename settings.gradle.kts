pluginManagement {
    plugins {
        id("com.android.application") version "7.4.2"
        id("com.google.dagger.hilt.android") version "2.51"
        id("org.jetbrains.kotlin.android") version "1.9.0"
        id("org.jetbrains.kotlin.kapt") version "1.9.0"
        id("org.jetbrains.kotlin.kotlin-dsl") version "1.9.0"
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "LiveWally"
include(":app")
