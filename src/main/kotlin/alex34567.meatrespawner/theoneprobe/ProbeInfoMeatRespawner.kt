package alex34567.meatrespawner.theoneprobe

import alex34567.meatrespawner.MeatRespawnerMod
import alex34567.meatrespawner.blocks.ModBlocks
import alex34567.meatrespawner.tileentities.TileEntityMeatRespawner
import mcjty.theoneprobe.api.*
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class ProbeInfoMeatRespawner : IProbeInfoProvider {
    override fun getID(): String {
        return MeatRespawnerMod.MODID + ":meatrespawnerprobe"
    }

    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, playerIn: EntityPlayer, world: World,
                              blockState: IBlockState, data: IProbeHitData) {
        if (blockState != ModBlocks.MEAT_RESPAWNER.defaultState) {
            return
        }
        val meatRespawner = world.getTileEntity(data.pos) as? TileEntityMeatRespawner ?: return
        val playerProfile = meatRespawner.player
        var canRespawn = false
        if (world.getBlockState(data.pos.up()) == net.minecraft.init.Blocks.AIR.defaultState) {
            canRespawn = true
        }
        if (mode == ProbeMode.NORMAL) {
            if (canRespawn && playerIn.gameProfile == playerProfile) {
                probeInfo.text(TextStyleClass.OK.toString() + "Can respawn this player")
            } else {
                probeInfo.text(TextStyleClass.ERROR.toString() + "Cannot respawn this player")
            }
        } else {
            when {
                playerProfile == null -> probeInfo.text(TextStyleClass.ERROR.toString() + "Unbinded")
                canRespawn -> probeInfo.text(TextStyleClass.OK.toString() + "Can respawn " + playerProfile.name)
                else -> probeInfo.text(TextStyleClass.ERROR.toString() + "Cannot respawn " + playerProfile.name)
            }
        }
    }
}
