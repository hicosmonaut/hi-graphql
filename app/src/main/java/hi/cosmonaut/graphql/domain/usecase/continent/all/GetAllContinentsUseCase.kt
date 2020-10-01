package hi.cosmonaut.graphql.domain.usecase.continent.all

import com.apollographql.apollo.api.toJson
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.data.remote.NetworkContract
import hi.cosmonaut.graphql.data.wrapper.Event
import hi.cosmonaut.graphql.util.GsonUtils
import query.ContinentsQuery

class GetAllContinentsUseCase(
    private val network: NetworkContract.IContinent
) : IGetAllContinentsUseCase {

    override suspend fun invoke(): Event<Any> = when (val result = network.getContinents()) {
        is Event.Success<*> -> handleSuccess(result.data as ContinentsQuery.Data)
        is Event.Error<*> -> Event.Error(null, result.message, null)
        else -> Event.Error(null, "ELSE ERROR", null)
    }

    private fun handleSuccess(data: ContinentsQuery.Data): Event.Success<*> {
        val result = GsonUtils.queryDataToModel(data.toJson(), ContinentList::class.java)
        return Event.Success(result)
    }

}