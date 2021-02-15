package br.com.rentakelly.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.utils.LoggerAndroid

class RepositoryViewModelFactory(private val client: RepositoryService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            return RepositoryViewModel(client, LoggerAndroid()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
