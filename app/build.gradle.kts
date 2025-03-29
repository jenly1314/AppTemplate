import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mvvmframe)
    id("kotlin-parcelize")
}

android {
    namespace = "com.king.template"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.king.template"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        ndk {
            //设置支持的SO库架构（开发者可以根据需要，选择一个或多个平台的so）
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", /* "x86_64", "x86" */))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        // 常见排除项
        resources.excludes += setOf(
            "META-INF/LICENSE*",
            "META-INF/LICENSE-*.txt",
            "META-INF/LICENSE-*.md",
            "META-INF/NOTICE*",
            "META-INF/COPYING*",
            "META-INF/DEPENDENCIES*",
            "META-INF/ASL2.0",
            "META-INF/LGPL2.1"
        )
    }

    val localProperties = Properties().apply {
        rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
    }
    val signingEnabled = localProperties.getProperty("signingEnabled", "false").toBoolean()
    if(signingEnabled) {
        signingConfigs {
            create("config") {
                localProperties.getProperty("storeFile.file")?.let { storeFile = file(it) }
                    ?: error("storeFile.file not found in local.properties")
                keyAlias = localProperties.getProperty("keyAlias")
                    ?: error("keyAlias not found in local.properties")
                keyPassword = localProperties.getProperty("keyPassword")
                    ?: error("keyPassword not found in local.properties")
                storePassword = localProperties.getProperty("storePassword")
                    ?: error("storePassword not found in local.properties")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            if (signingEnabled) {
                signingConfig = signingConfigs.getByName("config")
            }
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

//    flavorDimensions.add("flavor")
//
//    productFlavors {
//        register("flavors_release") {
//            dimension = "flavor"
//        }
//        register("flavors_dev") {
//            dimension = "flavor"
//        }
//    }

}


dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    //room
    ksp(libs.room.compiler)

    implementation(libs.bundles.jenly1314)

    implementation(libs.androidktx)
    implementation(libs.androidutil)

    implementation(libs.mmkv)

    implementation(libs.brvah)

    implementation(libs.bundles.smartrefreshlayout)

    implementation(libs.glide)
    kapt(libs.glide.compiler)

    //leakCanary
    debugImplementation(libs.leakcanary)

    implementation(libs.circleimageview)

    implementation(libs.banner)

    implementation(libs.autosize)

    implementation(project(":lib-umeng"))

}
