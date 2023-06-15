package net.kigawa.oyucraft.oyubingo.unit

import net.kigawa.kutil.unitapi.UnitIdentify

data class InstanceKunitStore<T: Any>(val identify: UnitIdentify<T>, val instance: T)