
package com.example.tiketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

import com.example.tiketapp.model.TicketStatus
import com.example.tiketapp.view.TicketScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF205AC4),
                    background = Color.White,
                    surface = Color.White
                )
            ) {
                TicketApp()
            }
        }
    }
}

// =====================================
// PARENT COMPOSABLE
// SEMUA STATE DIKELOLA DI SINI
// =====================================

@Composable
fun TicketApp() {

    // STATE 1: HARGA TIKET
    val ticketPrice by rememberSaveable {
        mutableIntStateOf(50000)
    }

    // STATE 2: JUMLAH TIKET
    var quantity by rememberSaveable {
        mutableIntStateOf(1)
    }

    // STATE 3: NAMA PEMBELI
    var buyerName by rememberSaveable {
        mutableStateOf("")
    }

    // STATE STATUS
    var status by rememberSaveable {
        mutableStateOf(TicketStatus.INITIAL)
    }

    // PEMICU LAUNCHED EFFECT
    var orderRequest by rememberSaveable {
        mutableIntStateOf(0)
    }

    val isProcessing =
        status == TicketStatus.PROCESSING

    // =====================================
    // LAUNCHED EFFECT
    // =====================================

    LaunchedEffect(orderRequest) {

        if (
            orderRequest > 0 &&
            status == TicketStatus.PROCESSING
        ) {

            // Tunggu 5 detik
            delay(5000L)

            // Ubah status menjadi berhasil
            status = TicketStatus.SUCCESS
        }
    }

    // =====================================
    // MEMANGGIL CHILD COMPOSABLE
    // =====================================

    TicketScreen(

        ticketPrice = ticketPrice,

        quantity = quantity,

        buyerName = buyerName,

        status = status,

        // CALLBACK INPUT NAMA
        onNameChange = { newName ->

            if (!isProcessing) {
                buyerName = newName

                status = TicketStatus.INITIAL
            }
        },

        // CALLBACK TOMBOL MINUS
        onMinusClick = {

            if (!isProcessing && quantity > 1) {

                quantity--

                status = TicketStatus.INITIAL
            }
        },

        // CALLBACK TOMBOL PLUS
        onPlusClick = {

            if (!isProcessing) {

                quantity++

                status = TicketStatus.INITIAL
            }
        },

        // CALLBACK PESAN TIKET
        onOrderClick = {

            if (buyerName.isBlank()) {

                // Nama kosong
                status = TicketStatus.NAME_EMPTY

            } else if (!isProcessing) {

                // Mulai pemesanan
                status = TicketStatus.PROCESSING

                // Memicu LaunchedEffect
                orderRequest++
            }
        }
    )
}
