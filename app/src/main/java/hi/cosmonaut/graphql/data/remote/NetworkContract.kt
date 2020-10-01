package hi.cosmonaut.graphql.data.remote

import com.apollographql.apollo.exception.ApolloException
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.wrapper.Event

interface NetworkContract{

    interface IContinent {
        suspend fun getContinents(): Event<Any>
        suspend fun getSelectedContinent(continent: Continent): Event<Any>
    }

    interface ICallback {
        fun onFailure(error: Event.Error<*>)
        fun onResponse(success: Event.Success<*>)
    }

}
