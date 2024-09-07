import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.papermc.paperweight.userdev") version "1.7.2"
    id("io.github.goooler.shadow") version "8.1.7"
}

// Library versions
val paper: String = property("paper") as String
val junit: String = property("junit") as String
val exposed: String = property("exposed") as String
val hikaricp: String = property("hikaricp") as String

// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"

allprojects {
    apply(plugin = "java")

    tasks {
        compileJava.get().options.encoding = Charsets.UTF_8.name()
        javadoc.get().options.encoding = Charsets.UTF_8.name()

        processResources {
            filesMatching("**/plugin.yml") {
                expand("version" to rootProject . version, "name" to rootProject . name)
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            filteringCharset = Charsets.UTF_8.name()
        }

        shadowJar {
            archiveClassifier.set("")
            manifest {
                attributes(mapOf(
                    "Built-By" to System . getProperty("user.name"),
                    "Version" to version,
                    "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ") . format(Date.from(Instant.now())),
                    "Created-By" to "Gradle ${gradle.gradleVersion}",
                    "Build-Jdk" to "${System.getProperty("java.version")} ${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")}",
                    "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}",
                    "Compiled" to(project.findProperty("compiled")?.toString() ?: "true").toBoolean()
                ))
            }
            archiveFileName.set(rootProject.name + "-${version}.jar")
            archiveClassifier.set("")
        }

        compileJava.get().dependsOn(clean)
        build.get().dependsOn(shadowJar)
    }

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/") // Paper
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://libraries.minecraft.net/") // Minecraft repo
        maven("https://maven.enginehub.org/repo/")
        maven("https://jitpack.io") // JitPack
        mavenLocal()
    }

    dependencies {
        paperweight.paperDevBundle(paper)

        compileOnly("io.papermc.paper:paper-api:$paper")

        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.exposed:exposed-core:$exposed")
        implementation("org.jetbrains.exposed:exposed-crypt:$exposed")
        implementation("org.jetbrains.exposed:exposed-dao:$exposed")
        implementation("org.jetbrains.exposed:exposed-jdbc:$exposed")
        implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed")
        implementation("org.jetbrains.exposed:exposed-java-time:$exposed")
        implementation("org.jetbrains.exposed:exposed-json:$exposed")
        implementation("com.zaxxer:HikariCP:$hikaricp")

        testImplementation(kotlin("test"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junit")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
