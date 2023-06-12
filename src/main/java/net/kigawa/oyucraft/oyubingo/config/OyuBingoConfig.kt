package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class OyuBingoConfig: Config() {
  
  @ConfigValue
  private val spawnRate: Float = 0f
}