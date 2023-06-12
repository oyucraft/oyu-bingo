package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.oyucraft.oyubingo.OyuBingo
import net.kigawa.oyucraft.oyubingo.caseformat.CaseFormat
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.Closeable
import java.io.File


abstract class Config(
  private var fileName: String? = null,
  private var fileDir: File? = null,
): Closeable {
  
  lateinit var file: File
    private set
  lateinit var fileConfig: FileConfiguration
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
    
    fileConfig = YamlConfiguration.loadConfiguration(file)
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
    
    fileConfig.load(file)
    loadValues()
  }
  
  fun getConfigFields(): List<ConfigField> {
    return KutilReflect.getAllExitFields(javaClass).mapNotNull {
      val configValue = it.getAnnotation(ConfigValue::class.java) ?: return@mapNotNull null
      return@mapNotNull ConfigField(this, it, configValue)
    }
  }
  
  private fun loadValues() {
    getConfigFields().forEach {
      it.set(fileConfig.getObject<Any?>(it.name, it.type, it.get()))
    }
    save()
  }
  
  private fun save() {
    getConfigFields().forEach {
      fileConfig.set(it.name, it.get())
    }
    
    fileConfig.save(file)
  }
  
  override fun close() {
    save()
  }
}