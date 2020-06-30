package hi.cosmonaut.graphql.data.remote

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.exception.ApolloException
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.data.model.SelectedContinent
import hi.cosmonaut.graphql.util.Constants
import hi.cosmonaut.graphql.util.GsonUtils
import query.ContinentsQuery
import query.SelectedContinentQuery

class NetworkManager(val callback: NetworkContract.ICallback) : NetworkContract.IContinent {

    //the first experience with GraphQL. Sorry for possible mistakes
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://countries.trevorblades.com")
        .build()

    override fun getContinents() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "getContinents(): start")
        execAsyncRequest(ContinentsQuery.Data::class.java) { apolloClient.query(ContinentsQuery()) }
    }

    override fun getSelectedContinent(continent: Continent) {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "getSelectedContinent(): start")
        execAsyncRequest(SelectedContinentQuery.Data::class.java) { apolloClient.query(SelectedContinentQuery(continent.code)) }
    }

    //custom lamba for handling different types of response
    private fun <T, C> execAsyncRequest(responseType: Class<C>, request: () -> ApolloQueryCall<T>){
        request().enqueue(object : ApolloCall.Callback<T>(){
            override fun onFailure(e: ApolloException) {
                callback.onFailure(e)
            }

            override fun onResponse(response: Response<T>) {
                val data = response.data
                if(Constants.LOGS_ENABLED) Log.d(TAG, "onResponse(): data: $data")
                when{
                    response.hasErrors() -> callback.onFailure(ApolloException("Response has errors"))
                    data == null -> callback.onFailure(ApolloException("Response data is null"))
                    else -> handleResponseData(data, responseType)
                }
            }
        })
    }

    private fun <T, R> handleResponseData(data: T, responseType: Class<R>) {
        when(responseType){
            ContinentsQuery.Data::class.java -> dataAsContinentQueryData(data as ContinentsQuery.Data)
            SelectedContinentQuery.Data::class.java -> dataAsSelectedQueryData(data as SelectedContinentQuery.Data)
            else -> if(Constants.LOGS_ENABLED) Log.d(TAG, "handleResponseData(): else type: ${responseType.canonicalName}")
        }
    }

    private fun dataAsContinentQueryData(data: ContinentsQuery.Data) {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "dataAsContinentQueryData(): data: $data")
        val result = GsonUtils.queryDataToModel(data.toJson(), ContinentList::class.java)
        callback.onResponse(result)
    }

    private fun dataAsSelectedQueryData(data: SelectedContinentQuery.Data) {
        val result = GsonUtils.queryDataToModel(data.toJson(), SelectedContinent::class.java)
        callback.onResponse(result)
    }

    companion object{
        private val TAG: String = NetworkManager::class.java.simpleName
    }
}