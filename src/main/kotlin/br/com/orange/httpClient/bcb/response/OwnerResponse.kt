package br.com.orange.httpClient.bcb

data class OwnerResponse(val type: PersonType)


enum class PersonType{
    NATURAL_PERSON, LEGAL_PERSON
}