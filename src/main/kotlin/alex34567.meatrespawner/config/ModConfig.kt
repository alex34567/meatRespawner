package alex34567.meatrespawner.config

import alex34567.meatrespawner.MeatRespawnerMod

object ModConfig {
    var betweenDim: Boolean = false
    var deathCauseBlackList: List<String> = mutableListOf()

    fun syncConfig() {
        betweenDim =
                MeatRespawnerMod.config.getBoolean("betweenDim", "general", false, "Allow revive across dimensions")
        deathCauseBlackList =
                MeatRespawnerMod.config.getStringList("deathCauseBlackList", "general", arrayOf("outOfWorld"),
                        "Do not respawn player for these causes of death").toList()

        if (MeatRespawnerMod.config.hasChanged()) {
            MeatRespawnerMod.config.save()
        }
    }
}