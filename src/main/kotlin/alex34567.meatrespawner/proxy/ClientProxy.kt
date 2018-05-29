package alex34567.meatrespawner.proxy

import alex34567.meatrespawner.MeatRespawnerMod
import alex34567.meatrespawner.blocks.ModBlocks
import alex34567.meatrespawner.config.ModConfig
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(Side.CLIENT)
class ClientProxy : CommonProxy() {
    companion object {
        @JvmStatic
        @SubscribeEvent
        fun registerItemModels(event: ModelRegistryEvent) {
            val itemBlock = Item.getItemFromBlock(ModBlocks.MEAT_RESPAWNER)
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
                    ModelResourceLocation(itemBlock.registryName!!, "normal"))
        }

        @JvmStatic
        @SubscribeEvent
        fun configChanged(event: ConfigChangedEvent.OnConfigChangedEvent) {
            if (event.modID != MeatRespawnerMod.MODID) {
                return
            }
            ModConfig.syncConfig()
        }
    }
}
