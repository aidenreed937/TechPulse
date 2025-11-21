plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.spotless) apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint().editorConfigOverride(
                mapOf(
                    // Allow uppercase function names when annotated, e.g. @Composable / @Preview
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable,Preview",
                    // So we can keep Compose-style wildcard imports and main.kt-style filenames
                    "disabled_rules" to "no-wildcard-imports,filename"
                )
            )
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint()
        }
    }
}
