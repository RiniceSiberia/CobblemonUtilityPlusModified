package com.xxxt.cupm.commander

import com.cobblemon.mod.common.util.player
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.LongArgumentType
import com.xxxt.cupm.CUPMMod
import com.xxxt.cupm.CUPMTags.BINDING_PLAYER_NAME_TAG
import com.xxxt.cupm.CUPMTags.EXPIRE_TIME_STAMP_TAG
import com.xxxt.cupm.items.CUPMSelectingItemImpl
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.RegisterCommandsEvent
import java.util.Calendar


object BindingCommand{
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>){
        dispatcher.register(
            Commands.literal("cupm")
                .then(Commands.literal("bind")
                    .executes { context ->
                        val player = context.source.playerOrException
                        val stack = player.mainHandItem
                        if (stack.item is CUPMSelectingItemImpl){
                            stack.set(BINDING_PLAYER_NAME_TAG, player.name.toString())
                            1
                        }else{
                            player.sendSystemMessage(Component.literal("please hold cupm mod selecting item!"))
                            0
                        }
                    }

                )

        )
    }
}