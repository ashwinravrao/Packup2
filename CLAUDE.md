# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Packup 2 is an Android home inventory and box tracker application built with Kotlin and Jetpack Compose. This is a complete rewrite of the original Packup app, focused on clean architecture, performance, and usability. The project is licensed under GPLv3.

## Build Commands

### Building the app
```bash
./gradlew build
```

### Running tests
```bash
# Run all tests
./gradlew test

# Run tests for a specific module
./gradlew :domain:test
./gradlew :data:test
./gradlew :feature:intake:test

# Run instrumentation tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

### Lint
```bash
# Run lint checks
./gradlew lint

# Auto-fix lint issues
./gradlew lintFix
```

### Clean build
```bash
./gradlew clean
```

### Assemble APK
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

## Architecture

### Module Structure

The project follows **Clean Architecture** principles with clear separation of concerns:

- **`:app`** - Application entry point, navigation, and dependency injection setup
  - Contains `PackupApplication` (Hilt app)
  - Navigation logic using Jetpack Navigation Compose with type-safe routes
  - Coordinates all feature modules

- **`:domain`** - Business logic layer (pure Kotlin, framework-agnostic)
  - Domain models (`Item`, `CompositionState`, `Measurement`, etc.)
  - Repository interfaces (defined here, implemented in `:data`)
  - Use cases for business operations (e.g., `SaveItemUseCase`, `GetItemsUseCase`, `ValidateFieldUseCase`)
  - No Android dependencies

- **`:data`** - Data layer implementation
  - Implements repository interfaces from `:domain`
  - Room database setup (`PackupDatabase`, DAOs)
  - Data models and mappers (domain ↔ data conversion)
  - Type converters for Room (Uri, CompositionState, Measurement)
  - Uses Hilt modules for database and repository provision

- **`:feature:*`** - Feature modules (UI + ViewModels)
  - **`:feature:main`** - Main screen with item list
  - **`:feature:intake`** - Item creation/editing form with validation
  - **`:feature:camera`** - Camera screen for capturing item photos
  - **`:feature:common`** - Shared UI components and theme
  - Each feature has interface-based ViewModels with both Real and Fake implementations
  - ViewModels depend on domain use cases, not repositories directly

- **`:util`** - Shared utilities (e.g., file operations)

### Key Patterns

**Dependency Flow**:
- `:app` → `:feature:*` → `:domain` ← `:data`
- Features depend on domain, data implements domain interfaces
- No circular dependencies between modules

**ViewModel Pattern**:
- Each feature defines a ViewModel interface (e.g., `IntakeScreenViewModel`)
- `RealXViewModel` - Production implementation using Hilt and use cases
- `FakeXViewModel` - Test/preview implementation for Compose previews
- ViewModels expose `StateFlow` for reactive UI updates

**Use Case Pattern**:
- All business operations go through use cases
- Use cases are injected via Hilt
- Single responsibility - each use case does one thing

**Repository Pattern**:
- Interfaces in `:domain`, implementations in `:data`
- `ItemRepository` interface → `LocalItemRepository` implementation
- Uses Kotlin coroutines and Flow for async operations

**Dependency Injection**:
- Dagger Hilt throughout
- KSP for annotation processing
- Modules in `:data` for database and repository provision
- ViewModels use Hilt's ViewModel integration

### Navigation

Type-safe navigation using Jetpack Navigation Compose with kotlinx.serialization:
- Routes defined in `NavRoute` (sealed interface)
- `PackupNavHost` coordinates all navigation graphs
- Feature modules provide graph builder extensions (in `GraphBuilderExtensions.kt`)
- Navigation flows: Main → Camera → Intake → Main

### Database

Room database with:
- DAO pattern for data access
- Type converters for complex types (Uri, enums, custom classes)
- Mappers convert between data entities and domain models
- Flow-based reactive queries

## Testing

- Unit tests should go in `src/test/` directories within each module
- Instrumented tests in `src/androidTest/` directories
- Use Hilt testing dependencies where needed (`hilt-android-testing`)
- Example test file: `feature/intake/src/test/java/com/ashwinrao/packup/intake/DirtyDesignatorTest.kt`

## Code Style

- Follow Kotlin official code style (`kotlin.code.style=official` in gradle.properties)
- All source files include GPLv3 copyright headers
- Copyright format: `Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).`

## Compilation

- **compileSdk**: 36
- **minSdk**: 26
- **targetSdk**: 36
- **JVM Target**: 11
- Uses Gradle version catalogs (libs.versions.toml) for dependency management