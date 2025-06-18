package com.rickardosatrioabout.profilkita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rickardosatrioabout.profilkita.navigation.SetupNavGraph
import com.rickardosatrioabout.profilkita.ui.theme.ProfilkitaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilkitaTheme {
                SetupNavGraph()
            }
        }
    }
}