package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.config.Config
import net.kigawa.oyucraft.oyubingo.config.annotation.ConfigName
import net.kigawa.oyucraft.oyubingo.config.annotation.ConfigValue

@Kunit
class SpawnRateConfig: Config() {
  
  @ConfigValue
  val spawnRateWorlds = SpawnRateWorlds()
  
  
  @ConfigValue
  val test = true
}