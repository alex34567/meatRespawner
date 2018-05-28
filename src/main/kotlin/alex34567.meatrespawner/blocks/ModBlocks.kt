package alex34567.meatrespawner.blocks

object ModBlocks {
    val MEAT_RESPAWNER = BlockMeatRespawner()

    init {
        MEAT_RESPAWNER.run {
            setRegistryName("meat_respawner")
            setHardness(.25f)
            unlocalizedName = "meatRespawner.meatRespawner"
        }
    }
}
