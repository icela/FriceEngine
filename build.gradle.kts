import org.gradle.api.internal.HasConvention
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.*
import java.nio.file.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

val commitHash by lazy {
	val process: Process = Runtime.getRuntime().exec("git rev-parse --short HEAD")
	process.waitFor(2000L, TimeUnit.MILLISECONDS)
	val output = process.inputStream.use {
		it.bufferedReader().use {
			it.readText()
		}
	}
	process.destroy()
	output.trim()
}

val isCI = !System.getenv("CI").isNullOrBlank()

val comingVersion = "1.8.3"
val packageName = "org.frice"
val kotlinVersion: String by extra

group = packageName
version = if (isCI) "$comingVersion-$commitHash" else comingVersion

buildscript {
	var kotlinVersion: String by extra
	var dokkaVersion: String by extra

	kotlinVersion = "1.2.30"
	dokkaVersion = "0.9.15"

	repositories {
		mavenCentral()
	}

	dependencies {
		classpath(kotlin("gradle-plugin", kotlinVersion))
		classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion")
	}
}

plugins {
	java
	kotlin("jvm") version "1.2.30"
}

allprojects {
	apply {
		plugin("org.jetbrains.dokka")
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

val SourceSet.kotlin
	get() = (this as HasConvention)
		.convention
		.getPlugin(KotlinSourceSet::class.java)
		.kotlin

java.sourceSets {
	"main" {
		java.srcDirs("src")
		kotlin.srcDirs("src")
		resources.srcDirs("res")
	}

	"test" {
		java.srcDirs("test")
		kotlin.srcDirs("test")
	}
}

repositories {
	mavenCentral()
}

configurations {
	create("library")
}

val libraries = Files
	.list(Paths.get("lib"))
	.filter { it.endsWith(".jar") }
	.collect(Collectors.toList()).toTypedArray()

dependencies {
	compile(kotlin("stdlib-jdk8", kotlinVersion))
	"library"(files(libraries))
	configurations.compile.extendsFrom(configurations["library"])
	testCompile("junit", "junit", "4.12")
	testCompile(kotlin("test-junit", kotlinVersion))
}

val jar = tasks["jar"] as Jar
jar.from(*libraries)

val fatJar = task<Jar>("fatJar") {
	classifier = "all"
	description = "Assembles a jar archive containing the main classes and all the dependencies."
	group = "build"
	from(*libraries) // TODO change to all libs
	with(jar)
}

val sourcesJar = task<Jar>("sourcesJar") {
	classifier = "sources"
	description = "Assembles a jar archive containing the source code of this project."
	group = "build"
	from(java.sourceSets["main"].allSource)
}

task("displayCommitHash") {
	group = "help"
	description = "Display the newest commit hash"
	doFirst {
		println("Commit hash: $commitHash")
	}
}

task("isCI") {
	group = "help"
	description = "Check if it's running in a continuous-integration"
	doFirst {
		println(if (isCI) "Yes, I'm on a CI." else "No, I'm not on CI.")
	}
}

