package alex34567.meatrespawner.tileentities

import net.minecraft.tileentity.TileEntity

object TileEntities {

    fun preInit() {
        TileEntity.register("meatrespawner:meat_respawner", TileEntityMeatRespawner::class.java)
    }
}
