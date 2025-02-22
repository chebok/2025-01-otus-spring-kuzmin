package io.goblin.hw03.dao

import io.goblin.hw03.domain.Question

interface QuestionDao {
    fun findAll(): List<Question>
}
