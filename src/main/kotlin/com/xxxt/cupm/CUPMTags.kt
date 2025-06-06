package com.xxxt.cupm

import com.mojang.serialization.Codec
import com.xxxt.cupm.utils.CalendarCodec
import com.xxxt.cupm.utils.CalendarStreamCodec
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.util.ExtraCodecs
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.*
@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CUPMTags {

    val TAGS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CUPMMod.MODID)

    val CAP_STATE_TAG : DeferredHolder<DataComponentType<*>, DataComponentType<Int>>
    = TAGS.registerComponentType("cap_state_tag") {
        DataComponentType.builder<Int>()
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
    }

    //我感到无尽的屈辱和悲愤，为什么没有native的date类的序列化和反序列化支持
    val EXPIRE_TIME_STAMP_TAG : DeferredHolder<DataComponentType<*>, DataComponentType<Calendar>>
    = TAGS.registerComponentType("expire_time_stamp_tag") {
        DataComponentType.builder<Calendar>()
            .persistent(CalendarCodec)
            .networkSynchronized(CalendarStreamCodec)
    }

    val BINDING_PLAYER_NAME_TAG : DeferredHolder<DataComponentType<*>, DataComponentType<String>>
    = TAGS.registerComponentType("binding_player_name_tag"){
        DataComponentType.builder<String>()
            .persistent(ExtraCodecs.PLAYER_NAME)
            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
    }

    fun register(eventBus: IEventBus) {
        TAGS.register(eventBus)
    }
}