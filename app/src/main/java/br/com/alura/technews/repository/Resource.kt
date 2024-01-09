package br.com.alura.technews.repository

class Resource<T>(
    val dado: T?,
    val erro: String? = null
)

fun <T> createResourceFailure(
    resourceOld: Resource<T?>?,
    error: String?
): Resource<T?> =
    if (resourceOld != null) Resource(dado = resourceOld.dado, erro = error)
    else Resource(dado = null, erro = error)