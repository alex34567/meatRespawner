package alex34567.meatrespawner.capability

import alex34567.meatrespawner.util.RespawnPos
import alex34567.meatrespawner.util.nbtextensions.isValidRespawnPos
import alex34567.meatrespawner.util.nbtextensions.respawnPos
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class CapabilityMeatRespawner private constructor() {
    var pos: RespawnPos? = null
    var deathCause: DamageSource? = null
    var NBT: NBTBase?
        get() = Capabilities.MEAT_RESPAWNER.writeNBT(this, null)
        set(value) = Capabilities.MEAT_RESPAWNER.readNBT(this, null, value)

    class Storage : Capability.IStorage<CapabilityMeatRespawner> {
        override fun writeNBT(capability: Capability<CapabilityMeatRespawner>,
                              instance: CapabilityMeatRespawner, side: EnumFacing?): NBTBase? {
            return instance.pos?.NBT
        }

        override fun readNBT(capability: Capability<CapabilityMeatRespawner>, instance: CapabilityMeatRespawner,
                             side: EnumFacing?, tag: NBTBase) {
            if (tag !is NBTTagCompound) {
                return
            }
            //check if position was recorded
            if (!tag.isValidRespawnPos) {
                return
            }

            instance.pos = tag.respawnPos
        }
    }

    companion object {
        internal fun newInstance(): CapabilityMeatRespawner {
            return CapabilityMeatRespawner()
        }
    }
}
