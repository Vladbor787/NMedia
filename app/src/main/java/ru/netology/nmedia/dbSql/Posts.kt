package ru.netology.nmedia.dbSql

import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

internal fun PostEntity.toModel() =
    Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likes,
        likedByMe = likedByMe,
        countShare = countShare,
        countView = countView,
        videoLink = videoLink
    )

internal fun Post.toEntity() =
        PostEntity(
            id = id,
            author = author,
            content = content,
            published = published,
            likes = likes,
            likedByMe = likedByMe,
            countShare = countShare,
            countView = countView,
            videoLink = videoLink
        )
