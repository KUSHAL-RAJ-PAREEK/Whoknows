package com.krp.whoknows.Appui.Profile.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.Utils.toBitmap
import com.krp.whoknows.roomdb.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by KUSHAL RAJ PAREEK on 15,March,2025
 */


class MainImageViewModel(private val repository: ImageRepository) : ViewModel() {

    private val _profileImage = MutableStateFlow<Bitmap?>(null)
    val profileImage: StateFlow<Bitmap?> = _profileImage

    private val _firstGalleryImage = MutableStateFlow<Bitmap?>(null)
    val firstGalleryImage: StateFlow<Bitmap?> = _firstGalleryImage

    private val _secondGalleryImage = MutableStateFlow<Bitmap?>(null)
    val secondGalleryImage: StateFlow<Bitmap?> = _secondGalleryImage

    private val _thirdGalleryImage = MutableStateFlow<Bitmap?>(null)
    val thirdGalleryImage: StateFlow<Bitmap?> = _thirdGalleryImage

    init {
        fetchProfileImage()
        fetchGalleryImages()
    }

    private fun fetchProfileImage() {
        viewModelScope.launch {
            val imageModel = repository.getProfileImage()
            _profileImage.value = imageModel?.imageString?.toBitmap()
        }
    }

     fun saveProfileImage(img : String?) {
        viewModelScope.launch {
           repository.saveProfileImageD(img)
            fetchProfileImage()
        }
    }

     fun saveGalleryImage(id : String,img : String?) {
        viewModelScope.launch {
            repository.saveGalleryImageD(id,img)
            fetchGalleryImages()
        }
    }


    fun updateProfile(bitmap: Bitmap?) {
        _profileImage.value = bitmap
    }
    fun updateG1(bitmap: Bitmap?) {
        _firstGalleryImage.value = bitmap
    }
    fun updateG2(bitmap: Bitmap?) {
        _secondGalleryImage.value = bitmap
    }
    fun updateG3(bitmap: Bitmap?) {
        _thirdGalleryImage.value = bitmap
    }


    private fun fetchGalleryImages() {
        viewModelScope.launch {
            val images = repository.getGalleryImages()
            val imageWithIds = images.mapNotNull { it.imageString.toBitmap()?.let { bitmap -> it.id to bitmap } }

            _firstGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g1" }?.second
            _secondGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g2" }?.second
            _thirdGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g3" }?.second
        }
    }
}