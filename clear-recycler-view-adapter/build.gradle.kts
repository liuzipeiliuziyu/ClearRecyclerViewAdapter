plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    `signing`
}

android {
    namespace = "com.kintory.clear_recycler_view_adapter"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
}

// 使用 afterEvaluate 确保 android 闭包解析完成，组件 "release" 已创建
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                // 从 gradle.properties 中读取属性，注意增加非空判断或默认值
                groupId = project.findProperty("GROUP_ID")?.toString() ?: "io.github.liuzipeiliuziyu"
                artifactId = "recyclerview-clear-adapter"
                version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"
                
                // 引用 android 闭包中注册的 release 组件
                from(components["release"])
                
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
        
        // 配置发布到的仓库 (如果需要发布到本地或特定仓库)
        repositories {
            /*maven {
                name = "mavenCentral"
                // 这里的 URL 会根据 Sonatype 的要求变化，通常是 https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
                url = uri(project.findProperty("RELEASE_REPOSITORY_URL")?.toString() ?: layout.buildDirectory.dir("outputs/repo"))
                credentials {
                    username = project.findProperty("mavenCentralUsername")?.toString()
                    password = project.findProperty("mavenCentralPassword")?.toString()
                }
            }*/
            maven {
                name = "local"
                url = uri(layout.buildDirectory.dir("outputs/repo"))
            }
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
