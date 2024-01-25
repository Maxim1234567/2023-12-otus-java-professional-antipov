plugins {
    id("java")
}

group = "ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.junit.jupiter:junit-jupiter-params")
    testImplementation ("org.assertj:assertj-core")
    testImplementation ("org.mockito:mockito-core")
    testImplementation ("org.mockito:mockito-junit-jupiter")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}