package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import java.util.logging.Logger

@Kunit
class SpawnRateListener(
  private val spawnRateManager: SpawnRateManager,
  private val logger: Logger,
): Listener {
  
  private val allowTypes = listOf(
    SpawnReason.BEEHIVE,
    SpawnReason.CURED,
    SpawnReason.JOCKEY,
    SpawnReason.LIGHTNING,
    SpawnReason.MOUNT,
    SpawnReason.NATURAL,
    SpawnReason.RAID,
    SpawnReason.REINFORCEMENTS,
    SpawnReason.SPAWNER,
  )
  
  
  @EventHandler
  fun onSpawn(spawnEvent: CreatureSpawnEvent) {
    if (!allowTypes.contains(spawnEvent.spawnReason)) return
    
    val location = spawnEvent.location
    val type = spawnEvent.entityType
    val world = location.world
    if (world == null) {
      logger.warning("world is null")
      return
    }
    
    val additionalSpawnCount = spawnRateManager.get(world.name, type)
    
    for (i in 2..additionalSpawnCount) {
      val clazz = type.entityClass
      if (clazz == null) {
        logger.warning("entity class is null")
        return
      }
      world.spawn(location, clazz)
    }
  }
}