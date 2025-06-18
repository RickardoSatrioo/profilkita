package com.rickardosatrioabout.profilkita.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rickardosatrioabout.profilkita.network.MahasiswaApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nama: String,
    kelas: String,
    suku: String,
    gambar: String,
    navController: NavController
) {
    val viewModel: MainViewModel = viewModel()
    var newNama by remember { mutableStateOf(nama) }
    var newKelas by remember { mutableStateOf(kelas) }
    var newSuku by remember { mutableStateOf(suku) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Memanggil fungsi updateData dari ViewModel
                        viewModel.updateData(
                            nama = newNama,
                            kelas = newKelas,
                            suku = newSuku,
                            onSuccess = { navController.popBackStack() }
                        )
                    }) {
                        // Menggunakan Ikon Save yang benar
                        Icon(Icons.Filled.Save, contentDescription = "Simpan")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = MahasiswaApi.getMahasiswaUrl(gambar),
                contentDescription = "Foto Mahasiswa",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            OutlinedTextField(
                value = newNama,
                onValueChange = { newNama = it },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = newKelas,
                onValueChange = { newKelas = it },
                label = { Text("Kelas") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = newSuku,
                onValueChange = { newSuku = it },
                label = { Text("Suku") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}