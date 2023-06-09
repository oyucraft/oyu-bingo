package net.kigawa.oyucraft.oyubingo.config

import io.github.oneservermc.oneserverapi.util.getClasses

class OneConfigContainer(rootClass: Class<*>): ConfigContainer {
    private val configs = HashMap<String, ConfigHandler>()

    init {
        val classes = getClasses(rootClass)

        for (clazz in classes) {
            if (clazz.superclass != ConfigHandler::class.java) continue
            val instance = clazz.getField("INSTANCE").get(null) as ConfigHandler

            configs[instance.configName] = instance

            instance.init(instance)
        }
    }

    override fun save() {
        configs.values.forEach { if (it.savable) it.config.saveConfig() }
    }
}