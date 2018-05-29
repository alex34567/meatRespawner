package alex34567.meatrespawner.util

import alex34567.meatrespawner.util.nbtextensions.NBT
import alex34567.meatrespawner.util.nbtextensions.blockPos
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

data class RespawnPos(val blockPos: BlockPos, val dimId: Int) {
    constructor(tag: NBTTagCompound) : this(tag.blockPos, tag.getInteger("Dim"))

    val nbt: NBTTagCompound
        get() {
            val tag = blockPos.NBT
            tag.setInteger("Dim", dimId)
            return tag
        }
}
