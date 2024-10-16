import com.vanniktech.maven.publish.SonatypeHost


plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.nmcp)
    alias(libs.plugins.gradle.maven.publish)
}

android {
    namespace = "io.jn.smwv"
    compileSdk = 34


    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.dotenv)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

group = "io.github.ruan625br"
version = "1.0.0-beta01"


mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    coordinates("io.github.ruan625br", "smwv", "1.0.0-beta01")

    signAllPublications()

    pom {
        name.set("Smwv")
        description.set("Smww embeds WebView into sa-mp clients, allowing you to create interfaces with react, vue, etc.")
        url.set("https://github.com/Ruan625Br/Smwv")
        inceptionYear.set("2024")

        licenses {
            license {
                name.set("The GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
            }
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/ruan625br/Smwv/issues")
        }

        developers {
            developer {
                id.set("ruan625br")
                name.set("Juan Nascimento")
                email.set("juannascimento.code@gmail.com")
            }
        }

        scm {
            connection.set("scm:git@github.com:ruan625br/Smwv")
            developerConnection.set("scm:git@github.com:ruan625br/Smwv.git")
            url.set("https://github.com/ruan625br/Smwv.git")
        }
    }

}


nmcp {
    publishAllPublications {
        username = System.getenv("NPM_USERNAME")
        password = System.getenv("NPM_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}

