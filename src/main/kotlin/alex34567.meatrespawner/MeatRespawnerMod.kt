package alex34567.meatrespawner

import alex34567.meatrespawner.capability.Capabilities
import alex34567.meatrespawner.config.ModConfig
import alex34567.meatrespawner.proxy.CommonProxy
import alex34567.meatrespawner.tileentities.TileEntities
import alex34567.meatrespawner.util.delegates.ForgeInject
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLInterModComms
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger
import java.io.File
import kotlin.properties.Delegates

@Mod(modid = MeatRespawnerMod.MODID, name = MeatRespawnerMod.NAME, version = MeatRespawnerMod.VERSION,
        modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
        guiFactory = "alex34567.meatrespawner.config.ModGuiFactory")
class MeatRespawnerMod {
    companion object {
        const val MODID: String = "meatrespawner"
        const val NAME: String = "Meat Respawner"
        const val VERSION: String = "1.12.2-0.0.0.0"

        @Mod.Instance
        private var instance_: MeatRespawnerMod? = null
        val instance: MeatRespawnerMod by ForgeInject(::instance_)

        @SidedProxy(clientSide = "alex34567.meatrespawner.proxy.ClientProxy",
                serverSide = "alex34567.meatrespawner.proxy.CommonProxy")
        private var proxy_: CommonProxy? = null
        val proxy: CommonProxy by ForgeInject(::proxy_)

        var logger: Logger by Delegates.notNull()
        var config: Configuration by Delegates.notNull()
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        config = Configuration(File(event.modConfigurationDirectory, "meatRespawner.cfg"))
        logger = event.modLog
        ModConfig.syncConfig()
        TileEntities.preInit()
        Capabilities.preInit()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe",
                "alex34567.meatrespawner.theoneprobe.TheOneProbeSupport")
    }

}