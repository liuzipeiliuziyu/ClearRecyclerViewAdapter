plugins {
    `kotlin-dsl`
    `maven-publish`
}

// 设置项目层级的 group 和 version，供发布使用
group = project.findProperty("GROUP_ID")?.toString() ?: "com.liuzipei.plugin"
version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    google()
    mavenCentral()
}

// 生成源码和文档 Jar 包（Maven Central 强制要求）
java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.5.0")
    implementation("org.ow2.asm:asm:9.6")
    implementation("org.ow2.asm:asm-commons:9.6")
}

gradlePlugin {
    plugins {
        create("recyclerViewClearPlugin") {
            id = "com.kintory.plugin.recyclerview-clear"
            implementationClass = "com.kintory.plugin.RecyclerViewClearPlugin"
        }
    }
}

publishing {
    publications {
        // 使用 withType<MavenPublication> 统一配置插件本身及其 Marker 的 POM 信息
        withType<MavenPublication> {
            pom {
                name.set(project.findProperty("POM_NAME")?.toString())
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
            name = "mavenCentral"
            // 默认发布到本地 build/outputs/repo，可通过属性配置正式仓库 URL
            url = uri(project.findProperty("RELEASE_REPOSITORY_URL")?.toString() ?: layout.buildDirectory.dir("outputs/repo"))
            credentials {
                username = project.findProperty("mavenCentralUsername")?.toString()
                password = project.findProperty("mavenCentralPassword")?.toString()
            }
        }
    }
}
