package net.kigawa.oyucraft.oyubingo.spawnrate

import org.bukkit.entity.SpawnCategory

class SpawnRateCategories(
  val world: String = "world",
) {
  
  private val categories = mutableMapOf<SpawnCategory, Int>()
  val entries: MutableSet<MutableMap.MutableEntry<SpawnCategory, Int>>
    get() {
      return categories.entries
    }
  
  init {
    SpawnCategory.values().forEach {
      categories[it] = 1
    }
  }
  
  fun set(category: SpawnCategory, rate: Int) {
    categories[category] = rate
  }
  
  fun get(category: SpawnCategory): Int {
    val result = categories[category]
    if (result != null) return result
    
    set(category, 1)
    return 1
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SpawnRateCategories) return false
    
    if (world != other.world) return false
    return categories == other.categories
  }
  
  override fun hashCode(): Int {
    var result = world.hashCode()
    result = 31 * result + categories.hashCode()
    return result
  }
}