plugins {
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.1.0"
	id("java")
}

group = "com.mcbanners"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// spring cloud BOM
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2022.0.2"))

	// spring dependencies
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// jwt dependencies
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

tasks {
	bootJar {
		archiveFileName.set("gateway.jar")
	}
}
