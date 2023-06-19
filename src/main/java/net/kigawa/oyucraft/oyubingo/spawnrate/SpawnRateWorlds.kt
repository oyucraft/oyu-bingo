package net.kigawa.oyucraft.oyubingo.spawnrate

class SpawnRateWorlds {
  
  var worldSpawnRatesMap = mutableMapOf<String, WorldSpawnRates>()
  
  fun getWorldSpawnRates(worldName: String): WorldSpawnRates? {
    return worldSpawnRatesMap[worldName]
  }
  
  fun getOrCreateWorldSpawnRates(worldName: String): WorldSpawnRates {
    return getWorldSpawnRates(worldName)
      ?: WorldSpawnRates(worldName)
        .also {worldSpawnRatesMap[worldName] = it}
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SpawnRateWorlds) return false
    
    return worldSpawnRatesMap == other.worldSpawnRatesMap
  }
  
  override fun hashCode(): Int {
    return worldSpawnRatesMap.hashCode()
  }
}