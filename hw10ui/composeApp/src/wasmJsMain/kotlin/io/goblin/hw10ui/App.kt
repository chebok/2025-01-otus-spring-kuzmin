package io.goblin.hw10ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.goblin.hw10ui.components.BookCard
import io.goblin.hw10ui.model.BookDto
import io.goblin.hw10ui.network.fetchBooks

@Composable
fun App() {
    var books by remember { mutableStateOf<List<BookDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        books = fetchBooks()
    }

    MaterialTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            books.forEach { book ->
                BookCard(book)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
