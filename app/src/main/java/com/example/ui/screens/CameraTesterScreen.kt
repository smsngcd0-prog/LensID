package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.view.TextureView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.camera.LiveCameraTelemetry
import com.example.ui.CameraUiState
import com.example.ui.CameraViewModel
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.TechNavyDark
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@Composable
fun CameraTesterScreen(
    state: CameraUiState,
    viewModel: CameraViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val telemetry by viewModel.telemetry.collectAsState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        viewModel.setPermissionGranted(isGranted)
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopPreview()
        }
    }

    var isPreviewActive by remember { mutableStateOf(false) }
    var currentTextureView by remember { mutableStateOf<TextureView?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Тестер и превью физических камер",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Выберите конкретный ID камеры для проверки аппаратного видеопотока",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // CAMERA SELECTOR ROW
        Text(
            text = "Доступные камеры для теста:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = CyanAccent
        )
        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.cameras) { camera ->
                val isSelected = state.selectedTesterCameraId == camera.id
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        viewModel.setTesterCameraId(camera.id)
                        isPreviewActive = false
                        viewModel.stopPreview()
                    },
                    label = {
                        Text("ID ${camera.id} (${camera.role.badge} • ${camera.megapixels}Мп)")
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyanAccent,
                        selectedLabelColor = Color(0xFF00363D),
                        containerColor = TechSurfaceDark,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.testTag("select_tester_camera_${camera.id}")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!hasCameraPermission) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AmberWarning.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = AmberWarning,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Требуется разрешение на камеру",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Для отображения аппаратного превью предоставьте доступ к камере.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                        colors = ButtonDefaults.buttonColors(containerColor = AmberWarning),
                        modifier = Modifier.testTag("grant_camera_permission_button")
                    ) {
                        Text("Разрешить доступ к камере", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // CAMERA PREVIEW SURFACE & TELEMETRY HUD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black)
                    .border(2.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { ctx ->
                        TextureView(ctx).apply {
                            currentTextureView = this
                            surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                                override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                                    if (isPreviewActive) {
                                        viewModel.startPreview(surface)
                                    }
                                }

                                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

                                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                                    viewModel.stopPreview()
                                    return true
                                }

                                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                if (!isPreviewActive) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Start",
                            tint = CyanAccent,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Нажмите 'Запустить поток' для старта",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }

                // HUD Overlay (Top-Left: Telemetry stats)
                if (isPreviewActive) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Column {
                            Text(
                                text = "FPS: ${telemetry.fps}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldGreen
                            )
                            if (telemetry.currentIso > 0) {
                                Text(
                                    text = "ISO: ${telemetry.currentIso}",
                                    fontSize = 11.sp,
                                    color = CyanAccent
                                )
                            }
                            if (telemetry.exposureTimeMs > 0) {
                                val expText = if (telemetry.exposureTimeMs < 1.0) {
                                    "1/${(1000.0 / telemetry.exposureTimeMs).toInt()}s"
                                } else {
                                    "${"%.1f".format(telemetry.exposureTimeMs)}ms"
                                }
                                Text(
                                    text = "Выдержка: $expText",
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                            if (telemetry.focalLengthMm > 0) {
                                Text(
                                    text = "Фокус: ${"%.1f".format(telemetry.focalLengthMm)} мм",
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Top-Right Flash button
                    IconButton(
                        onClick = { viewModel.toggleTorch() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .background(if (telemetry.isFlashOn) AmberWarning else Color.Black.copy(alpha = 0.6f), CircleShape)
                            .testTag("toggle_torch_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = "Flash",
                            tint = if (telemetry.isFlashOn) Color.Black else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // CONTROLS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (isPreviewActive) {
                            isPreviewActive = false
                            viewModel.stopPreview()
                        } else {
                            val st = currentTextureView?.surfaceTexture
                            if (st != null) {
                                isPreviewActive = true
                                viewModel.startPreview(st)
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("toggle_preview_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPreviewActive) Color(0xFFEF4444) else CyanAccent
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = if (isPreviewActive) Icons.Default.Stop else Icons.Default.PlayArrow,
                        contentDescription = "Toggle",
                        tint = if (isPreviewActive) Color.White else Color(0xFF00363D)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isPreviewActive) com.example.localization.AppStrings.getStopStream(state.appLanguage)
                               else com.example.localization.AppStrings.getStartStream(state.appLanguage),
                        fontWeight = FontWeight.Bold,
                        color = if (isPreviewActive) Color.White else Color(0xFF00363D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // STATUS LOG BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TechSurfaceDark, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusBadge(
                        text = if (isPreviewActive) "ONLINE" else "IDLE",
                        color = if (isPreviewActive) EmeraldGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = telemetry.statusMessage,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
