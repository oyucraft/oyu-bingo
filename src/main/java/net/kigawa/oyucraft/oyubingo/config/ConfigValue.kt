package io.github.oneservermc.oneserverapi.util.config.annotation.value

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ConfigValue(val name: String)
