plugins {
    id("java")
}

group = "com.miti99"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("io.opentelemetry:opentelemetry-api:1.33.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.33.0")
    implementation("io.opentelemetry:opentelemetry-sdk-logs:1.33.0")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:1.33.0")
    implementation("io.opentelemetry.instrumentation:opentelemetry-log4j-appender-2.17:1.30.0-alpha")
    implementation("io.opentelemetry.proto:opentelemetry-proto:1.0.0-alpha")

    implementation("org.projectlombok:lombok:1.18.30")

    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
