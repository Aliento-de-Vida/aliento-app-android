pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "aliento app"
include(":app")

include(":core:analytics")
include(":core:presentation")
include(":core:common")
include(":core:designsystem")
include(":core:data")
include(":core:domain")

include(":feature:gallery")
include(":feature:campus")
include(":feature:sermons")
include(":feature:settings")
include(":feature:notifications")
include(":feature:prayer")
include(":feature:admin")
include(":feature:church")
include(":feature:donations")
include(":feature:home")

include(":benchmark")
