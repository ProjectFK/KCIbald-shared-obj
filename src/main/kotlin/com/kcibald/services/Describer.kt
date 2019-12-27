package com.kcibald.services

import com.kcibald.interfaces.SubsetIdentifiable

interface Describer<T> {
    suspend fun describeThroughIdentifier(identifier: SubsetIdentifiable<T>): Result<T>

    suspend fun describeThroughIdentifiers(identifiers: Set<SubsetIdentifiable<T>>):
            Set<Pair<SubsetIdentifiable<T>, Result<T>>> =
        identifiers.map { it to describeThroughIdentifier(it) }.toSet()
}