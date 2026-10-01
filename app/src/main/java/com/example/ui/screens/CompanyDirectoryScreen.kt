package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.AppStrings
import com.example.model.CompanyCategory
import com.example.model.CompanyInfo
import com.example.ui.CameraUiState
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TechNavyDark
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompanyDirectoryScreen(
    state: CameraUiState,
    companies: List<CompanyInfo>,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (CompanyCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lang = state.appLanguage
    val isRu = lang == AppLanguage.RU
    val isUa = lang == AppLanguage.UA

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TOP DEVICE SUPPLIER CARD
        val analysis = state.supplierAnalysis
        if (analysis != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .border(
                            1.dp,
                            Brush.linearGradient(listOf(CyanAccent, EmeraldGreen)),
                            RoundedCornerShape(18.dp)
                        )
                        .testTag("device_supplier_card"),
                    colors = CardDefaults.cardColors(containerColor = TechNavyDark),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(CyanAccent.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = CyanAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                val topLabel = when (lang) {
                                    AppLanguage.RU -> "Анализ поставщиков для вашей модели"
                                    AppLanguage.UA -> "Аналіз постачальників для вашої моделі"
                                    AppLanguage.ES -> "Análisis de proveedores para su dispositivo"
                                    AppLanguage.PT, AppLanguage.PT_BR -> "Análise de fornecedores para o seu modelo"
                                    AppLanguage.FR -> "Analyse des fournisseurs pour votre modèle"
                                    AppLanguage.IT -> "Analisi fornitori per il tuo modello"
                                    AppLanguage.DE -> "Zulieferer-Analyse für Ihr Modell"
                                    else -> "Hardware Supplier Analysis for Your Device"
                                }
                                Text(
                                    text = topLabel,
                                    fontSize = 11.sp,
                                    color = CyanAccent,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = analysis.brandTitle,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = analysis.getSummary(lang),
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick summary breakdown
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            val sensorsLabel = when (lang) {
                                AppLanguage.RU -> "Матрицы (Сенсоры):"
                                AppLanguage.UA -> "Матриці (Сенсори):"
                                AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "Matrices (Sensores):"
                                AppLanguage.FR -> "Capteurs d'image:"
                                AppLanguage.IT -> "Sensori d'immagine:"
                                AppLanguage.DE -> "Bildsensoren:"
                                else -> "Image Sensor Foundries:"
                            }
                            Text(
                                text = sensorsLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                            Text(
                                text = analysis.mostLikelySensorVendors.joinToString(", "),
                                fontSize = 12.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val modulesLabel = when (lang) {
                                AppLanguage.RU -> "Сборщики модулей и оптика:"
                                AppLanguage.UA -> "Складальники модулів та оптика:"
                                AppLanguage.ES -> "Ensambladores de módulos y óptica:"
                                AppLanguage.PT, AppLanguage.PT_BR -> "Montadores de módulos e ótica:"
                                AppLanguage.FR -> "Assembleurs de modules et optique:"
                                AppLanguage.IT -> "Assemblatori moduli e ottica:"
                                AppLanguage.DE -> "Modulhersteller und Optik:"
                                else -> "Module Assemblers & Lenses:"
                            }
                            Text(
                                text = modulesLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldGreen
                            )
                            Text(
                                text = analysis.mostLikelyModuleMakers.joinToString(", "),
                                fontSize = 12.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val ispLabel = when (lang) {
                                AppLanguage.RU -> "Процессор обработки (ISP):"
                                AppLanguage.UA -> "Процесор обробки (ISP):"
                                AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "Processador de imagem (ISP):"
                                AppLanguage.FR -> "Processeur de signal d'image (ISP):"
                                AppLanguage.IT -> "Processore di segnale d'immagine (ISP):"
                                AppLanguage.DE -> "Bildsignalprozessor (ISP):"
                                else -> "Image Signal Processor (ISP):"
                            }
                            Text(
                                text = ispLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurpleAccent
                            )
                            Text(
                                text = analysis.mostLikelyIsp,
                                fontSize = 12.sp,
                                color = Color.White
                            )

                            if (analysis.opticPartnership != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                val opticsLabel = when (lang) {
                                    AppLanguage.RU -> "Оптическое партнёрство:"
                                    AppLanguage.UA -> "Оптичне партнерство:"
                                    AppLanguage.ES -> "Alianza óptica:"
                                    AppLanguage.PT, AppLanguage.PT_BR -> "Parceria ótica:"
                                    AppLanguage.FR -> "Partenariat optique:"
                                    AppLanguage.IT -> "Partnership ottica:"
                                    AppLanguage.DE -> "Optische Partnerschaft:"
                                    else -> "Optical Partnership:"
                                }
                                Text(
                                    text = opticsLabel,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberWarning
                                )
                                Text(
                                    text = analysis.opticPartnership,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // SEARCH BAR
        item {
            OutlinedTextField(
                value = state.companySearch,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("company_search_input"),
                placeholder = { Text(AppStrings.getSearchCompanyPlaceholder(lang)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = CyanAccent
                    )
                },
                trailingIcon = {
                    if (state.companySearch.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = TechSurfaceVariantDark,
                    focusedContainerColor = TechSurfaceDark,
                    unfocusedContainerColor = TechSurfaceDark
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // CATEGORY CHIPS
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    val allText = if (isRu) "Все компании (${companies.size})" else if (isUa) "Всі компанії (${companies.size})" else "All Companies (${companies.size})"
                    FilterChip(
                        selected = state.selectedCategory == null,
                        onClick = { onCategorySelect(null) },
                        label = { Text(allText) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = Color(0xFF00363D),
                            containerColor = TechSurfaceVariantDark.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                items(CompanyCategory.values()) { category ->
                    FilterChip(
                        selected = state.selectedCategory == category,
                        onClick = { onCategorySelect(category) },
                        label = { Text(category.getBadgeText(lang)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = Color(0xFF00363D),
                            containerColor = TechSurfaceVariantDark.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }

        // COMPANIES LIST
        if (companies.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val emptyText = if (isRu) "Компании по вашему запросу не найдены" else if (isUa) "Компанії за вашим запитом не знайдено" else "No companies found for your query"
                    Text(
                        text = emptyText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(companies, key = { it.id }) { company ->
                val categoryColor = when (company.category) {
                    CompanyCategory.SENSOR_FOUNDRY -> CyanAccent
                    CompanyCategory.MODULE_ASSEMBLER -> EmeraldGreen
                    CompanyCategory.LENS_OPTICS -> AmberWarning
                    CompanyCategory.ISP_CHIPSET -> PurpleAccent
                    CompanyCategory.SPECIALTY -> Color(0xFF14B8A6)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, categoryColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                        .testTag("company_card_${company.id}"),
                    colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = company.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = company.flagEmoji,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = company.getCountry(lang),
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            StatusBadge(
                                text = company.category.getBadgeText(lang),
                                color = categoryColor
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = company.getMarketRole(lang),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = categoryColor
                        )

                        Text(
                            text = company.getMarketShare(lang),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = company.getDescription(lang),
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        val specialNote = company.getSpecialCommentary(lang)
                        if (specialNote != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF7F1D1D).copy(alpha = 0.35f))
                                    .border(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                                    .testTag("special_commentary_${company.id}")
                            ) {
                                Column {
                                    Text(
                                        text = AppStrings.getExpertNoteTitle(lang),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFCA5A5)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = specialNote,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFFEE2E2)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Key products chips
                        val prodLabel = if (isRu) "Ключевые матрицы и продукты:" else if (isUa) "Ключові матриці та продукти:" else "Key Sensors & Products:"
                        Text(
                            text = prodLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            company.keyProducts.forEach { product ->
                                Box(
                                    modifier = Modifier
                                        .background(TechSurfaceVariantDark.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                        .border(1.dp, TechSurfaceVariantDark, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = product,
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Website Button
                        val visitText = if (isRu) "Перейти на сайт ${company.logoText}" else if (isUa) "Перейти на сайт ${company.logoText}" else "Visit ${company.logoText} Website"
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(company.website))
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("visit_company_${company.id}"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(categoryColor, categoryColor.copy(alpha = 0.5f)))),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInNew,
                                contentDescription = "Website",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = visitText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
