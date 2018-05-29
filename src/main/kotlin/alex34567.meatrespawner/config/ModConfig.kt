package alex34567.meatrespawner.config

import alex34567.meatrespawner.MeatRespawnerMod

object ModConfig {
    var betweenDim: Boolean = false

    fun syncConfig() {
        betweenDim =
                MeatRespawnerMod.config.getBoolean("betweenDim", "general", false, "Allow revive across dimensions")

        if (MeatRespawnerMod.config.hasChanged()) {
            MeatRespawnerMod.config.save()
        }
    }
}