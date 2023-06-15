package net.kigawa.oyucraft.oyubingo

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar
import net.kigawa.oyucraft.oyubingo.config.Config
import net.kigawa.oyucraft.oyubingo.config.ConfigInitializedFilter
import net.kigawa.oyucraft.oyubingo.unit.*
import org.bukkit.Bukkit
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
    container.getUnit(UnitFinderComponent::class.java).add(BukkitFinder::class.java)
    container.getUnit(UnitLoggerComponent::class.java).add(OyuBingoUnitLogger::class.java)
    container.getUnit(InitializedFilterComponent::class.java).apply {
      add(ConfigInitializedFilter::class.java)
      add(ListenerFilter::class.java)
    }
    
    container.getUnit(ResourceRegistrar::class.java).register(javaClass)
    
    CommandAPI.onEnable()
    
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, Runnable {
      container.getUnitList(Config::class.java).forEach {
        it.save()
      }
    }, 5 * 60 * 1000, 5 * 60 * 1000)
  }
  
  override fun onDisable() {
    container.close()
    CommandAPI.onDisable()
  }
}