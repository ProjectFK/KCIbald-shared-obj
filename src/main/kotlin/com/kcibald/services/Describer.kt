package com.kcibald.services

import com.kcibald.interfaces.SubsetIdentifiable

interface Describer<T> {
    fun describeThoughIdentifier(identifier: SubsetIdentifiable<T>): Result<T>
}