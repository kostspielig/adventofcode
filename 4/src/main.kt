import java.io.File
import java.math.BigInteger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val re = Regex("""\[(.+)\] (.+)""")
    val parsedLines = lines.mapNotNull {
        val matchResult = re.matchEntire(it)
        matchResult?.groupValues?.let {
            LocalDateTime.parse(it[1], DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")) to it[2]
        }
    }.sortedBy { it.first }


    val guards = mutableMapOf<String, MutableList<IntRange>>()
    var guardIdOnDuty = ""
    var asleepMinute = 0
    parsedLines.forEach { (time, message) ->
        val firstWord = message.takeWhile { it != ' ' }
        when (firstWord) {
            "Guard" -> {
                guardIdOnDuty = message.drop("Guard #".length).takeWhile { it != ' ' }
            }
            "falls" -> {
                asleepMinute = time.minute
            }
            "wakes" -> {
                val asleepTimes = guards.getOrPut(guardIdOnDuty, { mutableListOf<IntRange>() })
                asleepTimes.add(asleepMinute.until(time.minute))
            }
        }
    }
    println(task1(guards))
    println(task2(guards))
}

fun task1(guards: Map<String, List<IntRange>>): Int {
    val sleepyGuardId = guards.mapValues { it.value.sumBy { it.count() } }
            .maxBy { it.value }
            ?.key ?: throw Exception("invalid")

    val sleepyTimes = guards[sleepyGuardId]?: throw Exception("invalid")
    val maxMinute = (0..59).map { minute -> minute to sleepyTimes.count { it.contains(minute) }}.maxBy { it.second }?.first

    return sleepyGuardId.toInt()*maxMinute!!
}

fun task2(guards: Map<String, List<IntRange>>): Int {
    val guardsSleepMax = mutableMapOf<String, Pair<Int, Int>>()
    guards.forEach { s, list ->
        run {
            val pair = (0..59).map { minute -> minute to list.count { it.contains(minute) } }.maxBy { it.second }
            guardsSleepMax.put(s, pair!!)
        }
    }
    val mostSleepyGuard = guardsSleepMax.maxBy { it.value.second }

    return mostSleepyGuard!!.key.toInt()*mostSleepyGuard.value.first
}
