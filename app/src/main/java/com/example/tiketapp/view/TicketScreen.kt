
package com.example.tiketapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.tiketapp.model.TicketStatus

import java.text.NumberFormat
import java.util.Locale

// WARNA APLIKASI
private val Blue = Color(0xFF205AC4)
private val DarkBlue = Color(0xFF164BA8)
private val LightBlue = Color(0xFFEDF3FD)
private val DarkText = Color(0xFF142238)

// ===================================
// TAMPILAN PEMESANAN TIKET
// ===================================

@Composable
fun TicketScreen(
    ticketPrice: Int,
    quantity: Int,
    buyerName: String,
    status: TicketStatus,
    onNameChange: (String) -> Unit,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    onOrderClick: () -> Unit
) {

    val isProcessing =
        status == TicketStatus.PROCESSING

    Scaffold(
        containerColor = Color.White,

        // HEADER BIRU

        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                DarkBlue,
                                Blue
                            )
                        )
                    )
                    .statusBarsPadding()
                    .height(72.dp)
                    .padding(horizontal = 20.dp),

                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Pemesanan Tiket",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            // ===========================
            // INPUT NAMA PEMBELI
            // ===========================

            Text(
                text = "Nama",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = buyerName,

                onValueChange = onNameChange,

                placeholder = {
                    Text(
                        text = "Masukkan nama Anda",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                singleLine = true,

                enabled = !isProcessing,

                shape = RoundedCornerShape(6.dp),

                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue,
                        unfocusedBorderColor =
                            Color(0xFFBFC8D4)
                    )
            )

            Spacer(Modifier.height(20.dp))

            // ===========================
            // JUMLAH TIKET
            // ===========================

            Text(
                text = "Jumlah Tiket",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                CounterButton(
                    text = "−",
                    enabled = !isProcessing,
                    onClick = onMinusClick
                )

                Text(
                    text = quantity.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                CounterButton(
                    text = "+",
                    enabled = !isProcessing,
                    onClick = onPlusClick
                )
            }

            Spacer(Modifier.height(18.dp))

            // ===========================
            // TOMBOL PESAN TIKET
            // ===========================

            Button(
                onClick = onOrderClick,

                enabled = !isProcessing,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),

                shape = RoundedCornerShape(7.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    disabledContainerColor =
                        Color(0xFFA6B2C7)
                )
            ) {

                Text(
                    text = "Pesan Tiket",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(24.dp))

            // ===========================
            // STATUS PEMESANAN
            // ===========================

            TicketStatusBox(
                status = status
            )

            Spacer(Modifier.height(30.dp))

            // ===========================
            // RINCIAN TIKET
            // ===========================

            HorizontalDivider(
                color = Color(0xFFE3E9F2)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Rincian Tiket",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Harga Tiket",
                    color = DarkText
                )

                Text(
                    text = formatRupiah(ticketPrice),
                    color = DarkText
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Jumlah Tiket",
                    color = DarkText
                )

                Text(
                    text = quantity.toString(),
                    color = DarkText
                )
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(
                color = Color(0xFFE3E9F2)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Total Harga",
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = formatRupiah(
                        ticketPrice * quantity
                    ),
                    fontWeight = FontWeight.Bold,
                    color = Blue
                )
            }
        }
    }
}

// ===================================
// TOMBOL TAMBAH DAN KURANG
// ===================================

@Composable
private fun CounterButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(
                width = 70.dp,
                height = 46.dp
            )
            .background(
                LightBlue,
                RoundedCornerShape(12.dp)
            )
            .clickable(
                enabled = enabled
            ) {
                onClick()
            },

        contentAlignment =
            Alignment.Center
    ) {

        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
    }
}

// ===================================
// STATUS BOX
// ===================================

@Composable
private fun TicketStatusBox(
    status: TicketStatus
) {

    // WARNA LATAR STATUS
    val backgroundColor = when (status) {

        TicketStatus.INITIAL ->
            Color(0xFFF0F5FD)

        TicketStatus.NAME_EMPTY ->
            Color(0xFFFDE8EC)

        TicketStatus.PROCESSING ->
            Color(0xFFE4F1FF)

        TicketStatus.SUCCESS ->
            Color(0xFFE3F6E8)
    }

    // WARNA TULISAN
    val textColor = when (status) {

        TicketStatus.INITIAL ->
            Color(0xFF42506A)

        TicketStatus.NAME_EMPTY ->
            Color(0xFFB82036)

        TicketStatus.PROCESSING ->
            Color(0xFF1552BA)

        TicketStatus.SUCCESS ->
            Color(0xFF237944)
    }

    // PESAN STATUS
    val message = when (status) {

        TicketStatus.INITIAL ->
            "Silakan pesan tiket"

        TicketStatus.NAME_EMPTY ->
            "Nama masih kosong"

        TicketStatus.PROCESSING ->
            "Memproses pesanan..."

        TicketStatus.SUCCESS ->
            "Tiket telah dipesan"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor,
                RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 18.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        // IKON SESUAI STATUS
        when (status) {

            // LOADING
            TicketStatus.PROCESSING -> {

                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = Blue,
                    strokeWidth = 2.dp
                )

                Spacer(Modifier.width(10.dp))
            }

            // SUKSES
            TicketStatus.SUCCESS -> {

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            Color(0xFF57AC75),
                            CircleShape
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = "✓",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(10.dp))
            }

            // NAMA KOSONG
            TicketStatus.NAME_EMPTY -> {

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            Color(0xFFD6384D),
                            CircleShape
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = "!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(10.dp))
            }

            TicketStatus.INITIAL -> Unit
        }

        Text(
            text = "Status:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = message,
            fontSize = 13.sp,
            color = textColor
        )
    }
}

// ===================================
// FORMAT HARGA RUPIAH
// ===================================

private fun formatRupiah(
    number: Int
): String {

    val formatter =
        NumberFormat.getNumberInstance(
            Locale.forLanguageTag("id-ID")
        )

    return "Rp " + formatter.format(number)
}
