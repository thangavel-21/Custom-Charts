package zuper.dev.android.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.JobApiModel

class DashboardViewModel(private val repository: DataRepository = DataRepository()) : ViewModel() {
    val job = MutableLiveData<List<JobApiModel>>()
    val invoice = MutableLiveData<List<InvoiceApiModel>>()

    suspend fun getObserveJobs() {
        viewModelScope.launch {
            repository.observeJobs().collect { list ->
                job.value = list
            }
        }
    }

    suspend fun getObserveInvoice() {
        viewModelScope.launch {
            repository.observeInvoices().collect { list ->
                invoice.value = list
            }
        }
    }
}