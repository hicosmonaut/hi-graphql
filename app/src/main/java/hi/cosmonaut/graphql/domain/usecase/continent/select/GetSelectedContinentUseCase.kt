package hi.cosmonaut.graphql.domain.usecase.continent.select

import com.apollographql.apollo.api.toJson
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.SelectedContinent
import hi.cosmonaut.graphql.data.remote.NetworkContract
import hi.cosmonaut.graphql.data.wrapper.Event
import hi.cosmonaut.graphql.util.GsonUtils
import query.SelectedContinentQuery

class GetSelectedContinentUseCase(
    private val network: NetworkContract.IContinent,
    private val continent: Continent
) : IGetSelectedContinentUseCase {

    override suspend fun invoke(): Event<Any> = when(val result = network.getSelectedContinent(continent)){
        is Event.Success<*> -> handleSuccess(result.data as SelectedContinentQuery.Data)
        is Event.Error<*> -> Event.Error(null, result.message, null)
        else -> Event.Error(null, "Else error", null)
    }

    private fun handleSuccess(data: SelectedContinentQuery.Data): Event.Success<*> {
        val result = GsonUtils.queryDataToModel(data.toJson(), SelectedContinent::class.java)
        return Event.Success(result)
    }
}