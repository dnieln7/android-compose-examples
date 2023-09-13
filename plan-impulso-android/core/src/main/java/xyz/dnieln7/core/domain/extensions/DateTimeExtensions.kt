package xyz.dnieln7.core.domain.extensions

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun DateTime.stringifyToPortfolioFormat(): String {
    return portfolioFormatter.print(this)
}

private val portfolioFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
