@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val text = File(inputName).readText()
    for (substring in substrings) {
        var startIdx = 0
        var counter = 0
        while (true) {
            val foundIdx = text.indexOf(substring, startIdx, true)
            if (foundIdx < 0) break
            counter++
            startIdx = foundIdx + 1
        }
        result[substring] = counter
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val text = File(inputName).readText()
    val sb = StringBuilder()
    var doModify = false
    for (ch in text) {
        var ch2 = ch
        if (doModify) {
            ch2 = when (ch) {
                'Ы' -> 'И'
                'ы' -> 'и'
                'Я' -> 'А'
                'я' -> 'а'
                'Ю' -> 'У'
                'ю' -> 'у'
                else -> ch
            }
        }
        sb.append(ch2)

        doModify = (ch2 in "ЖжЧчШшЩщ")
    }
    File(outputName).writeText(sb.toString())
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLineLength = 0
    val lineList = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        val lineTrimmed = line.trim()
        maxLineLength = max(maxLineLength, lineTrimmed.length)
        lineList.add(lineTrimmed)
    }
    File(outputName).bufferedWriter().use {
        for (line in lineList) {
            val spaces = " ".repeat((maxLineLength - line.length) / 2)
            it.write(spaces)
            it.write(line)
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    var maxLineLength = 0
    val lineList = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        val lineTrimmed = line.trim().replace(Regex("""\s+"""), " ")
        maxLineLength = max(maxLineLength, lineTrimmed.length)
        lineList.add(lineTrimmed)
    }

    File(outputName).bufferedWriter().use {
        for (line in lineList) {
            if (line.isNotEmpty()) {
                val words = line.split(' ')
                val sb = StringBuilder(words[0])
                val gapCount = words.size - 1
                if (gapCount > 0) {
                    val spacesCountInLine = maxLineLength - line.length + gapCount
                    val spacesBtwWords = spacesCountInLine / gapCount
                    var spacesReminder = spacesCountInLine % gapCount

                    for (i in 1 until words.size) {
                        sb.append(" ".repeat(spacesBtwWords))
                        if (spacesReminder-- > 0) sb.append(' ')
                        sb.append(words[i])
                    }
                }
                it.write(sb.toString())
            }
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val dict = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        val splitted = line.split(Regex("""[^a-zA-Zа-яЁёА-Я]+"""))
        for (word in splitted) {
            if (word.isEmpty()) continue
            dict[word.toLowerCase()] = (dict[word.toLowerCase()] ?: 0) + 1
        }
    }

    return dict.entries.sortedByDescending { it.value }.take(20).associateBy({ it.key }, { it.value })
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val text = File(inputName).readText()
    val sb = StringBuilder()
    val dict = dictionary.mapKeys { it.key.toLowerCase() }.mapValues { it.value.toLowerCase() }
    for (c in text) {
        val lowerCh = c.toLowerCase()
        var r = (dict[lowerCh] ?: "$c").toLowerCase()
        if (c.isUpperCase() && r.isNotEmpty()) {
            r = r.capitalize()
        }
        sb.append(r)
    }

    File(outputName).writeText(sb.toString())
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val resultingMap = mutableMapOf<Int, MutableList<String>>()
    var maxLen = 0
    for (word in File(inputName).readLines()) {
        if (word.toLowerCase().toSet().size != word.length) continue
        resultingMap.getOrPut(word.length) { mutableListOf() }.add(word)
        maxLen = max(maxLen, word.length)
    }
    File(outputName).bufferedWriter().use {
        if (maxLen > 0) {
            it.write(resultingMap[maxLen]!!.joinToString(separator = ", "))
        }
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val markerOpenClose = mutableMapOf("*" to false, "**" to false, "~~" to false, "***" to false)
    val sb = StringBuilder()
    var paragraphs = false
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            paragraphs = true
            sb.append("</p><p>")
        }
        var i = 0
        while (i < line.length) {
            val ch = line[i]
            when {
                ch == '*' -> when {
                    (i < line.length - 2 && line[i + 1] == '*' && line[i + 2] != '*') ||
                            (i == line.length - 2 && line[i + 1] == '*')|| (markerOpenClose["**"]!!) -> {
                        sb.append(getTag(markerOpenClose, "**", "<b>", "</b>"))
                        ++i
                    }
                    (i < line.length - 1 && line[i + 1] != '*') || (i == line.length - 1) ||
                            (markerOpenClose["*"]!!) -> {
                        sb.append(getTag(markerOpenClose, "*", "<i>", "</i>"))
                    }
                    (i < line.length - 3 && line[i + 1] == '*' && line[i + 2] == '*' && line[i + 3] != '*') ||
                            (i == line.length - 3 && line[i + 1] == '*' && line[i + 2] == '*') -> {
                        sb.append(getTag(markerOpenClose, "***", "<b><i>", "</i></b>"))
                        i += 2
                    }
                }
                ch == '~' -> when {
                    (i < line.length - 2 && line[i + 1] == '~' && line[i + 2] != '~') ||
                            (i == line.length - 2 && line[i + 1] == '~') -> {
                        sb.append(getTag(markerOpenClose, "~~", "<s>", "</s>"))
                        ++i
                    }
                }
                else -> {
                    sb.append(ch)
                }
            }
            ++i
        }

    }
    File(outputName).bufferedWriter().use {
        it.write("<html><body>")
        if (paragraphs) it.write("<p>")
        it.write(sb.toString())
        if (paragraphs) it.write("</p>")
        it.write("</body></html>")
    }
}


fun getTag(markerOpenClose: MutableMap<String, Boolean>, srcMarker: String, openTag: String, closeTag: String): String {
    val isCloseTag = markerOpenClose[srcMarker]!!
    markerOpenClose[srcMarker] = !isCloseTag
    return if (isCloseTag) closeTag else openTag

}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val sb = StringBuilder()
    var listLevel = -1

    val listOpenedTags = mutableListOf<String>()
    val lines = File(inputName).readLines()
    for ((j, line) in lines.withIndex()) {
        var i = 0

        while (line[i] == ' ') {
            ++i
        }

        val curListLevel = i / 4

        if (line[i] == '*') {
            if (curListLevel > listLevel) {
                sb.append("<ul>")
                listOpenedTags.add("</li></ul>")
            } else if (curListLevel < listLevel) {
                sb.append(listOpenedTags.removeAt(listOpenedTags.size - 1))
            }
        } else {
            if (curListLevel > listLevel) {
                sb.append("<ol>")
                listOpenedTags.add("</li></ol>")
            } else if (curListLevel < listLevel) {
                sb.append(listOpenedTags.removeAt(listOpenedTags.size - 1))
            }
            i = line.indexOf('.', i)
        }

        if (listLevel >= curListLevel) sb.append("</li>")
        listLevel = curListLevel
        sb.append("<li>")
        sb.append(line.substring(i + 1))
        if (j < lines.size - 1) sb.append("\n")

    }

    for (v in listOpenedTags.reversed()) {
        sb.append(v)
    }
    File(outputName).bufferedWriter().use {
        it.write("<html><body>")
        it.write(sb.toString())
        it.write("</body></html>")
    }
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

