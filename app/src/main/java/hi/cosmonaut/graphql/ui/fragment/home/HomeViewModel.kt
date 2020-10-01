package hi.cosmonaut.graphql.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.data.model.SelectedContinent
import hi.cosmonaut.graphql.data.remote.NetworkContract
import hi.cosmonaut.graphql.data.remote.NetworkManager
import hi.cosmonaut.graphql.data.wrapper.Event
import hi.cosmonaut.graphql.domain.usecase.continent.all.GetAllContinentsUseCase
import hi.cosmonaut.graphql.domain.usecase.continent.select.GetSelectedContinentUseCase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val network: NetworkContract.IContinent = NetworkManager()

    private val _continentsData: MutableLiveData<List<Continent>> = MutableLiveData()
    val continentsData: LiveData<List<Continent>> get() = _continentsData

    private val _selectedContinentData: MutableLiveData<SelectedContinent> = MutableLiveData()
    val selectedContinentData: LiveData<SelectedContinent> get() = _selectedContinentData

    private val _messageData: MutableLiveData<String> = MutableLiveData()
    val messageData: LiveData<String> get() = _messageData

    private val getAllContinentsUseCase = GetAllContinentsUseCase(network)

    fun fetchData() {
        loadContinents()
    }

    fun selectContinent(continent: Continent) = viewModelScope.launch {
        val getSelectedContinentUseCase = GetSelectedContinentUseCase(network, continent)

        when(val result = getSelectedContinentUseCase()){
            is Event.Success<*> -> handleAsSelectedContinent(result.data as SelectedContinent)
            is Event.Error<*> -> showMessage(result.message)
        }

    }

    private fun loadContinents() = viewModelScope.launch {
        when(val result = getAllContinentsUseCase()){
          is Event.Success<*> -> handleAsAllContinents(result.data as ContinentList)
          is Event.Error<*> -> showMessage(result.message)
        }
    }

    private fun handleAsSelectedContinent(selectedContinent: SelectedContinent) = _selectedContinentData.postValue(selectedContinent)
    private fun handleAsAllContinents(continents: ContinentList) = _continentsData.postValue(continents.data?.continents)

    private fun showMessage(message: String) = _messageData.postValue(message)

    companion object{
        private val TAG: String = HomeViewModel::class.java.simpleName
    }
}