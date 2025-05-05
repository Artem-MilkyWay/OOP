plugins {
    id("java")
    id("jacoco")
    id("application")
}

group = "ru.nsu.mikiyanskiy"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.gradle:gradle-tooling-api:7.3-20210825160000+0000")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.10")
    implementation("org.apache.groovy:groovy-all:5.0.0-alpha-1")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("org.freemarker:freemarker:2.3.32")
    implementation("commons-io:commons-io:2.16.1")
}

tasks.test {
    enabled = false
}

tasks {
    "jacocoTestReport"(JacocoReport::class) {
        reports {
            xml.required.set(true)
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("$buildDir/jacoco/jacocoHtml"))
        }
    }
}

application {
    mainClass.set("org.example.Main")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}