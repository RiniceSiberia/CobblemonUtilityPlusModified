package com.xxxt.cupm.utils

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import java.util.*

object CalendarCodec : Codec<Calendar> {
    override fun <T> encode(
        input: Calendar,
        ops: DynamicOps<T>,
        prefix: T
    ): DataResult<T> {
        return ops.mergeToPrimitive(prefix, ops.createLong(input.timeInMillis))
    }

    override fun <T> decode(
        ops: DynamicOps<T>,
        input: T
    ): DataResult<Pair<Calendar, T>> {
        // 从输入中读取 Long（时间戳），转换为 Calendar 对象
        return ops.getNumberValue(input).map { number ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = number.toLong()
            Pair(calendar,ops.empty())
        }
    }
}

object CalendarStreamCodec : StreamCodec<RegistryFriendlyByteBuf, Calendar> {

    override fun encode(buf: RegistryFriendlyByteBuf, value: Calendar) {
        buf.writeLong(value.timeInMillis)
    }

    override fun decode(buf: RegistryFriendlyByteBuf): Calendar {
        val millis = buf.readLong()
        return Calendar.getInstance().apply {
            timeInMillis = millis
        }
    }
}