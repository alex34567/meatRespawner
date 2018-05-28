package alex34567.meatrespawner.proxy

import alex34567.meatrespawner.blocks.ModBlocks
import alex34567.meatrespawner.capability.CapabilityProvider
import alex34567.meatrespawner.capability.meatRespawner
import alex34567.meatrespawner.items.registerItemBlock
import alex34567.meatrespawner.tileentities.TileEntityMeatRespawner
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber
open class CommonProxy {
    companion object {
        @JvmStatic
        @SubscribeEvent
        fun registerBlocks(event: RegistryEvent.Register<Block>) {
            event.registry.register(ModBlocks.MEAT_RESPAWNER)
        }

        @JvmStatic
        @SubscribeEvent
        fun registerItems(event: RegistryEvent.Register<Item>) {
            registerItemBlock(event.registry, ModBlocks.MEAT_RESPAWNER)
        }

        @JvmStatic
        @SubscribeEvent
        fun attachPlayerMPCapabilities(event: AttachCapabilitiesEvent<Entity>) {
            if (event.`object` !is EntityPlayerMP) {
                return
            }

            event.addCapability(ResourceLocation("meatrespawner:player_capabilities"), CapabilityProvider())
        }

        @JvmStatic
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun playerDies(event: LivingDeathEvent) {
            val player = event.entity as? EntityPlayerMP ?: return
            val respawnerCapability = player.meatRespawner ?: return
            if (!TileEntityMeatRespawner.canRespawnPlayer(player)) {
                return
            }
            event.isCanceled = true
            respawnerCapability.deathCause = event.source
            player.health = 1f
        }

        @JvmStatic
        @SubscribeEvent
        fun serverTick(event: TickEvent.ServerTickEvent) {
            if (event.phase != TickEvent.Phase.START) {
                return
            }
            val server = FMLCommonHandler.instance().minecraftServerInstance
            for (player in server.playerList.players) {
                val respawnerCapability = player.meatRespawner ?: continue
                val source = respawnerCapability.deathCause ?: continue
                val respawnPos = respawnerCapability.pos
                val canRespawn = TileEntityMeatRespawner.canRespawnPlayer(player)
                if (!canRespawn || respawnPos == null) {
                    player.attackEntityFrom(source, Float.MAX_VALUE)
                    respawnerCapability.deathCause = null
                    continue
                }
                val blockPos = respawnPos.blockPos
                player.extinguish()
                var anotherDimension = false
                val world = server.getWorld(respawnPos.dimId)
                if (player.dimension != respawnPos.dimId) {
                    anotherDimension = true
                }
                world.addWeatherEffect(EntityLightningBolt(world, blockPos.x.toDouble(), blockPos.y.toDouble(),
                        blockPos.z.toDouble(), true))
                world.setBlockToAir(blockPos)
                player.dismountRidingEntity()
                player.motionX = 0.0
                player.motionY = 0.0
                player.motionZ = 0.0
                player.fallDistance = 0F
                player.velocityChanged = true
                if (anotherDimension) {
                    player.changeDimension(respawnPos.dimId) { _, entity, yaw ->
                        entity.moveToBlockPosAndAngles(blockPos, yaw, entity.rotationPitch)
                    }
                } else {
                    player.setPositionAndUpdate(blockPos.x + .5, blockPos.y.toDouble(), blockPos.z + .5)
                }
                if (player.foodStats.foodLevel < 6) {
                    player.foodStats.foodLevel = 6
                }
                player.clearActivePotions()
                respawnerCapability.deathCause = null
                respawnerCapability.pos = null
            }
        }
    }
}
