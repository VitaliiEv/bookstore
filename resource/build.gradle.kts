plugins {
    id("java")
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.google.cloud.tools.jib") version "3.4.0"
}

group = "vitaliiev"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    val lombokVersion = properties["lombokVersion"] as? String
    val springBootEurekaVersion = properties["springBootEurekaVersion"] as? String

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${springBootEurekaVersion}")
    implementation("org.postgresql:postgresql:42.6.0")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.19.1")
    testImplementation("org.testcontainers:junit-jupiter:1.19.1")
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

tasks.test {
    useJUnitPlatform()
}


jib {
    from {
        image = "eclipse-temurin:17-jre-alpine"
        platforms {
            platform {
                architecture = "${findProperty("jibArchitecture") ?: "amd64"}"
                os = "linux"
            }
        }
    }
    to {
        image = "${project.name}:latest"
    }
    container {
        mainClass = "vitaliiev.BookStore"
    }
}
