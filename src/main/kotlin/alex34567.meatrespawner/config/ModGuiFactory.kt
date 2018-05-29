package alex34567.meatrespawner.config

import alex34567.meatrespawner.MeatRespawnerMod
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.fml.client.DefaultGuiFactory
import net.minecraftforge.fml.client.config.GuiConfig

class ModGuiFactory : DefaultGuiFactory(MeatRespawnerMod.MODID, "Meat Respawner") {
    override fun createConfigGui(parentScreen: GuiScreen?): GuiScreen {
        return GuiConfig(parentScreen,
                MeatRespawnerMod.config.getCategory("general").orderedValues.map { ConfigElement(it) },
                MeatRespawnerMod.MODID,
                false, false, "Meat Respawner")
    }

}