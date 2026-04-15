plugins {
    `kotlin-dsl`
    `maven-publish`
    `signing`
}

group = project.findProperty("GROUP_ID")?.toString() ?: "io.github.liuzipeiliuziyu"
version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.1"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    google()
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("com.android.tools.build:gradle:8.5.0")
    implementation("org.ow2.asm:asm:9.6")
    implementation("org.ow2.asm:asm-commons:9.6")
}

gradlePlugin {
    plugins {
        create("recyclerViewClearPlugin") {
            // 🔥 把插件ID改成你有权限的 namespace！
            id = "io.github.liuzipeiliuziyu.recyclerview-clear"
            implementationClass = "com.kintory.plugin.RecyclerViewClearPlugin"
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            artifactId = "RecyclerViewClearPlugin"
            pom {
                name.set("ClearRecyclerViewPlugin")
                description.set(project.findProperty("POM_DESCRIPTION")?.toString())
                url.set(project.findProperty("POM_URL")?.toString())
                licenses {
                    license {
                        name.set(project.findProperty("POM_LICENSE_NAME")?.toString())
                        url.set(project.findProperty("POM_LICENSE_URL")?.toString())
                    }
                }
                developers {
                    developer {
                        id.set(project.findProperty("POM_DEVELOPER_ID")?.toString())
                        name.set(project.findProperty("POM_DEVELOPER_NAME")?.toString())
                        email.set(project.findProperty("POM_DEVELOPER_EMAIL")?.toString())
                    }
                }
                scm {
                    connection.set(project.findProperty("POM_SCM_CONNECTION")?.toString())
                    developerConnection.set(project.findProperty("POM_SCM_DEV_CONNECTION")?.toString())
                    url.set(project.findProperty("POM_SCM_URL")?.toString())
                }
            }
        }
    }

    repositories {
        maven {
            name = "local"
            url = uri(layout.buildDirectory.dir("outputs/repo"))
        }
    }
}

signing {
    val signingKeyFile = findProperty("signing.keyFile")?.toString()
    val signingKey = signingKeyFile?.let { File(it).readText() }
    val signingPassword = findProperty("signing.password")?.toString()
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
    isRequired = true
}