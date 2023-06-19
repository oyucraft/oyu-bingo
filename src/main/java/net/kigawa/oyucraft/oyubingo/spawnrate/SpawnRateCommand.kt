package net.kigawa.oyucraft.oyubingo.spawnrate

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.*
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.executors.CommandExecutor
import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.OyuBingo
import net.kigawa.oyucraft.oyubingo.command.AbstractCommand
import net.kigawa.oyucraft.oyubingo.command.SubCommand
import net.kigawa.oyucraft.oyubingo.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType

@Kunit
class RateCommand(
  private val spawnRateManager: SpawnRateManager,
): AbstractCommand(
  CommandAPICommand("spawn-rate")
    .withPermission(CommandPermission.OP)
) {
  
  @SubCommand
  fun get(): CommandAPICommand = CommandAPICommand("get")
    .withArguments(
      StringArgument("world")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {Bukkit.getWorlds().map(World::name)})
    )
    .withArguments(
      StringArgument("type")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {
          spawnRateManager.getEntityTypes().map {it.name}
        })
    )
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("get rate")
      
      useSpawnRateManager(sender, args.get("world") as String, args.get("type") as String) {worldName, type->
        sender.sendMessage(spawnRateManager.get(worldName, type).toString())
      }
    })
  
  
  @SubCommand
  fun set(): CommandAPICommand = CommandAPICommand("set")
    .withArguments(
      StringArgument("world")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {Bukkit.getWorlds().map(World::name)})
    )
    .withArguments(
      StringArgument("type")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {
          spawnRateManager.getEntityTypes().map {it.name}
        })
    )
    .withArguments(IntegerArgument("rate"))
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("set rate ${args.get("rate")}")
      
      useSpawnRateManager(sender, args.get("world") as String, args.get("type") as String) {worldName, type->
        val tickPerSpawn = args.get("rate")
        if (tickPerSpawn !is Int) {
          sender.sendMessage("数字を指定してください")
          return@useSpawnRateManager
        }
        
        spawnRateManager.set(worldName, type, tickPerSpawn)
      }
    })
  
  private fun useSpawnRateManager(
    sender: CommandSender,
    worldName: String,
    categoryName: String,
    function: (String, EntityType)->Unit,
  ) {
    
    val type = spawnRateManager.getEntityTypes().firstOrNull {
      it.name.lowercase() == categoryName.lowercase()
    }
    if (type == null) {
      sender.sendMessage("カテゴリが見つかりません")
      return
    }
    function(worldName, type)
  }
}