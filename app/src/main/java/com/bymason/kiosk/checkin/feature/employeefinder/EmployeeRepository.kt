package com.bymason.kiosk.checkin.feature.employeefinder

import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.MasonKiosk
import com.bymason.kiosk.checkin.core.model.Employee
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.directory.Directory
import com.google.api.services.directory.DirectoryScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.invoke

interface EmployeeRepository {
    suspend fun find(name: String): List<Employee>
}

class DefaultEmployeeRepository : EmployeeRepository {
    private val directory = createDirectory()

    override suspend fun find(name: String): List<Employee> = IO {
        directory.users().list().apply {
            domain = "bymason.com"
            viewType = "domain_public"
            query = name
        }.execute().users.orEmpty().map {
            Employee(it.id, it.name.fullName, it.primaryEmail, it.thumbnailPhotoUrl)
        }
    }

    private fun createDirectory(): Directory {
        val creds = ServiceAccountCredentials
                .fromStream(MasonKiosk.resources.openRawResource(R.raw.gsuite_creds))
                .toBuilder()
                .setServiceAccountUser("mason@bymason.com")
                .build()
                .createScoped(listOf(
                        DirectoryScopes.ADMIN_DIRECTORY_USER,
                        DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY
                ))

        return Directory.Builder(
                NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                HttpCredentialsAdapter(creds)
        ).setApplicationName("mason-checkin-kiosk").build()
    }
}
