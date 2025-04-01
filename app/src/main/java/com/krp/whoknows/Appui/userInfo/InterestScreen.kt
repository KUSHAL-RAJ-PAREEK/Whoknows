package com.krp.whoknows.Appui.userInfo

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.toDp
import com.google.accompanist.insets.imePadding
import com.krp.whoknows.Appui.Profile.presentation.SearchViewModel
import com.krp.whoknows.Appui.interest.Components.InterestItem
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.ordColor
import java.time.LocalDate

/**
 * Created by Kushal Raj Pareek on 01-04-2025 09:27
 */

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InterestScreen(modifier: Modifier = Modifier,
                   infoViewModel: InfoViewModel,
                   navController: NavController) {

    val viewModel = viewModel<SearchViewModel>()
    var searchisfocused by remember{ mutableStateOf(false) }

    val context = LocalContext.current
        val searchText by viewModel.searchText.collectAsState()
        val interestSea by viewModel.interest.collectAsState()
        val isSearch by viewModel.isSearching.collectAsState()
        val selectedStates by viewModel.selectedStates.collectAsState()
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.White)){

        Column (modifier = Modifier.fillMaxSize()
            .padding(top = 5.dp)){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 10.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow",
                    Modifier.size(35.dp).clickable{
                        navController.navigate(com.krp.whoknows.Navigation.DOBScreen){
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    tint = ordColor
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = "What's your Intreset?",
                    fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .onFocusChanged { searchisfocused = !searchisfocused },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp,color = Color.Black),
                placeholder = { Text("Search") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                trailingIcon = {  Icon(
                    imageVector = Icons.Filled.Search,
                    tint = ordColor,
                    contentDescription = "Search Icon")},
                maxLines = 1,
                shape = RoundedCornerShape(20.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                TextField(
//                    value = searchText,
//                    onValueChange = viewModel::onSearchTextChange,
//                    modifier = Modifier.fillMaxWidth(),
//                    placeholder = { Text(text = "Search") }
//                )
//                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    interestSea.forEachIndexed { i, interest ->
                        val isSelected = selectedStates[interest.interest] ?: false
                        InterestItem(
                            modifier = Modifier.padding(6.dp),
                            name = interest.interest, filled = isSelected
                        ) {
                            viewModel.toggleSelection(interest.interest)
                            if (!isSelected) {
                                infoViewModel.addInterest(interest.interest)
                            } else {
                                infoViewModel.removeInterest(interest.interest)
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    if(infoViewModel.interests.value == null || infoViewModel.interests.value!!.size < 3){
                        Toast.makeText(context,"You need to select at least three interests.",
                            Toast.LENGTH_SHORT).show()
                    }else{
                        Log.d("intrersetsfasfas",infoViewModel.interests.value.toString())
                        navController.navigate(com.krp.whoknows.Navigation.PreferredAgeRange)
                    }
                },
                shape = CircleShape,
                containerColor = ordColor,
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .size(56.dp)
                    .imePadding()
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}