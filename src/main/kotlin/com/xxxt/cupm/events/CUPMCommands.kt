package com.xxxt.cupm.events

import com.mojang.brigadier.CommandDispatcher
import com.xxxt.cupm.commander.BindingCommand
import com.xxxt.cupm.commander.ExpireCommand
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

object CUPMCommands {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>, registry: CommandBuildContext, selection: Commands.CommandSelection) {
        BindingCommand.register(dispatcher)
        ExpireCommand.register(dispatcher)
    }
}