@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.time.LocalDate

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
val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
val monthsMap = months.mapIndexed { index, s -> s to index }.toMap()

fun dateStrToDigit(str: String): String {
    val parts = str.split("\\s+".toRegex())
    try {
        if (parts.size != 3) return ""
        val day = parts[0].toInt()
        val month = monthsMap[parts[1]] ?: return ""
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
    val found = Regex("""^(\d{2})\.(\d{2})\.(\d+)$""").find(digital) ?: return ""
    try {
        val day = found.groupValues[1].toInt()
        val monthInt = found.groupValues[2].toInt()
        val month = months[monthInt - 1]
        val year = found.groupValues[3].toInt()
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
    val phoneNew = phone.filter { it != ' ' && it != '-' }
    if (!phoneNew.matches(Regex("""(\+\d+)?(\(\d+\))?(\d+)"""))) return ""
    return phoneNew.replace("""[()]""".toRegex(), "")
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
    val jumpsNew = "$jumps "
    if (!jumpsNew.matches(Regex("""((\d+|-|%) +)*"""))) return -1
    val parts = jumpsNew.split(Regex("""[\D]+""")).filter { it.isNotEmpty() }.map { it.toInt() }
    var answer = -1
    for (part in parts) {
        if (part >= answer) {
            answer = part
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
    val jumpsNew = "$jumps "
    val matchResult = Regex("""^(\d+ [+%\-]+ )*$""").find(jumpsNew) ?: return -1
    val parts = jumpsNew.split(Regex("""[\s%\-]+"""))
    var answer = -1
    var i = 0
    while (i < parts.size) {
        try {
            val partsInInt = parts[i++].toInt()
            if (parts[i] == "+") {
                answer = maxOf(partsInInt, answer)
                ++i
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
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (expression.matches(Regex("\\d+"))) return expression.toInt()
    if (!(expression.matches(Regex("""^(\d+\s+[+-]\s+)*\d+$""")))) throw IllegalArgumentException()
    val digitsAndSymbols = expression.split(" ")
    val (digits, symbols) = digitsAndSymbols.withIndex().partition { (i, _) -> i % 2 == 0 }
    var answer = digits.first().value.toInt()
    for ((i, symbol) in symbols.withIndex()) {
        when (symbol.value) {
            "+" -> answer += digits[i + 1].value.toInt()
            "-" -> answer -= digits[i + 1].value.toInt()
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
    val zippedWithNext = parts.zipWithNext()
    var answer = 0
    for ((w1, w2) in zippedWithNext) {
        if (w1 == w2) return answer
        answer += w1.length + 1
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
    val splited = description.split("; ")
    var max = 0.0
    try {
        for (goodAndPrice in splited) {
            val split = goodAndPrice.split(" ")
            if (split.size < 2) return ""
            val next = split[1].toDouble()
            if (next < 0)
                return ""
            else
                if (next >= max) {
                    max = next
                    answer = split[0]
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
    if (!commands.matches("""[ \[\]+\-<>]*""".toRegex())) throw IllegalArgumentException("illegal characters in commands")

    var curPos = cells / 2
    val answer = Array(cells) { 0 }
    var processedCommands = 0


    var dequeBrackets = 0
    commands.forEach {
        if (it == '[') dequeBrackets++
        else if (it == ']') {
            dequeBrackets--
            if (dequeBrackets < 0)
                throw IllegalArgumentException("not all square brackets [ ] have pair") // лишняя ]

        }
    }
    if (dequeBrackets > 0)
        throw IllegalArgumentException("not all square brackets [ ] have pair") // лишняя открывающая [


    var count = 0
    while (processedCommands < limit && count < commands.length) {
        val currentCommand = commands[count]
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
                        count++
                        when (commands[count]) {
                            '[' -> passCond++
                            ']' -> passCond--
                        }
                    }
                }
            }
            ']' -> {
                if (answer[curPos] != 0) {
                    var passCond = 1
                    while (passCond > 0) {
                        count--
                        when (commands[count]) {
                            ']' -> passCond++
                            '[' -> passCond--
                        }
                    }
                }
            }
            else -> throw IllegalArgumentException()
        }
        count++
        processedCommands++
        if (curPos < 0 || curPos >= answer.size) throw IllegalStateException() // Проверка на выход за границы
    }
    return answer.toList()
}