@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.time.LocalDate
import java.util.*

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    try {
        if (parts.size != 3) return ""
        val day = parts[0].toInt()
        val month = when (parts[1]) {
            "января" -> 0
            "февраля" -> 1
            "марта" -> 2
            "апреля" -> 3
            "мая" -> 4
            "июня" -> 5
            "июля" -> 6
            "августа" -> 7
            "сентября" -> 8
            "октября" -> 9
            "ноября" -> 10
            "декабря" -> 11
            else -> -1
        }
        val year = parts[2].toInt()
        LocalDate.of(year, month + 1, day)
        return String.format("%02d.%02d.%d", day, month + 1, year)
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    if (!digital.matches(Regex("""\d{2}\.\d{2}\.\d+"""))) return ""
    try {
        val parts = digital.split(".")
        val day = parts[0].toInt()
        val monthInt = parts[1].toInt()
        val month = when (monthInt) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> return ""
        }
        val year = parts[2].toInt()

        LocalDate.of(year, monthInt, day)
        return String.format("%d %s %d", day, month, year)
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val regex = Regex("""(\+\d+)?\s*(\((\d+)\))?\s*\d(([\s-]*)\d+)*""")
//    val matchRes = regex.find(phone) ?: return ""
    if (!phone.matches(regex)) return ""
    val filtered = phone.replace("""[\s-()]""".toRegex(), "")
    return filtered
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = if (jumps.matches(Regex("""[\d\s\-%]+"""))) jumps.split(Regex("""[\D]+"""))
    else return -1
    var answer = -1
    for (part in parts) {
        try {
            if (part.toInt() >= answer) {
                answer = part.toInt()
            }
        } catch (e: NumberFormatException) {
            continue
        }
    }
    return answer
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (jumps.matches(Regex("""[\d\s+%\-]+"""))) {
        val parts = jumps.split(Regex("""[\s%\-]+"""))
        val answer = mutableListOf<Int>()
        for (i in 0 until parts.size) {
            try {
                val partsInInt = parts[i].toInt()
                if (parts[i + 1] == "+") answer.add(partsInInt)
            } catch (e: NumberFormatException) {
                continue
            }
        }
        return answer.max() ?: -1
    } else return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (expression.matches(Regex("\\d+"))) return expression.toInt()
    if (!(expression.matches(Regex("^\\d+(\\s[+-]\\s)(\\d+\\s[+-]\\s)*\\d+$")))) throw IllegalArgumentException()
    val digitsAndSymbols = expression.split(" ")
    val digits = digitsAndSymbols.filterIndexed { i, _ -> i % 2 == 0 }
    val symbols = listOf("+").plus(digitsAndSymbols.filterIndexed { i, _ -> i % 2 != 0 })
    var answer = 0
    for ((i, digit) in digits.withIndex()) {
        when (symbols[i]) {
            "+" -> answer += digit.toInt()
            "-" -> answer -= digit.toInt()
            else -> throw IllegalArgumentException()
        }
    }
    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.toLowerCase().split(" ")
    var answer = 0
    for (i in 0 until parts.size - 1) {
        if (parts[i] == parts[i + 1]) return answer
        answer += parts[i].length + 1

    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var answer = ""
    val split = description.split("; ", " ")
    var max = 0.0
    try {
        for (i in 1 until split.size step 2) {
            val next = split[i].toDouble()
            if (next < 0)
                return ""
            else
                if (next >= max) {
                    max = next
                    answer = split[i - 1]
                }
        }
    } catch (e: NumberFormatException) {
        return ""
    }
    return answer
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (!roman.matches("""[MDCLXVI]+""".toRegex())) return -1
    val romanValues = mapOf('M' to 1000, 'D' to 500, 'C' to 100, 'L' to 50, 'X' to 10, 'V' to 5, 'I' to 1)

    var res = 0
    for (i in 0 until roman.length - 1) {
        val nextValue = romanValues[roman[i + 1]]!!
        val curValue = romanValues[roman[i]]!!
        if (curValue < nextValue) {
            res -= curValue
        } else {
            res += curValue
        }
    }
    res += romanValues[roman.last()]!!
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if (!commands.matches("""[ \[\]+\-<>]+""".toRegex())) throw IllegalArgumentException("illegal characters in commands")

    var curPos = cells / 2
    val answer = Array(cells) { 0 }
    var processedCommands = 0


    val dequeBrackets: Deque<Char> = LinkedList()
    try {
        commands.forEach {
            if (it == '[') dequeBrackets.push('[')
            else if (it == ']') dequeBrackets.pop()
        }
        if (dequeBrackets.isNotEmpty())
            throw IllegalArgumentException("not all square brackets [ ] have pair")
    } catch (e: Exception) {
        throw IllegalArgumentException(e)
    }


    var counter = 0
    while (processedCommands < limit && counter < commands.length) {
        val currentCommand = commands[counter]
        when (currentCommand) {
            ' ' -> {
            }
            '<' -> curPos--
            '>' -> curPos++
            '-' -> answer[curPos]--
            '+' -> answer[curPos]++
            '[' -> {
                if (answer[curPos] == 0) {
                    var passCond = 1
                    while (passCond > 0) {
                        counter++
                        if (commands[counter] == '[') passCond++
                        else if (commands[counter] == ']') passCond--
                    }
                }
            }
            ']' -> {
                if (answer[curPos] != 0) {
                    var passFactor = 1
                    while (passFactor > 0) {
                        counter--
                        if (commands[counter] == ']') passFactor++
                        else if (commands[counter] == '[') passFactor--
                    }
                }
            }
            else -> throw IllegalArgumentException()
        }
        counter++
        processedCommands++
        if (curPos < 0 || curPos >= answer.size) throw IllegalStateException()
    }
    return answer.toList()
}