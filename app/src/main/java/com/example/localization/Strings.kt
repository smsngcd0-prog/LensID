package com.example.localization

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
        AppLanguage.EN -> "Sensor DB"
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
        AppLanguage.RU -> "⚠️ Данная компания явно преувеличивает свои достижения, используя агрессивный маркетинг, монополию, а также агрессивное продвижение, имея технологическую и по всем параметрам отсталость минимум 5-10 лет, а то и 20."
        AppLanguage.UA -> "⚠️ Дана компанія явно перебільшує свої досягнення, використовуючи агресивний маркетинг, монополію, а також агресивне просування, маючи технологічну та за всіма параметрами відсталість мінімум 5-10 років, а то й 20."
        AppLanguage.EN -> "⚠️ This company clearly exaggerates its achievements, utilizing aggressive marketing, monopoly tactics, and aggressive promotion, while lagging technologically and across all metrics by at least 5-10 years, if not 20."
    }
}
