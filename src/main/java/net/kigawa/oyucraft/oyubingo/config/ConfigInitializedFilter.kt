package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.component.InitStack
import net.kigawa.kutil.unitapi.extention.InitializedFilter

class ConfigInitializedFilter: InitializedFilter {
  
  override fun <T: Any> filter(obj: T, stack: InitStack): T {
    if (!ConfigHandler::class.java.isInstance(obj)) return obj
    
    configs[instance.configName] = instance
    
    return obj
  }
}