package com.xxxt.cupm.utils

import kotlin.random.Random

fun <A> weightedShuffle(input: Map<A, Int>): List<A> {
    val remaining = input.toMutableMap()
    val result = mutableListOf<A>()

    while (remaining.isNotEmpty()) {
        val totalWeight = remaining.values.sum()
        var r = Random.nextInt(totalWeight)

        // 线性扫描并减权
        for ((key, weight) in remaining) {
            r -= weight
            if (r < 0) {
                result.add(key)
                remaining.remove(key)
                break
            }
        }
    }

    return result
}