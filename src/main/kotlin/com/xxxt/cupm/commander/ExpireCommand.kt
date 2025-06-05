package com.xxxt.cupm.commander

import com.cobblemon.mod.common.util.player
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.LongArgumentType
import com.xxxt.cupm.CUPMMod
import com.xxxt.cupm.CUPMTags.EXPIRE_TIME_STAMP_TAG
import com.xxxt.cupm.items.CUPMSelectingItemImpl
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.RegisterCommandsEvent
import java.util.Calendar


object ExpireCommand{
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>){
        dispatcher.register(
            Commands.literal("cupm")
                .then(Commands.literal("set_expire")
                    .then(Commands.argument("expire_time", IntegerArgumentType.integer(0, Int.MAX_VALUE))
                        .executes { context ->
                            val player = context.source.playerOrException
                            val stack = player.mainHandItem
                            if (stack.item is CUPMSelectingItemImpl){
                                stack.set(EXPIRE_TIME_STAMP_TAG, Calendar.getInstance().apply {
                                    this.add(Calendar.MINUTE,context.getArgument("expire_time", Int::class.java))
                                })

                                1
                            }else{
                                player.sendSystemMessage(Component.literal("please cupm this mod selecting item!"))
                                0
                            }
                        }
                    )

                )

        )
    }
}