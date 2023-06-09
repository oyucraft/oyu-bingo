package net.kigawa.oyucraft.oyubingo.config

import com.google.common.base.Charsets
import io.github.oneservermc.oneserverapi.util.config.annotation.value.ConfigValue
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.*
import java.nio.charset.StandardCharsets

@Kunit
class ConfigManager: Closeable {
  
  val file: File
  val config: FileConfiguration
  
  private val plugin: Plugin
  private var fileName: String
  
  fun init(configHandler: ConfigHandler) {
    config = YamlConfiguration.loadConfiguration(file)
    val defConfigStream: InputStream? = plugin.getResource(fileName)
    
    if (defConfigStream != null) {
      config.setDefaults(
        YamlConfiguration.loadConfiguration(
          InputStreamReader(
            defConfigStream,
            StandardCharsets.UTF_8
          )
        )
      )
      defConfigStream.close()
    }
    
    
    if (!createConfig()) {
      for (field in configHandler.javaClass.declaredFields) {
        field.isAccessible = true
        val configValue = if (field.getAnnotation(ConfigValue::class.java) == null) continue else field.getAnnotation(
          ConfigValue::class.java
        )!!
        
        config.config.set(configValue.name, field.get(configHandler))
      }
      
      config.saveConfig()
    } else {
      for (field in configHandler.javaClass.declaredFields) {
        field.isAccessible = true
        val configValue = if (field.getAnnotation(ConfigValue::class.java) == null) continue else field.getAnnotation(
          ConfigValue::class.java
        )!!
        field.set(this, config.config.get(configValue.name, field.get(configHandler)))
      }
    }
  }
  
  
  /**
   * ファイルが存在するかの判別
   * @return 存在-> true, 不在-> false
   */
  fun isExists(): Boolean = file.exists()
  
  
  /**
   * ファイルが存在しない場合 新しく作成します
   * @return 存在-> true, 不在-> false
   */
  fun createConfig(): Boolean {
    if (file.exists()) return true
    else {
      file.parentFile.mkdirs()
      file.createNewFile()
    }
    return false
  }
  
  
  /**
   * ファイルが存在しない場合 resource内のファイルをコピーします
   * @exception IllegalArgumentException resource内にファイルがない場合
   */
  fun createDefaultConfig() {
    if (!file.exists()) plugin.saveResource(fileName, false)
  }
  
  
  /**
   * コンフィグをファイルに保存します
   */
  fun saveConfig() {
    val data: String = config.saveToString()
    
    val writer: Writer = OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8)
    
    writer.use {writer2->
      writer2.write(data)
    }
    
    writer.close()
  }
  
  override fun close() {
    TODO("Not yet implemented")
  }
}