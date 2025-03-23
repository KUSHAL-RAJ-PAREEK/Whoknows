package com.krp.whoknows.roomdb

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.krp.whoknows.SupabaseClient.supabaseClient
import com.krp.whoknows.roomdb.entity.GalleryImageEntity
import com.krp.whoknows.roomdb.entity.ProfileImageEntity
import com.krp.whoknows.roomdb.entity.UserGalleryImageEntity
import com.krp.whoknows.roomdb.entity.UserProfileImage
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream

/**
 * Created by KUSHAL RAJ PAREEK on 14,March,2025
 */

class ImageRepository(private val imageDao: Dao) {

    val supabase: SupabaseClient by inject(SupabaseClient::class.java)


    fun getSupabasePublicImageUrl(bucketName: String, imagePath: String): String {
        return supabaseClient().storage
            .from(bucketName)
            .publicUrl(imagePath)
    }


    suspend fun saveProfileImageToSupabase(context: Context, uri: Uri?, id: String): Boolean {
        if (uri == null) return true

        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()
                inputStream?.close()

                if (byteArray != null) {
                    val fileName = "$id.jpg"

                    val response = supabase.storage
                        .from("profile_images")
                        .upload(fileName, byteArray, upsert = true)

                    return@withContext if (response.isNotEmpty()) {
                        Log.d("SupabaseUpload", "Profile image uploaded successfully: $fileName")
                        true
                    } else {
                        Log.e("SupabaseUpload", "Error uploading image: Upload failed")
                        false
                    }
                } else {
                    Log.e("SupabaseUpload", "Error reading image bytes")
                    false
                }
            } catch (e: Exception) {
                Log.e("SupabaseUpload", "Exception: ${e.message}")
                false
            }
        }
    }




    suspend fun deleteProfileImageFromSupabase(id: String): Boolean {
        Log.d("sfasfasfsfdafd",id)
        return withContext(Dispatchers.IO) {
            try {
                val response = supabase.storage.from("profile_images").delete("${id}.jpg")

                if (response != null){
                    Log.d("SupabaseDelete", "Image deleted successfully: $id")
                    return@withContext true
                } else {
                    Log.e("SupabaseDelete", "Failed to delete image: $id")
                    return@withContext false
                }
            } catch (e: Exception) {
                Log.e("SupabaseDelete", "Error deleting image: ${e.message}")
                return@withContext false
            }
        }
    }

    suspend fun deleteImageFromSupabase(bucket : String,id: String): Boolean {
        Log.d("DeleteRequest", "Attempting to delete image with ID: $id")

        return withContext(Dispatchers.IO) {
            try {
                val response = supabase.storage.from(bucket).delete("${id}.jpg")

                return@withContext if (response != null) {
                    Log.d("SupabaseDelete", "Image deleted successfully: $id")
                    true
                } else {
                    Log.e("SupabaseDelete", "Failed to delete image: $id, Error: ${response}")
                    false
                }
            } catch (e: Exception) {
                Log.e("SupabaseDelete", "Error deleting image: ${e.message}")
                false
            }
        }
    }



    suspend fun deleteGalleryImageFromSupabase(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = supabase.storage.from("gallery_images").delete("${id}.jpg")

                if (response != null) {
                    Log.d("SupabaseDelete", "Image deleted successfully: $id")
                    return@withContext true
                } else {
                    Log.e("SupabaseDelete", "Failed to delete image: $id")
                    return@withContext false
                }
            } catch (e: Exception) {
                Log.e("SupabaseDelete", "Error deleting image: ${e.message}")
                return@withContext false
            }
        }
    }


    suspend fun saveGalleryImageToSupabase(context: Context, uri: Uri?, id: String): Boolean {
        Log.d("adsadasddggwer",uri.toString())
        if (uri == null) return true

        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()
                inputStream?.close()

                if (byteArray != null) {
                    val fileName = "$id.jpg"

                    val response = supabase.storage
                        .from("gallery_images")
                        .upload(fileName, byteArray, upsert = true)

                    return@withContext if (response.isNotEmpty()) {
                        Log.d("SupabaseUpload", "Gallery image uploaded successfully: $fileName")
                        true
                    } else {
                        Log.e("SupabaseUpload", "Error uploading image: Upload failed")
                        false
                    }
                } else {
                    Log.e("SupabaseUpload", "Error reading image bytes")
                    false
                }
            } catch (e: Exception) {
                Log.e("SupabaseUpload", "Exception: ${e.message}")
                false
            }
        }
    }

    suspend fun saveProfileImage(context: Context, uri: Uri?) {
        val imageString = ImageConverter.uriToBase64(context, uri)
        if (imageString != null) {
            imageDao.upsertProfileImage(ProfileImageEntity(imageString = imageString))
        }
    }

    suspend fun saveProfileImageD(imageString :String?) {
        if (imageString != null) {
            imageDao.upsertProfileImage(ProfileImageEntity(imageString = imageString))
        }
    }

    suspend fun saveGalleryImageD(id : String,imageString :String?) {
        if (imageString != null) {
            imageDao.upsertGalleryImage(GalleryImageEntity(id = id, imageString = imageString))
        }
    }


    suspend fun saveMatchProfileImage(context: Context, uri: Uri?) {
        val imageString = ImageConverter.uriToBase64(context, uri)
        if (imageString != null) {
            imageDao.upsertMatchUserProfileImage(UserProfileImage(imageString = imageString))
        }
    }

    suspend fun saveMatchProfileImageD(imageString :String?) {
        if (imageString != null) {
            imageDao.upsertMatchUserProfileImage(UserProfileImage(imageString = imageString))
        }
    }

    suspend fun saveMatchGalleryImageD(id : String,imageString :String?) {
        if (imageString != null) {
            imageDao.upsertMatchUserGalleryImage(UserGalleryImageEntity(id = id, imageString = imageString))
        }
    }

    suspend fun saveGalleryImage(context: Context, uri: Uri?, id: String): Boolean {
        return try {
            val imageString = ImageConverter.uriToBase64(context, uri)
            if (imageString != null) {
                imageDao.upsertGalleryImage(GalleryImageEntity(id = id, imageString = imageString))
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("saveGalleryImage", "Error saving gallery image: ${e.message}")
            false
        }
    }

    suspend fun getProfileImage(): ProfileImageEntity? {
        return imageDao.getProfileImage()
    }

    suspend fun deleteProfileImage() {
         imageDao.deleteProfileImage()
    }

    suspend fun deleteGalleryImage(id : String){
        return imageDao.deleteGalleryImageById(id = id)
    }

    suspend fun getGalleryImages(): List<GalleryImageEntity> {
        return imageDao.getGalleryImages()
    }



    suspend fun getMatchProfileImage(): UserProfileImage? {
        return imageDao.getMatchUserProfileImage()
    }


    suspend fun getMatchGalleryImages(): List<UserGalleryImageEntity> {
        return imageDao.getMatchUserGalleryImages()
    }
}
