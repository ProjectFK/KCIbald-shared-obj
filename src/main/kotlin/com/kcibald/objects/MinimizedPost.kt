package com.kcibald.objects

interface MinimizedPost : ContentBased {
    val id: String
    val title: String
    val urlKey: String
    val parentRegionUrlKey: String
    val commentCount: Int
}