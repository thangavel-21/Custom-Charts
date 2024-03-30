package zuper.dev.android.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.JobApiModel

class JobViewModel(private val repository: DataRepository = DataRepository()) : ViewModel() {
    val job = MutableLiveData<List<JobApiModel>>()

    fun getJobs() {
        viewModelScope.launch {
            job.value = repository.getJobs()
        }
    }
}