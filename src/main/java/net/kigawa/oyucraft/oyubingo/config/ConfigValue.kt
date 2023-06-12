package net.kigawa.oyucraft.oyubingo.config

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ConfigValue(val name: String = "")
