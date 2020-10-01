package hi.cosmonaut.graphql.data.remote

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.wrapper.Event
import hi.cosmonaut.graphql.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import query.ContinentsQuery
import query.SelectedContinentQuery

class NetworkManager: NetworkContract.IContinent {

    //the first experience with GraphQL. Sorry for possible mistakes
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://countries.trevorblades.com")
        .build()

    override suspend fun getContinents(): Event<Any> {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "getContinents(): start")
        return execAsyncRequest { apolloClient.query(ContinentsQuery()) }
    }

    override suspend fun getSelectedContinent(continent: Continent): Event<Any> {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "getSelectedContinent(): start")
        return execAsyncRequest { apolloClient.query(SelectedContinentQuery(continent.code)) }
    }

    //custom lambda for handling different types of response
    private suspend fun <T> execAsyncRequest(request: () -> ApolloCall<T>) = withContext(Dispatchers.IO){

        val deferredResult = request().toDeferred()
        val response = deferredResult.await()

        val data: T? = response.data
        val errors = response.errors //todo: actions with errors, if needed

        when{
            response.hasErrors() -> Event.Error(null, "Response has errors", null)
            data == null -> Event.Error(null, "Response data is null", null)
            else -> Event.Success(data)
        }

    }

    companion object{
        private val TAG: String = NetworkManager::class.java.simpleName
    }
}