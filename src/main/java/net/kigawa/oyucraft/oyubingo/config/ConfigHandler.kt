package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.oyucraft.oyubingo.OyuBingo
import org.bukkit.configuration.file.FileConfiguration
import java.io.File


abstract class ConfigHandler(
  val configName: String,
  val savable: Boolean = true,
) {
  
  private var file: File? = null
  private var config: FileConfiguration? = null
  
  fun getFile(oyuBingo: OyuBingo): File {
    return file ?: File(oyuBingo.dataFolder, this.javaClass.simpleName + ".yml")
  }
}