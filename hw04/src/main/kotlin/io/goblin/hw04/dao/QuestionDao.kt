package io.goblin.hw04.dao

import io.goblin.hw04.domain.Question

interface QuestionDao {
    fun findAll(): List<Question>
}
