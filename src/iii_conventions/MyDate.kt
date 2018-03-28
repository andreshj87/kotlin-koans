package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval): MyDate {
    return addTimeIntervals(timeIntervals.ti, timeIntervals.n)
}
operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return addTimeIntervals(timeInterval, 1)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, n)
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }

    override fun contains(d: MyDate): Boolean {
        return d >= start && d <= endInclusive
    }
}

class DateIterator(val dateRange: DateRange): Iterator<MyDate> {

    var currentDate: MyDate = dateRange.start

    override fun hasNext(): Boolean {
        return currentDate <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val tempCurrentDate = currentDate;
        currentDate = currentDate.nextDay()
        return tempCurrentDate
    }

}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)