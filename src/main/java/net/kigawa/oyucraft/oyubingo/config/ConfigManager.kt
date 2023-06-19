package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.oyucraft.oyubingo.OyuBingo
import org.bukkit.Bukkit
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ConfigManager(
  private val configUtil: ConfigUtil,
  private val oyuBingo: OyuBingo,
) {
  
  private val yaml = Yaml(CustomClassLoaderConstructor(oyuBingo.pluginClassLoader))
  fun saveAsync(config: Config) {
    Bukkit.getScheduler().runTaskAsynchronously(oyuBingo, Runnable {
      save(config)
    })
  }
  
  fun save(config: Config) {
    createFile(config)
    
    val data = mutableMapOf<String, Any?>()
    configUtil.configFields(config).forEach {
      data[it.name] = it.get()
    }
    
    OutputStreamWriter(configUtil.file(config).outputStream()).use {
      yaml.dump(data, it)
    }
  }
  
  fun load(config: Config) {
    createFile(config)
    
    val data = InputStreamReader(configUtil.file(config).inputStream()).use {
      yaml.loadAs(it, Map::class.java)
    } ?: return
    
    configUtil.configFields(config).forEach {
      it.set(data[it.name])
    }
  }
  
  fun createFile(config: Config) {
    configUtil.dir(config).mkdirs()
    configUtil.file(config).createNewFile()
  }
}