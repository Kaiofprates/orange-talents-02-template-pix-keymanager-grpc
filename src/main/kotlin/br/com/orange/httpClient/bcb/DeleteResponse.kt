package br.com.orange.httpClient.bcb

data class DeleteResponse(
    val key: String,
    val participant: String,
    val deletedAt: String
)
