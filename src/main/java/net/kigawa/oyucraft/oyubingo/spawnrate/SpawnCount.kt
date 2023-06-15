package net.kigawa.oyucraft.oyubingo.spawnrate

import org.bukkit.entity.EntityType

class SpawnCount(
  val world: String = "world",
) {
  
  private val categories = mutableMapOf<EntityType, Int>()
  val entries: MutableSet<MutableMap.MutableEntry<EntityType, Int>>
    get() {
      return categories.entries
    }
  
  init {
    EntityType.values().forEach {
      categories[it] = 0
    }
  }
  
  fun set(entityType: EntityType, rate: Int) {
    categories[entityType] = rate
  }
  
  fun get(entityType: EntityType): Int {
    val result = categories[entityType]
    if (result != null) return result
    
    set(entityType, 1)
    return 1
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SpawnCount) return false
    
    if (world != other.world) return false
    return categories == other.categories
  }
  
  override fun hashCode(): Int {
    var result = world.hashCode()
    result = 31 * result + categories.hashCode()
    return result
  }
}