import com.xxxt.cupm.CUPMItems
import com.xxxt.cupm.CUPMMod
import com.xxxt.cupm.CUPMTags
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.client.renderer.item.ItemPropertyFunction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent

@EventBusSubscriber(modid = CUPMMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientModInit {
    @SubscribeEvent
    @JvmStatic
    fun onClientSetup(event: RegisterClientReloadListenersEvent) {
        ItemProperties.registerGeneric(
            CUPMTags.CAP_STATE_TAG.id,
            object : ItemPropertyFunction {
                override fun call(stack: ItemStack, p1: ClientLevel?, p2: LivingEntity?, p3: Int): Float {
                    return stack.components.getOrDefault(
                        CUPMTags.CAP_STATE_TAG.get(),
                        0
                    ).toFloat() * 0.1f
                }
            }
        )
    }
}