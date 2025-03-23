package com.krp.whoknows.RiveComponents

import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit

/**
 * Created by Kushal Raj Pareek on 20-03-2025 18:42
 */


@Composable
fun ComposableRiveAnimationView(modifier: Modifier = Modifier,
                                @RawRes animation :Int,
                                stateMachineName : String? =null,
                                alignment: app.rive.runtime.kotlin.core.Alignment =app.rive.runtime.kotlin.core.Alignment.CENTER,
                                fit : Fit = Fit.COVER,
                                onInit : (RiveAnimationView) -> Unit) {
    AndroidView(
        modifier =  modifier,
        factory = {context ->
            RiveAnimationView(context).also{
                it.setRiveResource(
                    resId = animation,
                    stateMachineName = stateMachineName,
                    alignment = alignment,
                    fit = fit
                )
            }
        },
        update = {view -> onInit(view)}
    )
    
}