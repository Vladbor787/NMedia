package ru.netology.nmedia.repository

import androidx.lifecycle.map
import ru.netology.nmedia.dbSql.PostDao
import ru.netology.nmedia.dbSql.toEntity
import ru.netology.nmedia.dbSql.toModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryRoomImpl (
    private val dao: PostDao
) : PostRepository {
    override fun getAll() = dao.getAll().map { entities ->
        entities.map {it.toModel() }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun viewersById(id: Long) {
        dao.viewById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
    dao.save(post = post.toEntity())
    }

    override fun cancelEditing(post: Post) {
        dao.equals(post)
    }

    override fun isVideo(post: Post): Boolean {
        TODO("Not yet implemented")
    }

}