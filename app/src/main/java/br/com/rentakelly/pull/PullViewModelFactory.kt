package br.com.rentakelly.pull

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.utils.LoggerAndroid

class PullViewModelFactory(private val client: RepositoryService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullViewModel::class.java)) {
            return PullViewModel(client, LoggerAndroid()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
