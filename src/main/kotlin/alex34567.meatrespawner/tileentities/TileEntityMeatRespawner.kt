package alex34567.meatrespawner.tileentities

import alex34567.meatrespawner.blocks.ModBlocks
import alex34567.meatrespawner.capability.meatRespawner
import alex34567.meatrespawner.config.ModConfig
import alex34567.meatrespawner.util.RespawnPos
import alex34567.meatrespawner.util.nbtextensions.NBT
import alex34567.meatrespawner.util.nbtextensions.gameProfile
import com.mojang.authlib.GameProfile
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.WorldServer
import kotlin.properties.Delegates

class TileEntityMeatRespawner : TileEntity() {
    var player: GameProfile? by Delegates.observable<GameProfile?>(null, { _, oldValue, newValue ->
        if (oldValue != newValue) {
            markDirty()
        }
    })

    override fun readFromNBT(tag: NBTTagCompound) {
        super.readFromNBT(tag)
        player = tag.getCompoundTag("player").gameProfile
    }

    override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(tag)
        val playerTag = player?.NBT ?: return tag
        tag.setTag("player", playerTag)
        return tag
    }

    companion object {

        fun canRespawnPlayer(player: EntityPlayerMP): Boolean {
            val respawnerCapability = player.meatRespawner ?: return false
            var world = player.world
            val pos = respawnerCapability.pos ?: return false
            if (pos.dimId != player.dimension) {
                world = world.minecraftServer!!.getWorld(pos.dimId)
            }
            if (world.getBlockState(pos.blockPos) != ModBlocks.MEAT_RESPAWNER.defaultState) return false
            val meatRespawner = world.getTileEntity(pos.blockPos) as? TileEntityMeatRespawner ?: return false
            val playerProfile = meatRespawner.player
            if (playerProfile == null) {
                respawnerCapability.pos = null
                return false
            }
            if (playerProfile.id != player.uniqueID) {
                respawnerCapability.pos = null
                return false
            }
            if (!ModConfig.betweenDim && pos.dimId != player.dimension) {
                respawnerCapability.pos = null
                meatRespawner.player = null
                return false
            }
            if (world.getBlockState(pos.blockPos.up()) != Blocks.AIR.defaultState) {
                respawnerCapability.pos = null
                meatRespawner.player = null
                return false
            }
            return true
        }

        fun invalidatePos(worldIn: WorldServer, pos: RespawnPos, player: GameProfile) {
            var world = worldIn
            if (world.provider.dimension != pos.dimId) {
                world = world.minecraftServer!!.getWorld(pos.dimId)
            }
            val meatRespawner = world.getTileEntity(pos.blockPos) as? TileEntityMeatRespawner ?: return
            if (player != meatRespawner.player) {
                return
            }
            meatRespawner.player = null
        }
    }
}
