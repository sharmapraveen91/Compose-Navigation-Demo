package com.praveen.picsumapp.presentation.ui.screen.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.praveen.picsumapp.domain.model.PicsumImage


/**
 * Created by Praveen.Sharma on 01/03/25 - 11:44..
 *
 ***/
@Composable
fun ImageCard(picsumImage: PicsumImage, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = picsumImage.download_url,
                contentDescription = "Picsum Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = picsumImage.author,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}