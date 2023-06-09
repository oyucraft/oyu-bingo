package net.kigawa.oyucraft.oyubingo

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kigawa.kutil.unitapi.component.InitializedFilterComponent
import net.kigawa.kutil.unitapi.component.UnitContainer
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
    }
    container.getUnit(InitializedFilterComponent::class.java).add(ConfigInitializedFilter::class.java)
    
    container.getUnit(ResourceRegistrar::class.java).register(javaClass)
    
    
    CommandAPI.onEnable()
  }
  
  override fun onDisable() {
    CommandAPI.onDisable()
  }
}