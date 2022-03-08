package ir.awlrhm.areminder.utils

fun getDayName(day: Int): String {
    return when (day) {
        1 -> "شنبه"
        2 -> "یکشنبه"
        3 -> "دوشنبه"
        4 -> "سه شنبه"
        5 -> "چهارشنبه"
        6 -> "پنجشنبه"
        7 -> "جمعه"
        else -> ""
    }
}


fun getMonthName(mothOfYear: Int): String {
    return when (mothOfYear) {
        1 -> "فروردین"
        2 -> "اردیبهشت"
        3 -> "خرداد"
        4 -> "تیر"
        5 -> "مرداد"
        6 -> "شهریور"
        7 -> "مهر"
        8 -> "آبان"
        9 -> "آذر"
        10 -> "دی"
        11 -> "بهمن"
        12 -> "اسفند"
        else -> ""
    }
}
