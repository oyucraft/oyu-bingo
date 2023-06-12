package net.kigawa.oyucraft.oyubingo

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar
import net.kigawa.oyucraft.oyubingo.config.ConfigInitializedFilter
import org.bukkit.plugin.java.JavaPlugin

class OyuBingo: JavaPlugin() {
  
  private val container = UnitContainer.create()
  
  override fun onLoad() {
    CommandAPI.onLoad(CommandAPIBukkitConfig(this))
  }
  
  override fun onEnable() {
    container.getUnit(InstanceRegistrar::class.java).apply {
      register(this@OyuBingo)
      register(logger)
    }
    container.getUnit(UnitLoggerComponent::class.java).add(OyuBingoUnitLogger::class.java)
    container.getUnit(InitializedFilterComponent::class.java).add(ConfigInitializedFilter::class.java)
    
    container.getUnit(ResourceRegistrar::class.java).register(javaClass)
    
    CommandAPI.onEnable()
  }
  
  override fun onDisable() {
    container.close()
    CommandAPI.onDisable()
  }
}