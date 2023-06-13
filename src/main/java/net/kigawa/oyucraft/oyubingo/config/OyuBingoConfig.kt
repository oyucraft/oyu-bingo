package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.bukkit.entity.SpawnCategory

@Kunit
class OyuBingoConfig: Config() {
  
  @ConfigValue
  val tickPerSpawn: Map<String, Map<SpawnCategory, Int>> = mutableMapOf()
}