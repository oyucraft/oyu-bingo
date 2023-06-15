package net.kigawa.oyucraft.oyubingo.unit

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.extention.UnitFinder
import net.kigawa.kutil.unitapi.options.FindOptions
import net.kigawa.oyucraft.oyubingo.OyuBingo

class BukkitFinder(
  oyuBingo: OyuBingo
): UnitFinder {
  
  private val instances = listOf<InstanceKunitStore<*>>()
  init {
  
  }
//  private fun addInstance()
  private fun <T: Any> findInstanceKunitStore(identify: UnitIdentify<T>): List<InstanceKunitStore<T>> {
    @Suppress("UNCHECKED_CAST")
    return instances.filter {
      it.identify == identify
    } as List<InstanceKunitStore<T>>
  }
  
  override fun <T: Any> findUnits(identify: UnitIdentify<T>, findOptions: FindOptions): List<T> {
    return findInstanceKunitStore(identify).map {it.instance}
  }
}