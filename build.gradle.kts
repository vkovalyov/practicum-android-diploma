// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("convention.detekt")
    id("androidx.room") version "2.8.0" apply false
    id ("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}
