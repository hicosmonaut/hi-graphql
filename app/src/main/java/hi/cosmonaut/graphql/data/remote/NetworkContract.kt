package hi.cosmonaut.graphql.data.remote

import com.apollographql.apollo.exception.ApolloException
import hi.cosmonaut.graphql.data.model.Continent

interface NetworkContract{

    interface IContinent {
        fun getContinents()
        fun getSelectedContinent(continent: Continent)
    }

    interface ICallback {
        fun onFailure(e: ApolloException)
        fun <T> onResponse(data: T)
    }

}
