package io.goblin.hw01.dao

import io.goblin.hw01.domain.Question

interface QuestionDao {
    fun findAll(): List<Question>
}
