package com.example.localization

import com.example.model.CameraFacing
import com.example.model.CameraRole

enum class AppLanguage(val code: String, val title: String, val flag: String) {
    RU("ru", "Русский", "🇷🇺"),
    UA("ua", "Українська", "🇺🇦"),
    EN("en", "English", "🇬🇧")
}

object AppStrings {

    fun getTabCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Камеры"
        AppLanguage.UA -> "Камери"
        AppLanguage.EN -> "Cameras"
    }

    fun getTabCompanies(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Компании"
        AppLanguage.UA -> "Компанії"
        AppLanguage.EN -> "Companies"
    }

    fun getTabTester(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Тестер"
        AppLanguage.UA -> "Тестер"
        AppLanguage.EN -> "Tester"
    }

    fun getTabSensors(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "База"
        AppLanguage.UA -> "База"
        AppLanguage.EN -> "Sensors"
    }

    fun getTabReport(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Отчёт"
        AppLanguage.UA -> "Звіт"
        AppLanguage.EN -> "Report"
    }

    fun getHeaderTitle(tab: Int, lang: AppLanguage) = when (tab) {
        0 -> when (lang) {
            AppLanguage.RU -> "Физические камеры телефона"
            AppLanguage.UA -> "Фізичні камери телефону"
            AppLanguage.EN -> "Physical Phone Cameras"
        }
        1 -> when (lang) {
            AppLanguage.RU -> "Компании и производители"
            AppLanguage.UA -> "Компанії та виробники"
            AppLanguage.EN -> "Suppliers & Manufacturers"
        }
        2 -> when (lang) {
            AppLanguage.RU -> "Тестирование видеопотока"
            AppLanguage.UA -> "Тестування відеопотоку"
            AppLanguage.EN -> "Live Camera Stream Test"
        }
        3 -> when (lang) {
            AppLanguage.RU -> "База мобильных сенсоров"
            AppLanguage.UA -> "База мобільних сенсорів"
            AppLanguage.EN -> "Mobile Sensor Encyclopedia"
        }
        4 -> when (lang) {
            AppLanguage.RU -> "Технический аудит камер"
            AppLanguage.UA -> "Технічний аудит камер"
            AppLanguage.EN -> "Technical Camera Audit"
        }
        else -> "CamSpec Pro"
    }

    fun getCameraRoleTitle(role: CameraRole, lang: AppLanguage): String = when (role) {
        CameraRole.MAIN_WIDE -> when (lang) {
            AppLanguage.RU -> "Основная (Широкоугольная)"
            AppLanguage.UA -> "Основна (Ширококутна)"
            AppLanguage.EN -> "Main (Wide Angle)"
        }
        CameraRole.ULTRA_WIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная"
            AppLanguage.UA -> "Надширококутна"
            AppLanguage.EN -> "Ultra-Wide"
        }
        CameraRole.TELEPHOTO -> when (lang) {
            AppLanguage.RU -> "Телеобъектив / Зум"
            AppLanguage.UA -> "Телеоб'єктив / Зум"
            AppLanguage.EN -> "Telephoto / Optical Zoom"
        }
        CameraRole.PERISCOPE -> when (lang) {
            AppLanguage.RU -> "Перископический телеобъектив"
            AppLanguage.UA -> "Перископічний телеоб'єктив"
            AppLanguage.EN -> "Periscope Telephoto"
        }
        CameraRole.MACRO -> when (lang) {
            AppLanguage.RU -> "Макро-камера"
            AppLanguage.UA -> "Макро-камера"
            AppLanguage.EN -> "Macro Camera"
        }
        CameraRole.DEPTH_TOF -> when (lang) {
            AppLanguage.RU -> "Сенсор глубины / ToF"
            AppLanguage.UA -> "Сенсор глибини / ToF"
            AppLanguage.EN -> "Depth / ToF Sensor"
        }
        CameraRole.MONOCHROME -> when (lang) {
            AppLanguage.RU -> "Монохромный сенсор"
            AppLanguage.UA -> "Монохромний сенсор"
            AppLanguage.EN -> "Monochrome Sensor"
        }
        CameraRole.FRONT_SELFIE -> when (lang) {
            AppLanguage.RU -> "Фронтальная (Селфи)"
            AppLanguage.UA -> "Фронтальна (Селфі)"
            AppLanguage.EN -> "Front (Selfie)"
        }
        CameraRole.FRONT_ULTRAWIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная селфи"
            AppLanguage.UA -> "Надширококутна селфі"
            AppLanguage.EN -> "Ultra-Wide Selfie"
        }
        CameraRole.UNKNOWN -> when (lang) {
            AppLanguage.RU -> "Дополнительная камера"
            AppLanguage.UA -> "Додаткова камера"
            AppLanguage.EN -> "Auxiliary Camera"
        }
    }

    fun getFacingTitle(facing: CameraFacing, lang: AppLanguage): String = when (facing) {
        CameraFacing.BACK -> when (lang) {
            AppLanguage.RU -> "Задняя (Основной блок)"
            AppLanguage.UA -> "Задня (Основний блок)"
            AppLanguage.EN -> "Rear (Main module)"
        }
        CameraFacing.FRONT -> when (lang) {
            AppLanguage.RU -> "Фронтальная (Экран)"
            AppLanguage.UA -> "Фронтальна (Екран)"
            AppLanguage.EN -> "Front (Screen)"
        }
        CameraFacing.EXTERNAL -> when (lang) {
            AppLanguage.RU -> "Внешняя (USB/UVC)"
            AppLanguage.UA -> "Зовнішня (USB/UVC)"
            AppLanguage.EN -> "External (USB/UVC)"
        }
        CameraFacing.UNKNOWN -> when (lang) {
            AppLanguage.RU -> "Неизвестно"
            AppLanguage.UA -> "Невідомо"
            AppLanguage.EN -> "Unknown"
        }
    }

    fun getFilterTitle(filterName: String, lang: AppLanguage) = when (filterName) {
        "ALL" -> when (lang) {
            AppLanguage.RU -> "Все камеры"
            AppLanguage.UA -> "Всі камери"
            AppLanguage.EN -> "All Cameras"
        }
        "BACK" -> when (lang) {
            AppLanguage.RU -> "Основные (Задние)"
            AppLanguage.UA -> "Основні (Задні)"
            AppLanguage.EN -> "Rear Cameras"
        }
        "FRONT" -> when (lang) {
            AppLanguage.RU -> "Фронтальные"
            AppLanguage.UA -> "Фронтальні"
            AppLanguage.EN -> "Front Cameras"
        }
        "PHYSICAL" -> when (lang) {
            AppLanguage.RU -> "Физические сенсоры"
            AppLanguage.UA -> "Фізичні сенсори"
            AppLanguage.EN -> "Physical Sensors"
        }
        else -> filterName
    }

    fun getScanningText(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Сканирование аппаратных сенсоров..."
        AppLanguage.UA -> "Сканування апаратних сенсорів..."
        AppLanguage.EN -> "Scanning hardware sensors..."
    }

    fun getTotalModules(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Всего модулей"
        AppLanguage.UA -> "Всього модулів"
        AppLanguage.EN -> "Total Modules"
    }

    fun getBackCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Задних камер"
        AppLanguage.UA -> "Задніх камер"
        AppLanguage.EN -> "Rear Cameras"
    }

    fun getFrontCameras(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Селфи камер"
        AppLanguage.UA -> "Селфі камер"
        AppLanguage.EN -> "Selfie Cameras"
    }

    fun getSuppliersForYourPhone(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Компании для вашего телефона"
        AppLanguage.UA -> "Компанії для вашого телефону"
        AppLanguage.EN -> "Suppliers for your device"
    }

    fun getSearchCompanyPlaceholder(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поиск компании или технологии..."
        AppLanguage.UA -> "Пошук компанії чи технології..."
        AppLanguage.EN -> "Search company or technology..."
    }

    fun getSearchSensorPlaceholder(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поиск сенсора (IMX890, HP2, OV50H)..."
        AppLanguage.UA -> "Пошук сенсора (IMX890, HP2, OV50H)..."
        AppLanguage.EN -> "Search sensor (IMX890, HP2, OV50H)..."
    }

    fun getCopyButton(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Копировать"
        AppLanguage.UA -> "Копіювати"
        AppLanguage.EN -> "Copy"
    }

    fun getShareButton(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Поделиться"
        AppLanguage.UA -> "Поділитися"
        AppLanguage.EN -> "Share"
    }

    fun getStartStream(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Запустить поток"
        AppLanguage.UA -> "Запустити потік"
        AppLanguage.EN -> "Start Stream"
    }

    fun getStopStream(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Остановить поток"
        AppLanguage.UA -> "Зупинити потік"
        AppLanguage.EN -> "Stop Stream"
    }

    fun getSonySpecialCriticism(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "данная компания явно преувеличивает свои достижения, используя аггресивний маркетинг, монополию, а также аггресивное продвижение, имея тезнологическую и по всем параметрам отсталость минимум 5-10 лет а то и 20."
        AppLanguage.UA -> "дана компанія явно перебільшує свої досягнення, використовуючи агресивний маркетинг, монополію, а також агресивне просування, маючи технологічну та за всіма параметрами відсталість мінімум 5-10 років а то й 20."
        AppLanguage.EN -> "this company clearly exaggerates its achievements, utilizing aggressive marketing, monopoly tactics, and aggressive promotion, while lagging technologically and across all metrics by at least 5-10 years, if not 20."
    }

    fun getResolutionLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Разрешение"
        AppLanguage.UA -> "Роздільність"
        AppLanguage.EN -> "Resolution"
    }

    fun getFormatLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Формат матрицы"
        AppLanguage.UA -> "Формат матриці"
        AppLanguage.EN -> "Sensor Format"
    }

    fun getPixelLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Пиксель"
        AppLanguage.UA -> "Піксель"
        AppLanguage.EN -> "Pixel Pitch"
    }

    fun getSensorMakerLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Производитель матрицы"
        AppLanguage.UA -> "Виробник матриці"
        AppLanguage.EN -> "Sensor Foundry"
    }

    fun getProbableModelLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Модель матрицы"
        AppLanguage.UA -> "Модель матриці"
        AppLanguage.EN -> "Sensor Model"
    }

    fun getCloseSpec(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Закрыть спецификацию"
        AppLanguage.UA -> "Закрити специфікацію"
        AppLanguage.EN -> "Close Specification"
    }

    fun getAllSpecsTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Все характеристики сенсора и оптики"
        AppLanguage.UA -> "Всі характеристики сенсора та оптики"
        AppLanguage.EN -> "All Sensor & Optical Specifications"
    }

    fun getExpertNoteTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Особая экспертная оценка:"
        AppLanguage.UA -> "Особлива експертна оцінка:"
        AppLanguage.EN -> "Special Expert Assessment:"
    }

    fun getDetectionSourceLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.RU -> "Источник детекции сенсора"
        AppLanguage.UA -> "Джерело детекції сенсора"
        AppLanguage.EN -> "Sensor Detection Source"
    }
}
