import de.bixilon.kutil.os.Architectures
import de.bixilon.kutil.os.OSTypes
import de.bixilon.kutil.os.PlatformInfo
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("de.bixilon:kutil:1.11")
    }
}

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

group = "dev.isxander"
version = "1.0-SNAPSHOT"

val os = PlatformInfo.OS
val arch = PlatformInfo.ARCHITECTURE

val lwjglNatives = when (os) {
    OSTypes.LINUX, OSTypes.UNIX, OSTypes.SOLARIS -> when (arch) {
        Architectures.AARCH64 -> "linux-arm64"
        Architectures.ARM -> "linux-arm32"
        Architectures.AMD64 -> "linux"
        else -> error("invalid linux arch")
    }
    OSTypes.MAC -> when (arch) {
        Architectures.AARCH64 -> "macos-arm64"
        Architectures.AMD64 -> "macos"
        else -> error("invalid macos arch")
    }
    OSTypes.WINDOWS -> when (arch) {
        Architectures.AMD64 -> "windows"
        Architectures.X86 -> "windows-x86"
        Architectures.AARCH64 -> "windows-arm64"
        else -> error("invalid windows arch")
    }
    OSTypes.OTHER -> error("invalid OS")
}
println("LWJGL Natives: $lwjglNatives")

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(platform("org.lwjgl:lwjgl-bom:${property("lwjglVersion")}"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-$lwjglNatives")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-$lwjglNatives")
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = "natives-$lwjglNatives")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-$lwjglNatives")
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = "natives-$lwjglNatives")
    implementation("org.joml", "joml", "1.10.4")
}

application {
    mainClass.set("dev.isxander.lwjglplayground.main.MainKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}