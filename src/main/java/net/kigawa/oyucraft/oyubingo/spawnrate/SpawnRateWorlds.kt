package net.kigawa.oyucraft.oyubingo.spawnrate

import org.bukkit.Bukkit
import org.bukkit.World

class SpawnRateWorlds {
  
  private val categoriesSet = mutableSetOf<SpawnRateCategories>()
  val categoriesList: List<SpawnRateCategories>
    get() {
      return ArrayList(categoriesSet)
    }
  
  init {
    Bukkit.getWorlds().forEach {
      categoriesSet.add(SpawnRateCategories(it.name))
    }
  }
  
  fun getCategories(world: World): SpawnRateCategories {
    categoriesSet.forEach {
      if (it.world == world.name) return it
    }
    val categories = SpawnRateCategories(world.name)
    categoriesSet.add(categories)
    return categories
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SpawnRateWorlds) return false
    
    return categoriesSet == other.categoriesSet
  }
  
  override fun hashCode(): Int {
    return categoriesSet.hashCode()
  }
}