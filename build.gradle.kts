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

    implementation("org.projectlombok:lombok:1.18.30")

    implementation("pl.tkowalcz.tjahzi:log4j2-appender:0.9.17")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
