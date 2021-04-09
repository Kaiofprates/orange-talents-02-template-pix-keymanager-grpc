package br.com.orange.remove

import br.com.orange.RemoveRequest
import java.util.*

fun RemoveRequest.toModel(): RemoveRequestDto {
    return RemoveRequestDto(
            pixId = key,
            clientId = clientId
    )
}