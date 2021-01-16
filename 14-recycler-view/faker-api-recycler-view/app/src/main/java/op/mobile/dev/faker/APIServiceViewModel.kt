package op.mobile.dev.faker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import op.mobile.dev.faker.APIService.retrofitService

class APIServiceViewModel : ViewModel() {
    private val _status = MutableLiveData<APIServiceStatus>()
    val status: LiveData<APIServiceStatus> get() = _status

    private val _properties = MutableLiveData<APIServiceProperty>()
    val properties: LiveData<APIServiceProperty> get() = _properties

    init {
        getAPIServiceProperties()
    }

    private fun getAPIServiceProperties() {
        viewModelScope.launch {
            _status.value = APIServiceStatus.LOADING
            try {
                _properties.value = retrofitService.getProperties()
                _status.value = APIServiceStatus.COMPLETE
            } catch (e: Exception) {
                _properties.value = null
                _status.value = APIServiceStatus.ERROR
            }
        }
    }
}