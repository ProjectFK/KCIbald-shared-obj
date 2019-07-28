package com.kcibald.services

import com.kcibald.interfaces.SubsetIdentifiable

interface Describer<T> {
    suspend fun describeThoughIdentifier(identifier: SubsetIdentifiable<T>): Result<T>
}