package hi.cosmonaut.graphql.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.exception.ApolloException
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.data.model.SelectedContinent
import hi.cosmonaut.graphql.data.remote.NetworkContract
import hi.cosmonaut.graphql.data.remote.NetworkManager
import hi.cosmonaut.graphql.util.Constants

class ContinentsViewModel: ViewModel(), NetworkContract.ICallback {

    private val network: NetworkContract.IContinent = NetworkManager(this)

    private val continents: MutableLiveData<ContinentList> by lazy {
        MutableLiveData<ContinentList>().also {
            loadContinents()
        }
    }

    private val selectedContinent: MutableLiveData<SelectedContinent> by lazy {
        MutableLiveData<SelectedContinent>()
    }

    private fun loadContinents() = network.getContinents()

    fun selectContinent(continent: Continent) = network.getSelectedContinent(continent)

    fun getSelectedContinent(): LiveData<SelectedContinent> = selectedContinent

    fun getContinents(): LiveData<ContinentList> = continents

    override fun onFailure(e: ApolloException) {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onFailure(): ${e.message}")
    }

    override fun <T> onResponse(data: T) {
        when(data){
            is ContinentList -> continents.postValue(data)
            is SelectedContinent -> {
                selectedContinent.postValue(data)
            }
        }
    }

    companion object{
        private val TAG: String = ContinentsViewModel::class.java.simpleName
    }
}