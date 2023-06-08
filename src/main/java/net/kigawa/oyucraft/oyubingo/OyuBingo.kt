package net.kigawa.oyucraft.oyubingo

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.java.JavaPlugin

class OyuBingo: JavaPlugin() {
  
  
  override fun onLoad() {
    CommandAPI.onLoad(CommandAPIBukkitConfig(this))
  }
  
  override fun onEnable() {
    CommandAPI.onEnable()
  }
  
  override fun onDisable() {
    CommandAPI.onDisable()
  }
}