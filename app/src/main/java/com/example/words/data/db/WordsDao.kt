package com.example.words.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.words.domain.entity.Word
import com.example.words.domain.entity.WordsCount

@Dao
interface WordsDao {

    @Insert
    suspend fun insertOnStart(words: List<Word>): List<Long>

    @Query("SELECT * FROM words_table WHERE isStudied = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getWord(): Word


    @Query(
        "SELECT (SELECT COUNT(*) FROM words_table WHERE isStudied = 0) as non_studied_count, " +
                "(SELECT COUNT(*) FROM words_table WHERE isStudied = 1) as studied_count;"
    )
    suspend fun getCount(): WordsCount

    @Update
    suspend fun updateWord(word: Word)

    //@Query("SELECT * FROM words_table WHERE noun LIKE '%' || :search || '%'")
    @Query("SELECT * FROM words_table WHERE word LIKE :search || '%'")
    suspend fun searchWords(search: String): List<Word>

}