

plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version "1.4.1"
}

group = "com.example"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven("https://maven.wagyourtail.xyz/releases")
}

val client by sourceSets.creating

tasks.register<Jar>("clientJar") {
    from(client.output)
    archiveClassifier.set("client")
    manifest {
        attributes(
            "JarModAgent-Transforms" to "farn-client.transform"
        )
    }
}

val minecraftVersion = project.properties["minecraft_version"] as String

unimined.minecraft(client) {
    version ("b1.7.3")
    side(this.sourceSet.name) // a trick because we named them based on the sides

    mappings {
        retroMCP("b1.7")
    }

    jarMod {
        transforms("farn-client.transform")
    }

}

dependencies {
}

tasks.withType<JavaCompile> {
    if (JavaVersion.current().isJava9Compatible) {
        options.release.set(8)
    }
}

tasks.jar {
    enabled = false
}