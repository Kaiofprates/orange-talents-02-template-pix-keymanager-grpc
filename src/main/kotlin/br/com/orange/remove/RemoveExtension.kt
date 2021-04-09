package br.com.orange.remove

import br.com.orange.RemoveRequest

// TODO: 09/04/2021 essa lógica só existe para o uso das validações, pensar em uma forma de otimizar

fun RemoveRequest.toModel(): RemoveRequestDto {
    return RemoveRequestDto(
            pixId = id,
            clientId = clientId
    )
}