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


    private val _matchprofileImage = MutableStateFlow<Bitmap?>(null)
    val matchprofileImage: StateFlow<Bitmap?> = _matchprofileImage

    private val _matchfirstGalleryImage = MutableStateFlow<Bitmap?>(null)
    val matchfirstGalleryImage: StateFlow<Bitmap?> = _matchfirstGalleryImage

    private val _matchsecondGalleryImage = MutableStateFlow<Bitmap?>(null)
    val matchsecondGalleryImage: StateFlow<Bitmap?> = _matchsecondGalleryImage

    private val _matchthirdGalleryImage = MutableStateFlow<Bitmap?>(null)
    val matchthirdGalleryImage: StateFlow<Bitmap?> = _matchthirdGalleryImage


    init {
        fetchProfileImage()
        fetchGalleryImages()
        fetchMatchProfileImage()
        fetchMatchGalleryImages()
    }

    private fun fetchProfileImage() {
        viewModelScope.launch {
            val imageModel = repository.getProfileImage()
            _profileImage.value = imageModel?.imageString?.toBitmap()
        }
    }


    fun saveProfileImage(img: String?) {
        viewModelScope.launch {
            repository.saveProfileImageD(img)
            fetchProfileImage()
        }
    }


    fun saveGalleryImage(id: String, img: String?) {
        viewModelScope.launch {
            repository.saveGalleryImageD(id, img)
            fetchGalleryImages()
        }
    }


    private fun fetchMatchProfileImage() {
        viewModelScope.launch {
            val imageModel = repository.getMatchProfileImage()
            _matchprofileImage.value = imageModel?.imageString?.toBitmap()
        }
    }


    fun saveMatchProfileImage(img: String?) {
        viewModelScope.launch {
            repository.saveMatchProfileImageD(img)
            fetchMatchProfileImage()
        }
    }

    fun saveMatchGalleryImage(id: String, img: String?) {
        viewModelScope.launch {
            repository.saveMatchGalleryImageD(id, img)
            fetchMatchGalleryImages()
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            repository.deleteProfile()
        }
    }

    fun deleteGallery() {
        viewModelScope.launch {
            repository.deleteGallery()
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
            val imageWithIds =
                images.mapNotNull { it.imageString.toBitmap()?.let { bitmap -> it.id to bitmap } }

            _firstGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g1" }?.second
            _secondGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g2" }?.second
            _thirdGalleryImage.value = imageWithIds.find { it.first.takeLast(2) == "g3" }?.second
        }
    }

    private fun fetchMatchGalleryImages() {
        viewModelScope.launch {
            val images = repository.getMatchGalleryImages()
            val imageWithIds =
                images.mapNotNull { it.imageString.toBitmap()?.let { bitmap -> it.id to bitmap } }

            _matchfirstGalleryImage.value =
                imageWithIds.find { it.first.takeLast(2) == "g1" }?.second
            _matchsecondGalleryImage.value =
                imageWithIds.find { it.first.takeLast(2) == "g2" }?.second
            _matchthirdGalleryImage.value =
                imageWithIds.find { it.first.takeLast(2) == "g3" }?.second
        }
    }


    fun clearMatch() {
        _matchprofileImage.value = null
        _matchfirstGalleryImage.value = null
        _matchsecondGalleryImage.value = null
        _matchthirdGalleryImage.value = null
        deleteProfile()
        deleteGallery()

    }
}