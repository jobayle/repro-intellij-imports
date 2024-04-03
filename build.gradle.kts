group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jooq.jooq-codegen-gradle") version "3.19.6"
}

dependencies {
    implementation("org.jooq:jooq:3.19.6")
    implementation("org.jooq:jooq-kotlin:3.19.6")

    jooqCodegen("org.postgresql:postgresql:42.7.3")
    //jooqCodegen("org.jooq:jooq-meta-extensions:42.7.3")
}


jooq {
    executions {
        create("test") {
            configuration {
                jdbc {
                    username = "postgres"
                    password = "postgres"
                    url = "jdbc:postgresql://localhost:5432/test"
                }
                generator {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    target {
                        packageName = "test.jooq.generated"
                    }
                }
            }
        }
    }
}

tasks.compileKotlin {
    dependsOn(tasks.jooqCodegen)
}

kotlin {
    jvmToolchain(17)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter() // JUnit 5 only
            dependencies {
                implementation(project())
            }
        }
    }
}
