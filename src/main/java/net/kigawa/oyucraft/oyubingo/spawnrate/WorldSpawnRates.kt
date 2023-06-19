package net.kigawa.oyucraft.oyubingo.spawnrate

import org.bukkit.entity.EntityType

class WorldSpawnRates(
  var world: String = "world",
) {
  
  var entityRates = mutableMapOf<String, Int>()
  
  fun set(entityType: EntityType, rate: Int) {
    entityRates[entityType.name] = rate
  }
  
  fun remove(entityTypeName: String) {
    entityRates.remove(entityTypeName)
  }
  
  fun get(entityType: EntityType): Int? {
    return entityRates[entityType.name]
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is WorldSpawnRates) return false
    
    if (world != other.world) return false
    return entityRates == other.entityRates
  }
  
  override fun hashCode(): Int {
    var result = world.hashCode()
    result = 31 * result + entityRates.hashCode()
    return result
  }
}