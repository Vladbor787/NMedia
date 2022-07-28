package ru.netology.nmedia.dbSql

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getPosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostEntity)


    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query("""
        UPDATE posts SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("""
        UPDATE posts SET
        countShare = countView + 1
        WHERE id = :id
        """)

    fun shareById(id: Long)
    @Query("""
        UPDATE posts SET
        countView = countView + 1
        WHERE id = :id
        """)
    fun viewById(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)


}