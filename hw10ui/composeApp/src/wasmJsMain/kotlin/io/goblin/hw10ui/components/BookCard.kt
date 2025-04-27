package io.goblin.hw10ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.goblin.hw10ui.model.BookDto

@Composable
fun BookCard(book: BookDto) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(1.dp, Color.Gray)
                .padding(12.dp),
    ) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.h6,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Author: ${book.author.fullName}",
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Genres: ${book.genres.joinToString { it.name }}",
            style = MaterialTheme.typography.body2,
        )
    }
}
