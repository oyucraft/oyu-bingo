package net.kigawa.oyucraft.oyubingo.config

import net.kigawa.kutil.unitapi.component.InitStack
import net.kigawa.kutil.unitapi.extention.InitializedFilter
import net.kigawa.oyucraft.oyubingo.OyuBingo

class ConfigInitializedFilter(
  private val oyuBingo: OyuBingo,
): InitializedFilter {
  
  
  override fun <T: Any> filter(obj: T, stack: InitStack): T {
    if (obj !is Config) return obj
    
    obj.init(oyuBingo)
    obj.load()
    
    return obj
  }
}