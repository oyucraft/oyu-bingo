package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.EntityType
import java.io.Closeable

@Kunit
class SpawnRateManager(
  private val spawnRateConfig: SpawnRateConfig,
): Closeable {
  
  init {
    Bukkit.getWorlds().forEach {world->
      spawnCategories().forEach {
        set(world, it, spawnRateConfig.spawnRateWorlds.getSpawnCount(world).get(it))
      }
    }
  }
  
  fun set(world: World, entityType: EntityType, tickPerSpawn: Int) {
    spawnRateConfig
      .spawnRateWorlds
      .getSpawnCount(world)
      .set(entityType, tickPerSpawn)
  }
  
  fun get(world: World, entityType: EntityType): Int {
    return spawnRateConfig
      .spawnRateWorlds
      .getSpawnCount(world)
      .get(entityType)
  }
  
  fun reset(world: World, entityType: EntityType) {
    set(world, entityType, 0)
  }
  
  override fun close() {
  }
  
  fun spawnCategories(): List<EntityType> {
    return EntityType
      .values()
      .toList()
  }
}