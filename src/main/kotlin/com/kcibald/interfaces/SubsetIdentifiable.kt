package com.kcibald.interfaces

/**
 * unique id for the object in a its subset<br>
 *     for example: unique identifier for a post in its repo is its SubsetIdentifier
 */
interface SubsetIdentifiable<SUBSET_TYPE> {
    val sid: String
}