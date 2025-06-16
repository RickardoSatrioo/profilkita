package com.rickardosatrioabout.profilkita.ui.theme.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickardosatrioabout.profilkita.model.Mahasiswa
import com.rickardosatrioabout.profilkita.network.MahasiswaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Mahasiswa>())
            private set

    var status = MutableStateFlow(MahasiswaApi.ApiStatus.LOADING)
            private set

                init {
                    retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = MahasiswaApi.ApiStatus.LOADING
            try {
                data.value = MahasiswaApi.service.getMahasiswa()
                status.value = MahasiswaApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}