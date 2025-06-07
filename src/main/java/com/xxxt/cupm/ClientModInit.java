package com.xxxt.cupm;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModInit {

    @SubscribeEvent
    public static void onClientSetup(RegisterClientReloadListenersEvent event) {
        List<Item> shiftCaps = Arrays.asList(
                CUPMItems.INSTANCE.getSILVER_CAP().get(),
                CUPMItems.INSTANCE.getCOPPER_CAP().get(),
                CUPMItems.INSTANCE.getIRON_CAP().get(),
                CUPMItems.INSTANCE.getOBSIDIAN_CAP().get()
        );

        for (Item cap : shiftCaps) {
            ItemProperties.register(cap, CUPMTags.INSTANCE.getCAP_STATE_TAG().getId(), new ClampedItemPropertyFunction() {
                @Override
                public float unclampedCall(@NotNull ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
                    return stack.getComponents().getOrDefault(CUPMTags.INSTANCE.getCAP_STATE_TAG().get(), 0) * 0.1f;
                }
            });
        }
    }
}
