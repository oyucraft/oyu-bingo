package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.OyuBingo

@Kunit
class Config(
  oyuBingo: OyuBingo,
) {
  
  private val fileConfiguration = oyuBingo.config
  
  init {
  }
}