package alex34567.meatrespawner.blocks

import alex34567.meatrespawner.capability.meatRespawner
import alex34567.meatrespawner.tileentities.TileEntityMeatRespawner
import alex34567.meatrespawner.util.RespawnPos
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraft.world.WorldServer

class BlockMeatRespawner : Block(Material.GROUND), ITileEntityProvider {

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer,
                                  hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (worldIn.isRemote) {
            return true
        }
        val bound = playerIn.meatRespawner
        val tileEntity = worldIn.getTileEntity(pos)
        if (bound == null || tileEntity !is TileEntityMeatRespawner) {
            return true
        }
        val oldPos = bound.pos
        bound.pos = RespawnPos(pos, playerIn.dimension)
        tileEntity.player = playerIn.gameProfile
        if (oldPos != null && (oldPos.blockPos != pos || oldPos.dimId != playerIn.dimension)) {
            TileEntityMeatRespawner.invalidatePos(worldIn as WorldServer, oldPos, playerIn.gameProfile)
        }
        playerIn.sendStatusMessage(TextComponentTranslation("tile.meatRespawner.meatRespawner.activated",
                TextComponentString(playerIn.name)), true)
        return true
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntityMeatRespawner {
        return TileEntityMeatRespawner()
    }
}
