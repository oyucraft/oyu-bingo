package net.kigawa.oyucraft.oyubingo.spawnrate

import org.bukkit.Bukkit
import org.bukkit.World

class SpawnRateWorlds {
  
  val categoriesSet = mutableSetOf<SpawnCount>()
  val categoriesList: List<SpawnCount>
    get() {
      return ArrayList(categoriesSet)
    }
  
  init {
    Bukkit.getWorlds().forEach {
      categoriesSet.add(SpawnCount(it.name))
    }
  }
  
  fun getSpawnCount(world: World): SpawnCount {
    categoriesSet.forEach {
      if (it.world == world.name) return it
    }
    val categories = SpawnCount(world.name)
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