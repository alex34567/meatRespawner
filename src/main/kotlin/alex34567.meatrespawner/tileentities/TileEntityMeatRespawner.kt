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
import net.minecraft.util.DamageSource
import net.minecraft.world.WorldServer

class TileEntityMeatRespawner : TileEntity() {
    private var _player: GameProfile? = null
    var player: GameProfile?
        get() = _player
        set(value) {
            if (_player != value) {
                _player = value
                markDirty()
            }
        }

    override fun readFromNBT(tag: NBTTagCompound) {
        super.readFromNBT(tag)
        _player = tag.getCompoundTag("player").gameProfile
    }

    override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(tag)
        val playerTag = player?.NBT ?: return tag
        tag.setTag("player", playerTag)
        return tag
    }

    companion object {

        fun canRespawnPlayer(player: EntityPlayerMP, deathType: DamageSource): Boolean {
            val respawnerCapability = player.meatRespawner ?: return false
            var world = player.world
            val pos = respawnerCapability.pos ?: return false
            if (pos.dimId != player.dimension) {
                world = world.minecraftServer!!.getWorld(pos.dimId)
            }
            val meatRespawner = world.getTileEntity(pos.blockPos)
            if (world.getBlockState(pos.blockPos) != ModBlocks.MEAT_RESPAWNER.defaultState ||
                    meatRespawner !is TileEntityMeatRespawner) {
                respawnerCapability.pos = null
                return false
            }
            val playerProfile = meatRespawner.player
            if (playerProfile == null || playerProfile.id != player.uniqueID) {
                respawnerCapability.pos = null
                return false
            }
            if ((!ModConfig.betweenDim && pos.dimId != player.dimension) ||
                    world.getBlockState(pos.blockPos.up()) != Blocks.AIR.defaultState ||
                    ModConfig.deathCauseBlackList.contains(deathType.damageType)) {
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
