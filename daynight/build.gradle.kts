plugins {
    
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

repositories {
    
    google()
    mavenCentral()
}

android {
    
    namespace = "com.velvet.darknight"
    compileSdk = 33

    defaultConfig {
        
        minSdk = 24
    }
    
    buildFeatures {
        
        compose = true
    }

    buildTypes {
        
        release {
            
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    composeOptions {
        
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    
    compileOptions {
        
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
}