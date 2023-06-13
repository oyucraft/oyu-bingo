package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.SpawnCategory
import java.io.Closeable

@Kunit
class SpawnRateManager(
  private val spawnRateConfig: SpawnRateConfig,
): Closeable {
  
  init {
    Bukkit.getWorlds().forEach {world->
      SpawnCategory.values().forEach {
        set(world, it, spawnRateConfig.spawnRateWorlds.getCategories(world).get(it))
      }
    }
  }
  
  fun set(world: World, spawnCategory: SpawnCategory, tickPerSpawn: Int) {
    world.setTicksPerSpawns(spawnCategory, tickPerSpawn)
    spawnRateConfig
      .spawnRateWorlds
      .getCategories(world)
      .set(spawnCategory, tickPerSpawn)
  }
  
  fun get(world: World, spawnCategory: SpawnCategory): Long? {
    return world.getTicksPerSpawns(spawnCategory)
  }
  
  fun reset(world: World, spawnCategory: SpawnCategory) {
    set(world, spawnCategory, 1)
  }
  
  override fun close() {
    Bukkit.getWorlds().forEach {world->
      SpawnCategory.values().forEach {
        world.setTicksPerSpawns(it, 1)
      }
    }
  }
}