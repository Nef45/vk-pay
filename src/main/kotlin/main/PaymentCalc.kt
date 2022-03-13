package main

fun main() {
    val transferAmount = 10000_00
    val commissionPct = 0.0075
    val minCommissionSum = 35_00

    val commissionSum = if ((transferAmount * commissionPct) <= minCommissionSum) {
        minCommissionSum
    } else {
        transferAmount * commissionPct
    }

    println(
        """
        Transfer amount: $transferAmount
        ${String.format("Commission sum: %.0f", commissionSum)}
        """.trimIndent()
    )
}