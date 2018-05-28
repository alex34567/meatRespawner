package alex34567.meatrespawner.capability

import alex34567.meatrespawner.util.delegates.ForgeInject
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

val EntityPlayer.meatRespawner
    get() = getCapability(Capabilities.MEAT_RESPAWNER, null)

object Capabilities {
    @CapabilityInject(value = CapabilityMeatRespawner::class)
    private var MEAT_RESPAWNER_: Capability<CapabilityMeatRespawner>? = null
    val MEAT_RESPAWNER: Capability<CapabilityMeatRespawner> by ForgeInject(::MEAT_RESPAWNER_)

    fun preInit() {
        CapabilityManager.INSTANCE.register(CapabilityMeatRespawner::class.java,
                CapabilityMeatRespawner.Storage(), CapabilityMeatRespawner.Companion::newInstance)
    }
}
