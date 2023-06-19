package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.annotation.Inject


abstract class Config {
  
  @Inject
  private lateinit var configManager: ConfigManager
  fun save() {
    configManager.save(this)
  }
}