package ru.rashgild.promedator.extentions

import com.fasterxml.jackson.databind.ObjectMapper


fun<T> ObjectMapper.map(source: String, targetClass: Class<T>?):T {
   return this.readValue(source, targetClass)
}

fun <S, T> ObjectMapper.mapToList(source: Collection<S> , targetClass: Class<T>?): List<T> {
    return source.map { any: Any? -> this.convertValue(any, targetClass) }
}
