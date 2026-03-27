import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "org.inklang"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    // Paper API
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // Ink/Quill dependencies (when available)
    // compileOnly("org.inklang:quill-compiler:1.0.0")
    
    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

bukkit {
    name = "InkRuntime"
    version = project.version.toString()
    main = "org.inklang.runtime.bukkit.InkPlugin"
    apiVersion = "1.21"
    author = "inklang"
    description = "Bukkit/Paper runtime for Ink programming language"
    website = "https://inklang.org"
    
    commands {
        register("ink") {
            description = "Ink runtime commands"
            usage = "/<command> [reload|run|scripts]"
            permission = "ink.admin"
        }
    }
    
    permissions {
        register("ink.admin") {
            description = "Admin permissions for Ink runtime"
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("ink.use") {
            description = "Use Ink scripts"
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    relocate("kotlin", "org.inklang.lib.kotlin")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
