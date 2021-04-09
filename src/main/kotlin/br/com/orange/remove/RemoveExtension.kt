package br.com.orange.remove

import br.com.orange.RemoveRequest

fun RemoveRequest.toModel(): RemoveRequestDto {
    return RemoveRequestDto(
            key = key,
            clientId = clientId
    )
}