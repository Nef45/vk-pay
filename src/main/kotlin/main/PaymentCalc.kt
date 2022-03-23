package main

import kotlin.math.roundToInt

const val COMMISSION_PCT_MAESTRO = 0.006
const val COMMISSION_PCT_MASTERCARD = 0.006
const val COMMISSION_PCT_MIR = 0.0075
const val COMMISSION_PCT_VISA = 0.0075

const val COMMISSION_VKPAY = 0.0

const val MIN_COMMISSION_SUM_MAESTRO = 20_00
const val MIN_COMMISSION_SUM_MASTERCARD = 20_00
const val MIN_COMMISSION_SUM_MIR = 35_00
const val MIN_COMMISSION_SUM_VISA = 35_00

const val MAX_COMMISSION_FREE_SUM_MAESTRO = 75_000_00
const val MAX_COMMISSION_FREE_SUM_MASTERCARD = 75_000_00

const val CARDS_LIMIT_SUM_DAY = 150_000_00
const val CARDS_LIMIT_SUM_MONTH = 600_000_00
const val VKPAY_LIMIT_SUM_DAY = 15_000_00
const val VKPAY_LIMIT_SUM_MONTH = 40_000_00

fun main() {
    val transferSum = 10_000_00

    println(payCommission(cardType = "Maestro", transferSum = transferSum))
    println(payCommission("Maestro", 70_000_00, transferSum))
    println(payCommission("Mastercard", 595_000_00, transferSum))

}

fun isBeyondLimits(cardType: String, previousPaymentsSum: Int, transferSum: Int): Boolean {
    return when (cardType) {
        "Mastercard", "Maestro", "Mir", "Visa" -> when {
            (previousPaymentsSum + transferSum) in 0..CARDS_LIMIT_SUM_MONTH
                    &&
                    transferSum in 0..CARDS_LIMIT_SUM_DAY -> false
            else -> true
        }
        "VK Pay" -> when {
            (previousPaymentsSum + transferSum) in 0..VKPAY_LIMIT_SUM_MONTH
                    &&
                    transferSum in 0..VKPAY_LIMIT_SUM_DAY -> false
            else -> true
        }
        else -> true
    }
}

fun calculateCommission(cardType: String, previousPaymentsSum: Int, transferSum: Int): Int {
    val commissionSum =
        if (!isBeyondLimits(cardType, previousPaymentsSum, transferSum)) {
            when (cardType) {
                "Mastercard" -> when (previousPaymentsSum + transferSum) {
                    in 0..MAX_COMMISSION_FREE_SUM_MASTERCARD -> 0
                    else -> transferSum * COMMISSION_PCT_MASTERCARD + MIN_COMMISSION_SUM_MASTERCARD
                }
                "Maestro" -> when (previousPaymentsSum + transferSum) {
                    in 0..MAX_COMMISSION_FREE_SUM_MAESTRO -> 0
                    else -> transferSum * COMMISSION_PCT_MAESTRO + MIN_COMMISSION_SUM_MAESTRO
                }
                "Mir" -> when ((transferSum * COMMISSION_PCT_MIR).roundToInt()) {
                    in 0..MIN_COMMISSION_SUM_MIR -> MIN_COMMISSION_SUM_MIR
                    else -> transferSum * COMMISSION_PCT_MIR
                }
                "Visa" -> when ((transferSum * COMMISSION_PCT_VISA).roundToInt()) {
                    in 0..MIN_COMMISSION_SUM_VISA -> MIN_COMMISSION_SUM_VISA
                    else -> transferSum * COMMISSION_PCT_VISA
                }
                "VK Pay" -> COMMISSION_VKPAY
                else -> throw RuntimeException()
            }
        } else {
            -1
        }
    return commissionSum.toDouble().roundToInt()
}

fun payCommission(cardType: String = "VK Pay", previousPaymentsSum: Int = 0, transferSum: Int): String {
    val commissionSum = calculateCommission(cardType, previousPaymentsSum, transferSum)
    val string = when (commissionSum) {
        -1 -> "You have reached the transfer limit or are using unknown payment system"
        else -> "Commission sum: ${commissionSum / 100} rub. ${commissionSum % 100} kop."
    }
    return string
}