package hi.cosmonaut.graphql.domain.usecase.base

import hi.cosmonaut.graphql.data.wrapper.Event

interface IBaseSuspendUseCase  {
    suspend operator fun invoke(): Event<Any>
}