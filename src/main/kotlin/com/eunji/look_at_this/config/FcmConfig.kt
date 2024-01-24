package com.eunji.look_at_this.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException


@Configuration
class FCMConfig {
    @Bean
    @Throws(IOException::class)
    fun firebaseMessaging(): FirebaseMessaging {
        val resource = ClassPathResource("look-at-this-25516-firebase-adminsdk-vgxr2-6f000c9396.json")

        val refreshToken = resource.inputStream

        var firebaseApp: FirebaseApp? = null
        val firebaseAppList: List<FirebaseApp> = FirebaseApp.getApps()

        if (firebaseAppList.isNotEmpty()) {
            for (app in firebaseAppList) {
                if (app.name.equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app
                }
            }
        } else {
            val options: FirebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build()

            firebaseApp = FirebaseApp.initializeApp(options)
        }

        return FirebaseMessaging.getInstance(firebaseApp)
    }
}