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
    val springBootEurekaVersion = properties["springBootEurekaVersion"] as? String

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:${springBootEurekaVersion}")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${springBootEurekaVersion}")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
        mainClass = "vitaliiev.bookstore.BookStoreGateway"
    }
}
