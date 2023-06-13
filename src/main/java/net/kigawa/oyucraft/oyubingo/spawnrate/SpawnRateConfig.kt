package net.kigawa.oyucraft.oyubingo.spawnrate

import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.config.Config
import net.kigawa.oyucraft.oyubingo.config.ConfigValue

@Kunit
class SpawnRateConfig: Config() {
  
  @ConfigValue
  val spawnRateWorlds = SpawnRateWorlds()
}