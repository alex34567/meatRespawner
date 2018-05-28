package alex34567.meatrespawner.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class CapabilityProvider : ICapabilitySerializable<NBTTagCompound> {
    private val meatRespawner = Capabilities.MEAT_RESPAWNER.defaultInstance!!

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === Capabilities.MEAT_RESPAWNER
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability === Capabilities.MEAT_RESPAWNER) {
            Capabilities.MEAT_RESPAWNER.cast(meatRespawner)
        } else null

    }

    override fun serializeNBT(): NBTTagCompound {
        val tag = NBTTagCompound()
        val meatRespawnerTag = meatRespawner.NBT
        if (meatRespawnerTag != null) {
            tag.setTag("meatRespawnerBound", meatRespawnerTag)
        }
        return tag
    }

    override fun deserializeNBT(tag: NBTTagCompound) {
        if (tag.hasKey("meatRespawnerBound")) {
            val meatRespawnerTag = tag.getTag("meatRespawnerBound")
            meatRespawner.NBT = meatRespawnerTag
        }
    }
}
