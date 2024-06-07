package com.example.astrovision.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.astrovision.R
import com.example.astrovision.Screen
import com.example.astrovision.common.CommonText
import com.example.astrovision.ui.theme.mediumTextStyleSize18
import com.example.astrovision.ui.theme.mediumTextStyleSize20
import com.example.astrovision.ui.theme.mediumTextStyleSize28
import com.example.astrovision.ui.theme.primaryTextColor
import com.example.astrovision.ui.theme.white
import com.example.astrovision.viewmodel.ProductsViewModel
import com.example.astrovision.viewmodel.ScreenState

@Composable
fun ProductsHomeScreen(navController: NavController, viewModel: ProductsViewModel) {

    var searchQuery by remember { mutableStateOf("") }
    val screenState by viewModel.screenState
    val productsList by viewModel.productsList

    LaunchedEffect(Unit) {
        viewModel.fetchProductsOnline()
        viewModel.fetchProductsOffline()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header("Astro Vision Products")

        SearchBar(hint = "Search", onTextChange = { query ->
            searchQuery = query
            viewModel.filterProducts(query)
        })

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {


                items(productsList) { product ->
                    ProductItem(
                        onItemClick = {
                            navController.navigate("${Screen.ProductDetailScreen.route}/${product.id}")
                        },
                        imageUrl = product.image,
                        name = product.name,
                        price = product.price,
                        discount = product.discount
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        if (screenState is ScreenState.Loading){
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                color = Color.Black

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.Top,
        ) {
        CommonText(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.mediumTextStyleSize28
        )
    }
}

@Composable
fun SearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    height: Dp = 40.dp,
    elevation: Dp = 3.dp,
    cornerShape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = white,
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .shadow(elevation = elevation, shape = cornerShape)
            .background(color = backgroundColor, shape = cornerShape)
            .clickable { onSearchClicked() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier
                .weight(5f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = text,
            onValueChange = {
                text = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.mediumTextStyleSize18,
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    CommonText(
                        text = hint,
                        textColor = primaryTextColor,
                        style = MaterialTheme.typography.mediumTextStyleSize20,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            singleLine = true
        )
        Box(
            modifier = modifier
                .weight(1f)
                .size(40.dp)
                .background(white, shape = CircleShape)
                .clickable {
                    if (text.text.isNotEmpty()) {
                        text = TextFieldValue(text = "")
                        onTextChange("")
                    }
                },
        ) {
            if (text.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .size(34.dp),
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = null,
                    tint = primaryTextColor,
                )
            } else {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null,
                    tint = primaryTextColor,
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    onItemClick: () -> Unit,
    imageUrl: String?,
    name: String,
    price: Int,
    discount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .clickable(onClick = { onItemClick.invoke() }),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
        ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .size(48.dp)
                )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Spacer(modifier = Modifier.width(16.dp))
                CommonText(
                    modifier = Modifier,
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.mediumTextStyleSize20
                )
                Spacer(modifier = Modifier.width(8.dp))
                CommonText(
                    modifier = Modifier,
                    text = "Price: ₹$price",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
                Spacer(modifier = Modifier.width(8.dp))
                CommonText(
                    modifier = Modifier,
                    text =  "Discount: ₹$discount",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.mediumTextStyleSize18
                )
            }
        }
    }
}


