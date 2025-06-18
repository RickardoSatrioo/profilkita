package com.rickardosatrioabout.profilkita.ui.theme.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickardosatrioabout.profilkita.model.Mahasiswa
import com.rickardosatrioabout.profilkita.network.MahasiswaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Mahasiswa>())
        private set

    var status = MutableStateFlow(MahasiswaApi.ApiStatus.LOADING)
        private set

    var errorMassage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = MahasiswaApi.ApiStatus.LOADING
            try {
                data.value = MahasiswaApi.service.getMahasiswa(userId)
                status.value = MahasiswaApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = MahasiswaApi.ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, kelas: String, suku: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                MahasiswaApi.service.postMahasiswa(
                    userId,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    kelas.toRequestBody("text/plain".toMediaTypeOrNull()),
                    suku.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                retrieveData(userId)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMassage.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi updateData ditempatkan di dalam ViewModel
    fun updateData(nama: String, kelas: String, suku: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CATATAN: API Anda saat ini hanya memiliki endpoint untuk POST (membuat baru),
                // bukan PUT/PATCH (memperbarui). Memanggil postMahasiswa di sini akan
                // membuat data baru, bukan mengubah data lama.
                // Untuk "update" yang sesungguhnya, Anda perlu endpoint API baru.
                // Kode di bawah ini hanya sebagai contoh dan tidak akan berfungsi
                // tanpa endpoint update dan cara menangani gambar.

                Log.d("MainViewModel", "Update dipanggil. Nama: $nama, Kelas: $kelas, Suku: $suku")
                errorMassage.value = "Fitur update belum didukung oleh API."

                // Panggil onSuccess agar UI kembali ke halaman sebelumnya
                launch(Dispatchers.Main) {
                    onSuccess()
                }

            } catch (e: Exception) {
                errorMassage.value = "Update gagal: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val maxKb = 500
        var bmp = this
        var stream = ByteArrayOutputStream()
        var quality = 80
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        while (stream.toByteArray().size / 1024 > maxKb && quality > 20) {
            stream = ByteArrayOutputStream()
            quality -= 10
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        }
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun clearMessage() {
        errorMassage.value = null
    }
}