package com.bymason.kiosk.checkin.feature.masonitefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.MasonKiosk
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.Employee
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.directory.Directory
import com.google.api.services.directory.DirectoryScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

class MasoniteFinder : ViewModel() {
    private val directory = createDirectory()

    private val _masonites = MutableLiveData<List<Employee>>()
    val masonites: LiveData<List<Employee>> get() = _masonites

    private var previousSearch: Job? = null

    fun find(masonite: String?) {
        previousSearch?.cancel("New search came in")
        if (masonite.isNullOrBlank()) {
            _masonites.postValue(emptyList())
            return
        }

        previousSearch = viewModelScope.launch {
            val employees = try {
                IO {
                    directory.users().list().apply {
                        domain = "bymason.com"
                        viewType = "domain_public"
                        query = masonite
                    }.execute()
                }
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            }

            _masonites.postValue(employees.users.orEmpty().map {
                Employee(it.id, it.name.fullName, it.primaryEmail, it.thumbnailPhotoUrl)
            })
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
