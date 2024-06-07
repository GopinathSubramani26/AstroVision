package com.example.astrovision.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.astrovision.R
import com.example.astrovision.common.CommonHeader
import com.example.astrovision.common.CommonText
import com.example.astrovision.ui.theme.mediumTextStyleSize16
import com.example.astrovision.ui.theme.mediumTextStyleSize18
import com.example.astrovision.ui.theme.mediumTextStyleSize20
import com.example.astrovision.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel
) {
    val id = navController.currentBackStackEntry?.arguments?.getInt("id")

    LaunchedEffect(Unit) {
        viewModel.fetchProductById(id ?: 0)
    }

    val product = viewModel.product.value

    Column(modifier = Modifier.padding(16.dp)) {
        CommonHeader(
            text = product?.name ?: "",
            image = painterResource(id = R.drawable.left_arrow),
            onBackClick = {
                navController.popBackStack()
            })

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
        ) {

            Column(modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                ) {
                AsyncImage(
                    model = product?.image ?: "",
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonText(
                    modifier = Modifier,
                    text = product?.name ?: "",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.mediumTextStyleSize20
                )
                Spacer(modifier = Modifier.height(8.dp))
                CommonText(
                    modifier = Modifier,
                    text = product?.description ?: "",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize16
                )
                Spacer(modifier = Modifier.height(8.dp))
                CommonText(
                    modifier = Modifier,
                    text = "Price: ₹${product?.price ?: 0}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
                Spacer(modifier = Modifier.width(8.dp))
                CommonText(
                    modifier = Modifier,
                    text = "Discount: ₹${product?.discount ?: 0}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
                Spacer(modifier = Modifier.height(8.dp))
                CommonText(
                    modifier = Modifier,
                    text = "* ${product?.authentic ?: 0}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
                CommonText(
                    modifier = Modifier,
                    text = "* ${product?.remedies ?: 0}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
                CommonText(
                    modifier = Modifier,
                    text = "* ${product?.vedic ?: 0}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
            }
        }
    }
}