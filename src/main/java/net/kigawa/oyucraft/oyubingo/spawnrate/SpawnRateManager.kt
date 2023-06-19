package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.OyuBingo
import net.kigawa.oyucraft.oyubingo.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import java.io.Closeable

@Kunit
class SpawnRateManager(
  private val spawnRateConfig: SpawnRateConfig,
): Closeable {
  
  fun set(worldName: String, entityType: EntityType, rate: Int) {
    spawnRateConfig
      .spawnRateWorlds
      .getOrCreateWorldSpawnRates(worldName)
      .set(entityType, rate)
    spawnRateConfig.save()
  }
  
  fun get(worldName: String, entityType: EntityType): Int {
    return spawnRateConfig
      .spawnRateWorlds
      .getWorldSpawnRates(worldName)
      ?.get(entityType)
      ?: 1
  }
  
  fun remove(worldName: String, entityTypeName: String) {
    spawnRateConfig
      .spawnRateWorlds
      .getWorldSpawnRates(worldName)
      ?.remove(entityTypeName)
  }
  
  override fun close() {
  }
  
  fun getEntityTypes(): List<EntityType> {
    return EntityType
      .values()
      .toList()
  }
  
  fun getEntityType(name: String): EntityType? {
    return EntityType
      .values()
      .firstOrNull {
        it.name.lowercase() == name.lowercase()
      }
  }
}