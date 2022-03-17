plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("java")
}

group = "io.dsql.cdc"
version = "1.0.0"
java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.debezium:debezium-api:1.8.1.Final")
	implementation("io.debezium:debezium-embedded:1.8.1.Final")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.apache.commons:commons-lang3:3.12.0")

	// consumer db
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("mysql:mysql-connector-java:8.0.28")

	// consumer queue
//	implementation("org.springframework.integration:spring-integration-jms")
//	implementation("com.solace.spring.boot:solace-jms-spring-boot-starter:4.1.1")

	dependencies {
		implementation(files("./debezium-connector-yugabytedb2-1.7.0-SNAPSHOT-jar-with-dependencies.jar"))
	}
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
