package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.oyucraft.oyubingo.OyuBingo
import net.kigawa.oyucraft.oyubingo.caseformat.CaseFormat
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.Closeable
import java.io.File


abstract class ConfigHandler(
  private var fileName: String? = null,
  private var fileDir: File? = null,
): Closeable {
  
  lateinit var file: File
    private set
  lateinit var config: FileConfiguration
    private set
  
  fun init(oyuBingo: OyuBingo) {
    if (fileName == null) {
      fileName = CaseFormat
        .HIGHER_CAMEL_CASE
        .caseString(this.javaClass.simpleName)
        .toFormatStr(CaseFormat.KEBAB_CASE) + ".yml"
    }
    
    if (fileDir == null)
      fileDir = oyuBingo.dataFolder
    
    
    file = File(
      getFileDir(),
      getFileName()
    )
    
    config = YamlConfiguration.loadConfiguration(file)
  }
  
  fun getFileName(): String {
    return fileName!!
  }
  
  fun getFileDir(): File {
    return fileDir!!
  }
  
  fun load() {
    if (!file.exists()) {
      file.parentFile.mkdirs()
      file.createNewFile()
    }
    
    config.load(file)
    loadValues()
  }
  
  private fun loadValues() {
    KutilReflect.getAllExitFields(javaClass).forEach {
      val configValue = it.getAnnotation(ConfigValue::class.java) ?: return
      val name = if (configValue.name == "") configValue.name else it.name
      
      it.isAccessible = true
      
      it.set(this, config.get(name, it.get(this)))
    }
    save()
  }
  
  private fun save() {
    KutilReflect.getAllExitFields(javaClass).forEach {
      
      val configValue = it.getAnnotation(ConfigValue::class.java) ?: return
      val name = if (configValue.name == "") configValue.name else it.name
      
      it.isAccessible = true
      
      config.set(name, it.get(this))
    }
    
    config.save(file)
  }
  
  override fun close() {
    save()
  }
}