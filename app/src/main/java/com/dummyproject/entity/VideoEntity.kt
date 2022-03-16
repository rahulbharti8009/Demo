package com.dummyproject.entity

data class VideoEntity(
    val categories: MutableList<Category>
)

data class Category(
    val name: String,
    val videos: MutableList<Video>
)

data class Video(
    val description: String,
    val sources: MutableList<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)