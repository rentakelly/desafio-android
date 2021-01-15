package br.com.rentakelly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.repository.RepositoryViewModel

class ViewModelFactory(private val client: RepositoryService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)){
            return RepositoryViewModel(client) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}