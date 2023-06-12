package net.kigawa.oyucraft.oyubingo.config

import java.lang.reflect.Field

class ConfigField(
  private val config: Config,
  private val field: Field,
  private val configValue: ConfigValue,
) {
  
  val name: String
    get() {
      return if (configValue.name == "") this.field.name else configValue.name
    }
  val type: Class<Any?>
    get() {
      @Suppress("UNCHECKED_CAST")
      return this.field.type as Class<Any?>
    }
  
  
  fun get(): Any? {
    field.isAccessible = true
    return field.get(config)
  }
  
  fun set(value: Any?) {
    field.isAccessible = true
    return field.set(config, value)
  }
}