package com.xxxt.cupm.events

import com.xxxt.cupm.CUPMMod
import com.xxxt.cupm.commander.BindingCommand
import com.xxxt.cupm.commander.ExpireCommand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.RegisterCommandsEvent

@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD)
class ServerEvents {
    @SubscribeEvent
    fun onCommandRegister(event: RegisterCommandsEvent) {
        BindingCommand.register(event.dispatcher)
        ExpireCommand.register(event.dispatcher)
    }
}