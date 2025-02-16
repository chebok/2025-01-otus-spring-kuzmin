package io.goblin.hw02.dao

import io.goblin.hw02.domain.Question

interface QuestionDao {
    fun findAll(): List<Question>
}
