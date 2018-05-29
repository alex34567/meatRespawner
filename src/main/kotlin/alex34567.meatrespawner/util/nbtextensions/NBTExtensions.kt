package alex34567.meatrespawner.util.nbtextensions

import alex34567.meatrespawner.util.RespawnPos
import com.mojang.authlib.GameProfile
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.math.BlockPos

val BlockPos.NBT: NBTTagCompound
    get() = NBTUtil.createPosTag(this)

val NBTTagCompound.isValidBlockPos
    get() = hasKey("X", 99) && hasKey("Y", 99) && hasKey("Z", 99)

val NBTTagCompound.blockPos: BlockPos
    get() = NBTUtil.getPosFromTag(this)

val GameProfile.NBT: NBTTagCompound
    get() = NBTUtil.writeGameProfile(NBTTagCompound(), this)

val NBTTagCompound.gameProfile
    get() = NBTUtil.readGameProfileFromNBT(this)

val NBTTagCompound.isValidGameProfile
    get() = gameProfile == null

val NBTTagCompound.isValidRespawnPos
    get() = isValidBlockPos && hasKey("Dim", 99)

val NBTTagCompound.respawnPos
    get() = RespawnPos(this)
