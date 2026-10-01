package com.example.localization

import com.example.model.CameraFacing
import com.example.model.CameraRole
import java.util.Locale

enum class AppLanguage(val code: String, val title: String, val flag: String) {
    RU("ru", "Русский", "🇷🇺"),
    UA("ua", "Українська", "🇺🇦"),
    EN("en", "English", "🇬🇧"),
    ES("es", "Español", "🇪🇸"),
    PT("pt", "Português", "🇵🇹"),
    PT_BR("pt-br", "Português (Brasil)", "🇧🇷"),
    FR("fr", "Français", "🇫🇷"),
    IT("it", "Italiano", "🇮🇹"),
    DE("de", "Deutsch", "🇩🇪");

    companion object {
        fun fromCode(code: String): AppLanguage =
            values().firstOrNull { it.code.equals(code, ignoreCase = true) } ?: EN

        fun fromSystemLocale(locale: Locale): AppLanguage {
            val lang = locale.language.lowercase()
            val country = locale.country.lowercase()
            return when {
                lang == "ru" -> RU
                lang == "uk" || lang == "ua" -> UA
                lang == "es" -> ES
                lang == "pt" && (country == "br" || country == "brazil") -> PT_BR
                lang == "pt" -> PT
                lang == "fr" -> FR
                lang == "it" -> IT
                lang == "de" -> DE
                else -> EN
            }
        }
    }
}

object AppStrings {

    fun getVibecodingWarningTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> "Внимание: Режим Vibecoding"
        AppLanguage.UA -> "Увага: Режим Вайбкодингу"
        AppLanguage.ES -> "Atención: Modo Vibecoding"
        AppLanguage.PT, AppLanguage.PT_BR -> "Atenção: Modo Vibecoding"
        AppLanguage.FR -> "Attention: Mode Vibecoding"
        AppLanguage.IT -> "Attenzione: Modalità Vibecoding"
        AppLanguage.DE -> "Achtung: Vibecoding-Modus"
        else -> "Notice: Vibecoding Mode"
    }

    fun getVibecodingWarningMessage(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> "Внимание, Данное приложение является Вайбкодом, и возможно будут баги, и прочее, а также возможно устарелая информация по типу \"Helio G99 вместо G100 Ultra\" т.к. пока что Вайбкод Gemini может показивать информацию которая устарела на пару лет, поєтому просим проверять некоторую информацию точнее."
        AppLanguage.UA -> "Увага, Цей додаток є Вайбкодом, і можливо будуть баги, та інше, а також можливо застаріла інформація за типом \"Helio G99 замість G100 Ultra\", оскільки поки що Вайбкод Gemini може показувати інформацію, яка застаріла на пару років, тому просимо перевіряти деяку інформацію точніше."
        AppLanguage.ES -> "Atención: Esta aplicación es un Vibecode y puede contener errores, además de información potencialmente desactualizada como \"Helio G99 en lugar de G100 Ultra\", ya que Gemini Vibecode puede mostrar datos con desfase temporal. Por favor, verifique la información crítica con precisión."
        AppLanguage.PT, AppLanguage.PT_BR -> "Atenção: Este aplicativo é um Vibecode e pode conter bugs, além de informações possivelmente desatualizadas como \"Helio G99 em vez de G100 Ultra\", pois o Gemini Vibecode pode apresentar dados defasados. Por favor, verifique informações específicas com atenção."
        AppLanguage.FR -> "Attention: Cette application est un Vibecode et peut comporter des bugs ainsi que des informations potentiellement obsolètes (comme \"Helio G99 au lieu de G100 Ultra\"), Gemini Vibecode pouvant présenter des données en décalage. Veuillez vérifier les informations clés."
        AppLanguage.IT -> "Attenzione: Questa applicazione è un Vibecode e potrebbe contenere bug nonché informazioni obsolete come \"Helio G99 invece di G100 Ultra\", poiché Gemini Vibecode può mostrare dati non recenti. Si invita a verificare le informazioni con attenzione."
        AppLanguage.DE -> "Achtung: Diese Anwendung ist ein Vibecode und kann Fehler sowie veraltete Informationen enthalten (z. B. \"Helio G99 statt G100 Ultra\"), da Gemini Vibecode teilweise ältere Daten anzeigt. Bitte überprüfen Sie wichtige Angaben sorgfältig."
        else -> "Notice: This application is a Vibecode and may contain bugs, as well as potentially outdated info like \"Helio G99 instead of G100 Ultra\" because Gemini Vibecoding may reflect older database records. Please double-check critical specifications."
    }

    fun getVibecodingWarningConfirm(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> "Понятно"
        AppLanguage.UA -> "Зрозуміло"
        AppLanguage.ES -> "Entendido"
        AppLanguage.PT, AppLanguage.PT_BR -> "Entendido"
        AppLanguage.FR -> "Compris"
        AppLanguage.IT -> "Ho capito"
        AppLanguage.DE -> "Verstanden"
        else -> "Understood"
    }

    fun getTabCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Камеры"
        AppLanguage.UA -> "Камери"
        AppLanguage.ES -> "Cámaras"
        AppLanguage.PT, AppLanguage.PT_BR -> "Câmeras"
        AppLanguage.FR -> "Appareils"
        AppLanguage.IT -> "Fotocamere"
        AppLanguage.DE -> "Kameras"
        else -> "Cameras"
    }

    fun getTabHardware(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Параметры"
        AppLanguage.UA -> "Параметри"
        AppLanguage.ES -> "Parámetros"
        AppLanguage.PT, AppLanguage.PT_BR -> "Parâmetros"
        AppLanguage.FR -> "Paramètres"
        AppLanguage.IT -> "Parametri"
        AppLanguage.DE -> "Parameter"
        else -> "Hardware"
    }

    fun getTabCompanies(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Компании"
        AppLanguage.UA -> "Компанії"
        AppLanguage.ES -> "Compañías"
        AppLanguage.PT, AppLanguage.PT_BR -> "Empresas"
        AppLanguage.FR -> "Entreprises"
        AppLanguage.IT -> "Aziende"
        AppLanguage.DE -> "Hersteller"
        else -> "Companies"
    }

    fun getTabTester(lang: AppLanguage) = when (lang) {
        AppLanguage.RU, AppLanguage.UA -> "Тестер"
        AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "Testador"
        AppLanguage.FR -> "Testeur"
        AppLanguage.IT -> "Tester"
        AppLanguage.DE -> "Tester"
        else -> "Tester"
    }

    fun getTabSensors(lang: AppLanguage) = when (lang) {
        AppLanguage.RU, AppLanguage.UA -> "База"
        AppLanguage.ES -> "Sensores"
        AppLanguage.PT, AppLanguage.PT_BR -> "Sensores"
        AppLanguage.FR -> "Capteurs"
        AppLanguage.IT -> "Sensori"
        AppLanguage.DE -> "Sensoren"
        else -> "Sensors"
    }

    fun getTabReport(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Отчёт"
        AppLanguage.UA -> "Звіт"
        AppLanguage.ES -> "Informe"
        AppLanguage.PT, AppLanguage.PT_BR -> "Relatório"
        AppLanguage.FR -> "Rapport"
        AppLanguage.IT -> "Report"
        AppLanguage.DE -> "Bericht"
        else -> "Report"
    }

    fun getHeaderTitle(tab: Int, lang: AppLanguage) = when (tab) {
        0 -> when (lang) {
            AppLanguage.RU -> "Физические камеры телефона"
            AppLanguage.UA -> "Фізичні камери телефону"
            AppLanguage.ES -> "Cámaras físicas del teléfono"
            AppLanguage.PT, AppLanguage.PT_BR -> "Câmeras físicas do telefone"
            AppLanguage.FR -> "Caméras physiques du téléphone"
            AppLanguage.IT -> "Fotocamere fisiche del telefono"
            AppLanguage.DE -> "Physische Telefonkameras"
            else -> "Physical Phone Cameras"
        }
        1 -> when (lang) {
            AppLanguage.RU -> "Параметры и аудит устройства"
            AppLanguage.UA -> "Параметри та аудит пристрою"
            AppLanguage.ES -> "Parámetros y auditoría de hardware"
            AppLanguage.PT, AppLanguage.PT_BR -> "Parâmetros e auditoria de hardware"
            AppLanguage.FR -> "Paramètres et audit matériel"
            AppLanguage.IT -> "Parametri e audit hardware"
            AppLanguage.DE -> "Geräteparameter und Hardware-Audit"
            else -> "Device Specs & Hardware Audit"
        }
        2 -> when (lang) {
            AppLanguage.RU -> "Компании и производители"
            AppLanguage.UA -> "Компанії та виробники"
            AppLanguage.ES -> "Fabricantes y proveedores"
            AppLanguage.PT, AppLanguage.PT_BR -> "Fabricantes e fornecedores"
            AppLanguage.FR -> "Fabricants et fournisseurs"
            AppLanguage.IT -> "Produttori e fornitori"
            AppLanguage.DE -> "Hersteller und Zulieferer"
            else -> "Suppliers & Manufacturers"
        }
        3 -> when (lang) {
            AppLanguage.RU -> "Тестирование видеопотока"
            AppLanguage.UA -> "Тестування відеопотоку"
            AppLanguage.ES -> "Prueba de transmisión en vivo"
            AppLanguage.PT, AppLanguage.PT_BR -> "Teste de transmissão de câmera"
            AppLanguage.FR -> "Test du flux de caméra en direct"
            AppLanguage.IT -> "Test del flusso video in tempo reale"
            AppLanguage.DE -> "Live-Kameratest"
            else -> "Live Camera Stream Test"
        }
        4 -> when (lang) {
            AppLanguage.RU -> "База мобильных сенсоров"
            AppLanguage.UA -> "База мобільних сенсорів"
            AppLanguage.ES -> "Catálogo de sensores móviles"
            AppLanguage.PT, AppLanguage.PT_BR -> "Catálogo de sensores móveis"
            AppLanguage.FR -> "Catalogue des capteurs mobiles"
            AppLanguage.IT -> "Catalogo dei sensori mobili"
            AppLanguage.DE -> "Katalog mobiler Sensoren"
            else -> "Mobile Sensor Encyclopedia"
        }
        5 -> when (lang) {
            AppLanguage.RU -> "Технический аудит камер"
            AppLanguage.UA -> "Технічний аудит камер"
            AppLanguage.ES -> "Auditoría técnica de cámaras"
            AppLanguage.PT, AppLanguage.PT_BR -> "Auditoria técnica de câmeras"
            AppLanguage.FR -> "Audit technique des caméras"
            AppLanguage.IT -> "Audit tecnico fotocamere"
            AppLanguage.DE -> "Technisches Kamera-Audit"
            else -> "Technical Camera Audit"
        }
        else -> "CamSpec Pro"
    }

    fun getCameraRoleTitle(role: CameraRole, lang: AppLanguage): String = when (role) {
        CameraRole.MAIN_WIDE -> when (lang) {
            AppLanguage.RU -> "Основная (Широкоугольная)"
            AppLanguage.UA -> "Основна (Ширококутна)"
            AppLanguage.ES -> "Principal (Gran angular)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Principal (Grande angular)"
            AppLanguage.FR -> "Principale (Grand angle)"
            AppLanguage.IT -> "Principale (Grandangolare)"
            AppLanguage.DE -> "Hauptkamera (Weitwinkel)"
            else -> "Main (Wide Angle)"
        }
        CameraRole.ULTRA_WIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная"
            AppLanguage.UA -> "Надширококутна"
            AppLanguage.ES -> "Ultra gran angular"
            AppLanguage.PT, AppLanguage.PT_BR -> "Ultra grande angular"
            AppLanguage.FR -> "Ultra grand angle"
            AppLanguage.IT -> "Ultra grandangolare"
            AppLanguage.DE -> "Ultraweitwinkel"
            else -> "Ultra-Wide"
        }
        CameraRole.TELEPHOTO -> when (lang) {
            AppLanguage.RU -> "Телеобъектив / Зум"
            AppLanguage.UA -> "Телеоб'єктив / Зум"
            AppLanguage.ES -> "Teleobjetivo / Zoom"
            AppLanguage.PT, AppLanguage.PT_BR -> "Telefoto / Zoom"
            AppLanguage.FR -> "Téléobjectif / Zoom"
            AppLanguage.IT -> "Teleobiettivo / Zoom"
            AppLanguage.DE -> "Teleobjektiv / Zoom"
            else -> "Telephoto / Optical Zoom"
        }
        CameraRole.PERISCOPE -> when (lang) {
            AppLanguage.RU -> "Перископический телеобъектив"
            AppLanguage.UA -> "Перископічний телеоб'єктив"
            AppLanguage.ES -> "Teleobjetivo periscopio"
            AppLanguage.PT, AppLanguage.PT_BR -> "Periscópio telefoto"
            AppLanguage.FR -> "Périscope téléobjectif"
            AppLanguage.IT -> "Teleobiettivo a periscopio"
            AppLanguage.DE -> "Periskop-Teleobjektiv"
            else -> "Periscope Telephoto"
        }
        CameraRole.MACRO -> when (lang) {
            AppLanguage.RU, AppLanguage.UA -> "Макро-камера"
            AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "Câmera Macro"
            AppLanguage.FR -> "Caméra Macro"
            AppLanguage.IT -> "Fotocamera Macro"
            AppLanguage.DE -> "Makrokamera"
            else -> "Macro Camera"
        }
        CameraRole.DEPTH_TOF -> when (lang) {
            AppLanguage.RU -> "Сенсор глубины / ToF"
            AppLanguage.UA -> "Сенсор глибини / ToF"
            AppLanguage.ES -> "Sensor de profundidad / ToF"
            AppLanguage.PT, AppLanguage.PT_BR -> "Sensor de profundidade / ToF"
            AppLanguage.FR -> "Capteur de profondeur / ToF"
            AppLanguage.IT -> "Sensore di profondità / ToF"
            AppLanguage.DE -> "Tiefensensor / ToF"
            else -> "Depth / ToF Sensor"
        }
        CameraRole.MONOCHROME -> when (lang) {
            AppLanguage.RU -> "Монохромный сенсор"
            AppLanguage.UA -> "Монохромний сенсор"
            AppLanguage.ES -> "Sensor monocromático"
            AppLanguage.PT, AppLanguage.PT_BR -> "Sensor monocromático"
            AppLanguage.FR -> "Capteur monochrome"
            AppLanguage.IT -> "Sensore monocromatico"
            AppLanguage.DE -> "Monochromsensor"
            else -> "Monochrome Sensor"
        }
        CameraRole.FRONT_SELFIE -> when (lang) {
            AppLanguage.RU -> "Фронтальная (Селфи)"
            AppLanguage.UA -> "Фронтальна (Селфі)"
            AppLanguage.ES -> "Frontal (Selfie)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Frontal (Selfie)"
            AppLanguage.FR -> "Avant (Selfie)"
            AppLanguage.IT -> "Frontale (Selfie)"
            AppLanguage.DE -> "Frontkamera (Selfie)"
            else -> "Front (Selfie)"
        }
        CameraRole.FRONT_ULTRAWIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная селфи"
            AppLanguage.UA -> "Надширококутна селфі"
            AppLanguage.ES -> "Selfie ultra gran angular"
            AppLanguage.PT, AppLanguage.PT_BR -> "Selfie ultra grande angular"
            AppLanguage.FR -> "Selfie ultra grand angle"
            AppLanguage.IT -> "Selfie ultra grandangolare"
            AppLanguage.DE -> "Ultraweitwinkel-Selfie"
            else -> "Ultra-Wide Selfie"
        }
        CameraRole.UNKNOWN -> when (lang) {
            AppLanguage.RU -> "Дополнительная камера"
            AppLanguage.UA -> "Додаткова камера"
            AppLanguage.ES -> "Cámara auxiliar"
            AppLanguage.PT, AppLanguage.PT_BR -> "Câmera auxiliar"
            AppLanguage.FR -> "Caméra auxiliaire"
            AppLanguage.IT -> "Fotocamera ausiliaria"
            AppLanguage.DE -> "Zusatzkamera"
            else -> "Auxiliary Camera"
        }
    }

    fun getFacingTitle(facing: CameraFacing, lang: AppLanguage): String = when (facing) {
        CameraFacing.BACK -> when (lang) {
            AppLanguage.RU -> "Задняя (Основной блок)"
            AppLanguage.UA -> "Задня (Основний блок)"
            AppLanguage.ES -> "Trasera (Módulo principal)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Traseira (Módulo principal)"
            AppLanguage.FR -> "Arrière (Module principal)"
            AppLanguage.IT -> "Posteriore (Modulo principale)"
            AppLanguage.DE -> "Rückseite (Hauptmodul)"
            else -> "Rear (Main module)"
        }
        CameraFacing.FRONT -> when (lang) {
            AppLanguage.RU -> "Фронтальная (Экран)"
            AppLanguage.UA -> "Фронтальна (Екран)"
            AppLanguage.ES -> "Frontal (Pantalla)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Frontal (Tela)"
            AppLanguage.FR -> "Avant (Écran)"
            AppLanguage.IT -> "Frontale (Schermo)"
            AppLanguage.DE -> "Vorderseite (Bildschirm)"
            else -> "Front (Screen)"
        }
        CameraFacing.EXTERNAL -> when (lang) {
            AppLanguage.RU -> "Внешняя (USB/UVC)"
            AppLanguage.UA -> "Зовнішня (USB/UVC)"
            AppLanguage.ES -> "Externa (USB/UVC)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Externa (USB/UVC)"
            AppLanguage.FR -> "Externe (USB/UVC)"
            AppLanguage.IT -> "Esterna (USB/UVC)"
            AppLanguage.DE -> "Extern (USB/UVC)"
            else -> "External (USB/UVC)"
        }
        CameraFacing.UNKNOWN -> when (lang) {
            AppLanguage.RU -> "Неизвестно"
            AppLanguage.UA -> "Невідомо"
            AppLanguage.ES -> "Desconocido"
            AppLanguage.PT, AppLanguage.PT_BR -> "Desconhecido"
            AppLanguage.FR -> "Inconnu"
            AppLanguage.IT -> "Sconosciuto"
            AppLanguage.DE -> "Unbekannt"
            else -> "Unknown"
        }
    }

    fun getFilterTitle(filterName: String, lang: AppLanguage) = when (filterName) {
        "ALL" -> when (lang) {
            AppLanguage.RU -> "Все камеры"
            AppLanguage.UA -> "Всі камери"
            AppLanguage.ES -> "Todas las cámaras"
            AppLanguage.PT, AppLanguage.PT_BR -> "Todas as câmeras"
            AppLanguage.FR -> "Toutes les caméras"
            AppLanguage.IT -> "Tutte le fotocamere"
            AppLanguage.DE -> "Alle Kameras"
            else -> "All Cameras"
        }
        "BACK" -> when (lang) {
            AppLanguage.RU -> "Основные (Задние)"
            AppLanguage.UA -> "Основні (Задні)"
            AppLanguage.ES -> "Traseras"
            AppLanguage.PT, AppLanguage.PT_BR -> "Traseiras"
            AppLanguage.FR -> "Arrières"
            AppLanguage.IT -> "Posteriori"
            AppLanguage.DE -> "Hauptkameras"
            else -> "Rear Cameras"
        }
        "FRONT" -> when (lang) {
            AppLanguage.RU -> "Фронтальные"
            AppLanguage.UA -> "Фронтальні"
            AppLanguage.ES -> "Frontales"
            AppLanguage.PT, AppLanguage.PT_BR -> "Frontais"
            AppLanguage.FR -> "Frontales"
            AppLanguage.IT -> "Frontali"
            AppLanguage.DE -> "Frontkameras"
            else -> "Front Cameras"
        }
        "PHYSICAL" -> when (lang) {
            AppLanguage.RU -> "Физические сенсоры"
            AppLanguage.UA -> "Фізичні сенсори"
            AppLanguage.ES -> "Sensores físicos"
            AppLanguage.PT, AppLanguage.PT_BR -> "Sensores físicos"
            AppLanguage.FR -> "Capteurs physiques"
            AppLanguage.IT -> "Sensori fisici"
            AppLanguage.DE -> "Physische Sensoren"
            else -> "Physical Sensors"
        }
        else -> filterName
    }

    fun getScanningText(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Сканирование аппаратных сенсоров..."
        AppLanguage.UA -> "Сканування апаратних сенсорів..."
        AppLanguage.ES -> "Escaneando sensores de hardware..."
        AppLanguage.PT, AppLanguage.PT_BR -> "Escaneando sensores de hardware..."
        AppLanguage.FR -> "Analyse des capteurs matériels..."
        AppLanguage.IT -> "Scansione sensori hardware..."
        AppLanguage.DE -> "Hardware-Sensoren werden gescannt..."
        else -> "Scanning hardware sensors..."
    }

    fun getTotalModules(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Всего модулей"
        AppLanguage.UA -> "Всього модулів"
        AppLanguage.ES -> "Total de módulos"
        AppLanguage.PT, AppLanguage.PT_BR -> "Total de módulos"
        AppLanguage.FR -> "Total des modules"
        AppLanguage.IT -> "Moduli totali"
        AppLanguage.DE -> "Module insgesamt"
        else -> "Total Modules"
    }

    fun getBackCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Задних камер"
        AppLanguage.UA -> "Задніх камер"
        AppLanguage.ES -> "Cámaras traseras"
        AppLanguage.PT, AppLanguage.PT_BR -> "Câmeras traseiras"
        AppLanguage.FR -> "Caméras arrières"
        AppLanguage.IT -> "Fotocamere posteriori"
        AppLanguage.DE -> "Rückkameras"
        else -> "Rear Cameras"
    }

    fun getFrontCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Фронтальных"
        AppLanguage.UA -> "Фронтальних"
        AppLanguage.ES -> "Frontales"
        AppLanguage.PT, AppLanguage.PT_BR -> "Frontais"
        AppLanguage.FR -> "Frontales"
        AppLanguage.IT -> "Frontali"
        AppLanguage.DE -> "Frontkameras"
        else -> "Front"
    }

    fun getPhysicalSensors(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Физических"
        AppLanguage.UA -> "Фізичних"
        AppLanguage.ES -> "Físicos"
        AppLanguage.PT, AppLanguage.PT_BR -> "Físicos"
        AppLanguage.FR -> "Physiques"
        AppLanguage.IT -> "Fisici"
        AppLanguage.DE -> "Physisch"
        else -> "Physical"
    }

    fun getSuppliersForYourPhone(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поставщики для вашей модели"
        AppLanguage.UA -> "Постачальники для вашої моделі"
        AppLanguage.ES -> "Proveedores para su modelo"
        AppLanguage.PT, AppLanguage.PT_BR -> "Fornecedores do seu modelo"
        AppLanguage.FR -> "Fournisseurs pour votre modèle"
        AppLanguage.IT -> "Fornitori per il tuo modello"
        AppLanguage.DE -> "Zulieferer für Ihr Modell"
        else -> "Component Suppliers for Your Model"
    }

    fun getSearchCompanyPlaceholder(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поиск компании или технологии..."
        AppLanguage.UA -> "Пошук компанії або технології..."
        AppLanguage.ES -> "Buscar empresa o tecnología..."
        AppLanguage.PT, AppLanguage.PT_BR -> "Buscar empresa ou tecnologia..."
        AppLanguage.FR -> "Rechercher une entreprise ou une technologie..."
        AppLanguage.IT -> "Cerca azienda o tecnologia..."
        AppLanguage.DE -> "Firma oder Technologie suchen..."
        else -> "Search company or technology..."
    }

    fun getSearchSensorPlaceholder(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поиск сенсора (например, IMX890, HP2, OV50H)..."
        AppLanguage.UA -> "Пошук сенсора (наприклад, IMX890, HP2, OV50H)..."
        AppLanguage.ES -> "Buscar sensor (ej. IMX890, HP2, OV50H)..."
        AppLanguage.PT, AppLanguage.PT_BR -> "Buscar sensor (ex. IMX890, HP2, OV50H)..."
        AppLanguage.FR -> "Rechercher un capteur (ex. IMX890, HP2, OV50H)..."
        AppLanguage.IT -> "Cerca sensore (es. IMX890, HP2, OV50H)..."
        AppLanguage.DE -> "Sensor suchen (z. B. IMX890, HP2, OV50H)..."
        else -> "Search sensor (e.g. IMX890, HP2, OV50H)..."
    }

    fun getCopyButton(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Скопировать"
        AppLanguage.UA -> "Скопіювати"
        AppLanguage.ES -> "Copiar"
        AppLanguage.PT, AppLanguage.PT_BR -> "Copiar"
        AppLanguage.FR -> "Copier"
        AppLanguage.IT -> "Copia"
        AppLanguage.DE -> "Kopieren"
        else -> "Copy"
    }

    fun getShareButton(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поделиться"
        AppLanguage.UA -> "Поділитися"
        AppLanguage.ES -> "Compartir"
        AppLanguage.PT, AppLanguage.PT_BR -> "Compartilhar"
        AppLanguage.FR -> "Partager"
        AppLanguage.IT -> "Condividi"
        AppLanguage.DE -> "Teilen"
        else -> "Share"
    }

    fun getCopiedToast(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Отчёт скопирован в буфер обмена"
        AppLanguage.UA -> "Звіт скопійовано в буфер обміну"
        AppLanguage.ES -> "Informe copiado al portapapeles"
        AppLanguage.PT, AppLanguage.PT_BR -> "Relatório copiado para a área de transferência"
        AppLanguage.FR -> "Rapport copié dans le presse-papiers"
        AppLanguage.IT -> "Report copiato negli appunti"
        AppLanguage.DE -> "Bericht in die Zwischenablage kopiert"
        else -> "Report copied to clipboard"
    }

    fun getStartStream(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Запустить поток"
        AppLanguage.UA -> "Запустити потік"
        AppLanguage.ES -> "Iniciar transmisión"
        AppLanguage.PT, AppLanguage.PT_BR -> "Iniciar transmissão"
        AppLanguage.FR -> "Démarrer le flux"
        AppLanguage.IT -> "Avvia streaming"
        AppLanguage.DE -> "Stream starten"
        else -> "Start Stream"
    }

    fun getStopStream(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Остановить поток"
        AppLanguage.UA -> "Зупинити потік"
        AppLanguage.ES -> "Detener transmisión"
        AppLanguage.PT, AppLanguage.PT_BR -> "Parar transmissão"
        AppLanguage.FR -> "Arrêter le flux"
        AppLanguage.IT -> "Ferma streaming"
        AppLanguage.DE -> "Stream stoppen"
        else -> "Stop Stream"
    }

    fun getSamsungCommentary(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "данная компания явно преувеличивает свои достижения, используя аггресивний маркетинг, монополию, а также аггресивное продвижение, имея тезнологическую и по всем параметрам отсталость минимум 5-10 лет а то и 20."
        AppLanguage.UA -> "дана компанія явно перебільшує свої досягнення, використовуючи агресивний маркетинг, монополію, а також агресивне просування, маючи технологічну та за всіма параметрами відсталість мінімум 5-10 років а то й 20."
        AppLanguage.ES -> "esta empresa exagera notablemente sus logros mediante marketing agresivo y tácticas de monopolio, presentando un retraso tecnológico considerable de al menos 5 a 10 años."
        AppLanguage.PT, AppLanguage.PT_BR -> "esta empresa exagera notoriamente suas conquistas através de marketing agressivo e monopólio, apresentando atraso tecnológico de 5 a 10 anos."
        AppLanguage.FR -> "cette entreprise exagère nettement ses réalisations par un marketing agressif et des pratiques de monopole, accusant un retard technologique d'au moins 5 à 10 ans."
        AppLanguage.IT -> "questa azienda esagera notevolmente i propri risultati attraverso marketing aggressivo e monopolio, con un ritardo tecnologico di almeno 5-10 anni."
        AppLanguage.DE -> "dieses Unternehmen übertreibt seine Errungenschaften durch aggressives Marketing und Monopolstellung, während es technologisch um 5-10 Jahre zurückliegt."
        else -> "this company clearly exaggerates its achievements, utilizing aggressive marketing, monopoly tactics, and aggressive promotion, while lagging technologically by at least 5-10 years."
    }

    fun getResolutionLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Разрешение"
        AppLanguage.UA -> "Роздільність"
        AppLanguage.ES -> "Resolución"
        AppLanguage.PT, AppLanguage.PT_BR -> "Resolução"
        AppLanguage.FR -> "Résolution"
        AppLanguage.IT -> "Risoluzione"
        AppLanguage.DE -> "Auflösung"
        else -> "Resolution"
    }

    fun getSensorFormatLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Формат матрицы"
        AppLanguage.UA -> "Формат матриці"
        AppLanguage.ES -> "Formato del sensor"
        AppLanguage.PT, AppLanguage.PT_BR -> "Formato do sensor"
        AppLanguage.FR -> "Format du capteur"
        AppLanguage.IT -> "Formato sensore"
        AppLanguage.DE -> "Sensorformat"
        else -> "Sensor Format"
    }

    fun getPixelLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Пиксель"
        AppLanguage.UA -> "Піксель"
        AppLanguage.ES -> "Píxel"
        AppLanguage.PT, AppLanguage.PT_BR -> "Pixel"
        AppLanguage.FR -> "Pixel"
        AppLanguage.IT -> "Pixel"
        AppLanguage.DE -> "Pixelgröße"
        else -> "Pixel Pitch"
    }

    fun getSensorFoundryLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Производитель матрицы"
        AppLanguage.UA -> "Виробник матриці"
        AppLanguage.ES -> "Fabricante del sensor"
        AppLanguage.PT, AppLanguage.PT_BR -> "Fabricante do sensor"
        AppLanguage.FR -> "Fonderie du capteur"
        AppLanguage.IT -> "Produttore del sensore"
        AppLanguage.DE -> "Sensorhersteller"
        else -> "Sensor Foundry"
    }

    fun getSensorModelLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Модель матрицы"
        AppLanguage.UA -> "Модель матриці"
        AppLanguage.ES -> "Modelo del sensor"
        AppLanguage.PT, AppLanguage.PT_BR -> "Modelo do sensor"
        AppLanguage.FR -> "Modèle du capteur"
        AppLanguage.IT -> "Modello del sensore"
        AppLanguage.DE -> "Sensormodell"
        else -> "Sensor Model"
    }

    fun getCloseSpecLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Закрыть спецификацию"
        AppLanguage.UA -> "Закрити специфікацію"
        AppLanguage.ES -> "Cerrar especificación"
        AppLanguage.PT, AppLanguage.PT_BR -> "Fechar especificação"
        AppLanguage.FR -> "Fermer les spécifications"
        AppLanguage.IT -> "Chiudi specifica"
        AppLanguage.DE -> "Spezifikation schließen"
        else -> "Close Specification"
    }

    fun getAllSpecsLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Все характеристики сенсора и оптики"
        AppLanguage.UA -> "Всі характеристики сенсора та оптики"
        AppLanguage.ES -> "Todas las especificaciones del sensor y óptica"
        AppLanguage.PT, AppLanguage.PT_BR -> "Todas as especificações do sensor e ótica"
        AppLanguage.FR -> "Toutes les spécifications du capteur et de l'optique"
        AppLanguage.IT -> "Tutte le specifiche del sensore e dell'ottica"
        AppLanguage.DE -> "Alle Sensor- und Optikspezifikationen"
        else -> "All Sensor & Optical Specifications"
    }

    fun getSpecialAssessmentLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Особая экспертная оценка:"
        AppLanguage.UA -> "Особлива експертна оцінка:"
        AppLanguage.ES -> "Evaluación especial de expertos:"
        AppLanguage.PT, AppLanguage.PT_BR -> "Avaliação especial de especialistas:"
        AppLanguage.FR -> "Évaluation d'experts:"
        AppLanguage.IT -> "Valutazione speciale degli esperti:"
        AppLanguage.DE -> "Besondere Expertenbewertung:"
        else -> "Special Expert Assessment:"
    }

    fun getDetectionSourceLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Источник детекции сенсора"
        AppLanguage.UA -> "Джерело детекції сенсора"
        AppLanguage.ES -> "Fuente de detección del sensor"
        AppLanguage.PT, AppLanguage.PT_BR -> "Fonte de detecção do sensor"
        AppLanguage.FR -> "Source de détection du capteur"
        AppLanguage.IT -> "Sorgente di rilevamento sensore"
        AppLanguage.DE -> "Sensor-Erkennungsquelle"
        else -> "Sensor Detection Source"
    }

    fun getTesterTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Тестер и превью физических камер"
        AppLanguage.UA -> "Тестер та прев'ю фізичних камер"
        AppLanguage.ES -> "Probador de flujo de cámara física"
        AppLanguage.PT, AppLanguage.PT_BR -> "Testador de transmissão de câmera física"
        AppLanguage.FR -> "Testeur de flux de caméras physiques"
        AppLanguage.IT -> "Tester stream fotocamere fisiche"
        AppLanguage.DE -> "Physischer Kamerastream-Tester"
        else -> "Physical Camera Stream Tester"
    }

    fun getTesterSubtitle(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Выберите конкретный ID камеры для проверки аппаратного видеопотока"
        AppLanguage.UA -> "Оберіть конкретний ID камери для перевірки апаратного відеопотоку"
        AppLanguage.ES -> "Seleccione un ID de cámara para verificar la transmisión en vivo de hardware"
        AppLanguage.PT, AppLanguage.PT_BR -> "Selecione um ID de câmera para verificar a transmissão de hardware"
        AppLanguage.FR -> "Sélectionnez un ID de caméra pour vérifier le flux vidéo matériel"
        AppLanguage.IT -> "Seleziona l'ID della fotocamera per verificare lo stream hardware"
        AppLanguage.DE -> "Wählen Sie eine Kamera-ID, um den Hardware-Videostream zu testen"
        else -> "Select camera ID to verify hardware live video stream"
    }

    fun getAvailableCamerasForTest(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Доступные камеры для теста:"
        AppLanguage.UA -> "Доступні камери для тесту:"
        AppLanguage.ES -> "Cámaras disponibles para prueba:"
        AppLanguage.PT, AppLanguage.PT_BR -> "Câmeras disponíveis para teste:"
        AppLanguage.FR -> "Caméras disponibles pour le test:"
        AppLanguage.IT -> "Fotocamere disponibili per il test:"
        AppLanguage.DE -> "Verfügbare Kameras zum Testen:"
        else -> "Available cameras for test:"
    }

    fun getPermissionRequired(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Требуется разрешение на использование камеры"
        AppLanguage.UA -> "Потрібен дозвіл на використання камери"
        AppLanguage.ES -> "Se requiere permiso de cámara"
        AppLanguage.PT, AppLanguage.PT_BR -> "Permissão de câmera necessária"
        AppLanguage.FR -> "Autorisation de la caméra requise"
        AppLanguage.IT -> "Autorizzazione fotocamera richiesta"
        AppLanguage.DE -> "Kameraberechtigung erforderlich"
        else -> "Camera permission required"
    }

    fun getGrantCameraPermission(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Разрешить доступ к камере"
        AppLanguage.UA -> "Надати доступ до камери"
        AppLanguage.ES -> "Conceder permiso de cámara"
        AppLanguage.PT, AppLanguage.PT_BR -> "Conceder permissão de câmera"
        AppLanguage.FR -> "Accorder l'autorisation de la caméra"
        AppLanguage.IT -> "Concedi autorizzazione fotocamera"
        AppLanguage.DE -> "Kamerazugriff erlauben"
        else -> "Grant Camera Permission"
    }

    fun getTapToStart(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Нажмите 'Запустить поток' для старта"
        AppLanguage.UA -> "Натисніть 'Запустити потік' для старту"
        AppLanguage.ES -> "Toque 'Iniciar transmisión' para comenzar"
        AppLanguage.PT, AppLanguage.PT_BR -> "Toque em 'Iniciar transmissão' para começar"
        AppLanguage.FR -> "Appuyez sur 'Démarrer le flux' pour lancer l'aperçu"
        AppLanguage.IT -> "Tocca 'Avvia streaming' per iniziare"
        AppLanguage.DE -> "Tippen Sie auf 'Stream starten'"
        else -> "Tap 'Start Stream' to begin live preview"
    }

    fun getExposureLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Выдержка:"
        AppLanguage.UA -> "Витримка:"
        AppLanguage.ES -> "Exposición:"
        AppLanguage.PT, AppLanguage.PT_BR -> "Exposição:"
        AppLanguage.FR -> "Exposition:"
        AppLanguage.IT -> "Esposizione:"
        AppLanguage.DE -> "Belichtung:"
        else -> "Exposure:"
    }

    fun getFocusLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU, AppLanguage.UA -> "Фокус:"
        AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "Foco:"
        AppLanguage.FR -> "Mise au point:"
        AppLanguage.IT -> "Messa a fuoco:"
        AppLanguage.DE -> "Fokus:"
        else -> "Focus:"
    }

    fun getPlatformLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU, AppLanguage.UA -> "Платформа:"
        AppLanguage.ES -> "Plataforma:"
        AppLanguage.PT, AppLanguage.PT_BR -> "Plataforma:"
        AppLanguage.FR -> "Plateforme:"
        AppLanguage.IT -> "Piattaforma:"
        AppLanguage.DE -> "Plattform:"
        else -> "Platform:"
    }

    fun getAllSpecsTitle(lang: AppLanguage): String = getAllSpecsLabel(lang)
    fun getCloseSpec(lang: AppLanguage): String = getCloseSpecLabel(lang)
    fun getExpertNoteTitle(lang: AppLanguage): String = getSpecialAssessmentLabel(lang)
}
