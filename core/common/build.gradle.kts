plugins {
    id(CoreLibPlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.core.common"
}

dependencies {
    // Modules
    implementation(project(":core:domain"))
}
