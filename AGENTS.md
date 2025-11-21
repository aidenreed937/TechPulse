# TechPulse AGENTS Guide

Short brief so future agents can work quickly on this Kotlin Multiplatform sample (Android, iOS, Desktop, Web, Server).

## Repository Map
- `composeApp/`: Compose Multiplatform UI. Shared UI lives in `src/commonMain/kotlin/com/aidenreed/techpulse/App.kt`; platform entrypoints in `src/{androidMain,iosMain,jvmMain,webMain}`. Add shared resources under `src/commonMain/composeResources/` (generated accessors appear under `composeApp/build/**/generated`; do not edit generated files).
- `shared/`: Common business logic and expect/actual platform info (`Platform.*.kt`, `Greeting.kt`, `Constants.kt` with `SERVER_PORT=8080`). Every target consumes this module.
- `server/`: Ktor Netty service exposing `/` and reusing `Greeting` and `SERVER_PORT`.
- `iosApp/`: SwiftUI shell that embeds the shared Compose UI.
- Root Gradle files and `.npmrc` (uses npmmirror mirrors for npm assets).

## Tooling and Versions
- Kotlin `2.2.20`, Compose Multiplatform `1.9.1`, Android Gradle Plugin `8.11.2`, Ktor `3.3.1` (see `gradle/libs.versions.toml`).
- Always use the Gradle wrapper (`./gradlew` / `.\gradlew.bat`); avoid editing files under `build/` or `composeApp/build/`.

## Common Tasks
- Android debug build: `./gradlew :composeApp:assembleDebug`
- Android emulator install & launch home: start an emulator, then run `./gradlew :composeApp:installDebug` and `adb shell monkey -p com.aidenreed.techpulse 1`
- Desktop run: `./gradlew :composeApp:run`
- Desktop hot reload (fast UI refresh): `./gradlew :composeApp:hotDevJvm --className=com.aidenreed.techpulse.MainKt --funName=main`
- Web dev runs: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` (fast) or `:composeApp:jsBrowserDevelopmentRun`
- Server run: `./gradlew :server:run`
- Tests: `./gradlew :shared:allTests :server:test`

## Formatting and Linting
- Spotless + ktlint are applied to all subprojects via the root `build.gradle.kts`.
- Kotlin sources: `./gradlew spotlessKotlinCheck` / `spotlessKotlinApply`.
- Gradle Kotlin scripts: `./gradlew spotlessKotlinGradleCheck` / `spotlessKotlinGradleApply`.
- Run everything before committing: `./gradlew spotlessCheck` (or `spotlessApply` to auto-fix).

## Conventions and Gotchas
- Package namespace is `com.aidenreed.techpulse`; keep new code under this path.
- Shared constants and greetings live in `shared`; reuse them instead of duplicating in platform modules.
- Platform specifics belong in expect/actual files under the appropriate `*Main` source set; keep `commonMain` platform-agnostic.
- When adding UI assets, place originals in `composeApp/src/commonMain/composeResources` (or platform `res/` for Android) so the resource generator can pick them up.

## Kotlin Multiplatform Reference
- Official guide: https://kotlinlang.org/docs/multiplatform/get-started.html
- This repo follows the standard KMP structure: shared business logic in `shared/` (`commonMain` + platform `*Main`), and platform apps consuming that module.
- `composeApp/` is the main multiplatform UI sample: common UI in `commonMain` with Compose Multiplatform, and entry points under `androidMain`, `iosMain`, `jvmMain`, `webMain`.
- `server/` shows reuse of the same shared code (e.g., `Greeting`, `Constants.SERVER_PORT`) in a Ktor backend.
- `iosApp/` demonstrates integrating the shared Compose UI into a SwiftUI shell, similar to the “shared module + iOS app” setup in the official docs.
- When in doubt about how to extend targets, source sets, or expect/actual declarations, align with the patterns from the official KMP “Getting Started” docs and mirror them in `shared/` and `composeApp/`.
