package main

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.math.roundToInt

class PaymentCalcKtTest {

    // =======================
    //isBeyondLimits for cards
    // =======================
    @Test
    fun isBeyondLimits_returnFalse_when_transferSumIsInLimits_for_cards_TT() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH - transferSum
        val expectedResult = false

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_cards_TF() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY + 100_00
        val previousPaymentsSum = CARDS_LIMIT_SUM_DAY
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_cards_FT() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_cards_FF() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY + 100_00
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_cards_TF_negative() {
        val cardType = "Mastercard"
        val transferSum = -CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_cards_FT_negative() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = -CARDS_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //isBeyondLimits for VKPay
    // =======================
    @Test
    fun isBeyondLimits_returnFalse_when_transferSumIsInLimits_for_vkPay_TT() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = VKPAY_LIMIT_SUM_MONTH - transferSum
        val expectedResult = false

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_vkPay_TF() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY + 100_00
        val previousPaymentsSum = VKPAY_LIMIT_SUM_DAY
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_vkPay_FT() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = VKPAY_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_vkPay_FF() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY + 100_00
        val previousPaymentsSum = VKPAY_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_vkPay_TF_negative() {
        val cardType = "VK Pay"
        val transferSum = -VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = VKPAY_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun isBeyondLimits_returnTrue_when_transferSumIsTooBig_for_vkPay_FT_negative() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = -VKPAY_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //isBeyondLimits for unknown card type
    // =======================
    @Test
    fun isBeyondLimits_returnTrue_when_cardType_Unknown() {
        val cardType = "XXX"
        val transferSum = CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH
        val expectedResult = true

        val actualResult = isBeyondLimits(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }


    // =======================
    //calculateCommission for Mastercard within limits
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_meetsSpecialOfferConditions_Mastercard() {
        val cardType = "Mastercard"
        val transferSum = MAX_COMMISSION_FREE_SUM_MASTERCARD - 1
        val previousPaymentsSum = MAX_COMMISSION_FREE_SUM_MASTERCARD - transferSum
        val expectedResult = 0

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_beyondSpecialOfferConditions_Mastercard() {
        val cardType = "Mastercard"
        val transferSum = MAX_COMMISSION_FREE_SUM_MASTERCARD - 1
        val previousPaymentsSum = MAX_COMMISSION_FREE_SUM_MASTERCARD - transferSum + 1
        val expectedResult = (transferSum * COMMISSION_PCT_MASTERCARD + MIN_COMMISSION_SUM_MASTERCARD).toDouble().roundToInt()

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //calculateCommission for Maestro within limits
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_meetsSpecialOfferConditions_Maestro() {
        val cardType = "Maestro"
        val transferSum = MAX_COMMISSION_FREE_SUM_MAESTRO - 1
        val previousPaymentsSum = MAX_COMMISSION_FREE_SUM_MAESTRO - transferSum
        val expectedResult = 0

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_beyondSpecialOfferConditions_Maestro() {
        val cardType = "Maestro"
        val transferSum = MAX_COMMISSION_FREE_SUM_MAESTRO - 1
        val previousPaymentsSum = MAX_COMMISSION_FREE_SUM_MAESTRO - transferSum + 1
        val expectedResult = (transferSum * COMMISSION_PCT_MAESTRO + MIN_COMMISSION_SUM_MAESTRO).toDouble().roundToInt()

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //calculateCommission for Mir within limits
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_minimumCommission_Mir() {
        val cardType = "Mir"
        val transferSum = ((MIN_COMMISSION_SUM_MIR - 1) / COMMISSION_PCT_MIR).roundToInt()
        val previousPaymentsSum = 0
        val expectedResult = MIN_COMMISSION_SUM_MIR

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_normalCommission_Mir() {
        val cardType = "Mir"
        val transferSum = ((MIN_COMMISSION_SUM_MIR + 100_00) / COMMISSION_PCT_MIR).roundToInt()
        val previousPaymentsSum = 0
        val expectedResult = (transferSum * COMMISSION_PCT_MIR).roundToInt()

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //calculateCommission for Visa within limits
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_minimumCommission_Visa() {
        val cardType = "Visa"
        val transferSum = ((MIN_COMMISSION_SUM_VISA - 1) / COMMISSION_PCT_VISA).roundToInt()
        val previousPaymentsSum = 0
        val expectedResult = MIN_COMMISSION_SUM_VISA

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_normalCommission_Visa() {
        val cardType = "Visa"
        val transferSum = ((MIN_COMMISSION_SUM_VISA + 100_00) / COMMISSION_PCT_VISA).roundToInt()
        val previousPaymentsSum = 0
        val expectedResult = (transferSum * COMMISSION_PCT_VISA).roundToInt()

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //calculateCommission for VK Pay within limits
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_cardType_vkPay() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY - 1
        val previousPaymentsSum = 0
        val expectedResult = COMMISSION_VKPAY.roundToInt()

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //calculateCommission for payment systems when transfer sum is beyond limits / with unknown card type
    // =======================
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_unknownCardType() {
        val cardType = "XXX"
        val transferSum = VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = 0
        val expectedResult = -1

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun calculateCommission_returnCorrectCommissionSum_when_beyondLimits() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY + 1
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH + transferSum
        val expectedResult = -1

        val actualResult = calculateCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }


    // =======================
    //payCommission within/beyond limits
    // =======================
    @Test
    fun payCommission_returnCorrectStringWithCommissionSum_withinLimits() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH - transferSum
        val commissionSum = calculateCommission(cardType, previousPaymentsSum, transferSum)
        val expectedResult = "Commission sum: ${commissionSum / 100} rub. ${commissionSum % 100} kop."

        val actualResult = payCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun payCommission_returnCorrectStringWithCommissionSum_beyondLimits() {
        val cardType = "Mastercard"
        val transferSum = CARDS_LIMIT_SUM_DAY + 1
        val previousPaymentsSum = CARDS_LIMIT_SUM_MONTH + transferSum
        val commissionSum = calculateCommission(cardType, previousPaymentsSum, transferSum)
        val expectedResult = "You have reached the transfer limit or are using unknown payment system"

        val actualResult = payCommission(cardType, previousPaymentsSum, transferSum)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun payCommission_returnCorrectStringWithCommissionSum_default() {
        val cardType = "VK Pay"
        val transferSum = VKPAY_LIMIT_SUM_DAY
        val previousPaymentsSum = 0
        val commissionSum = calculateCommission(cardType, previousPaymentsSum, transferSum)
        val expectedResult = "Commission sum: ${commissionSum / 100} rub. ${commissionSum % 100} kop."

        val actualResult = payCommission(transferSum = transferSum)

        assertEquals(expectedResult, actualResult)
    }

    // =======================
    //FAIL
    // =======================
    @Test
    fun failedTest() {
        assertEquals(true, false)
    }

}