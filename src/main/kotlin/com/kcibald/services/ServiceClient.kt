package com.kcibald.services

interface ServiceClient {
    val clientVersion: String
    val compatibleServiceVersion: String
}