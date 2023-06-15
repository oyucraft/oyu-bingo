package net.kigawa.oyucraft.oyubingo.spawnrate

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.*
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.executors.CommandExecutor
import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.command.AbstractCommand
import net.kigawa.oyucraft.oyubingo.command.SubCommand
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
          spawnRateManager.spawnCategories().map {it.name}
        })
    )
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("get additional spawn")
      
      useSpawnRateManager(sender, args.get("world") as String, args.get("type") as String) {world, type->
        sender.sendMessage(spawnRateManager.get(world, type).toString())
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
          spawnRateManager.spawnCategories().map {it.name}
        })
    )
    .withArguments(IntegerArgument("additional spawn"))
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("set additional spawn ${args.get("additional spawn")}")
      
      useSpawnRateManager(sender, args.get("world") as String, args.get("type") as String) {world, type->
        val tickPerSpawn = args.get("additional spawn")
        if (tickPerSpawn !is Int) {
          sender.sendMessage("数字を指定してください")
          return@useSpawnRateManager
        }
        
        spawnRateManager.set(world, type, tickPerSpawn)
      }
    })
  
  private fun useSpawnRateManager(
    sender: CommandSender,
    worldName: String,
    categoryName: String,
    function: (World, EntityType)->Unit,
  ) {
    val world = Bukkit.getWorld(worldName)
    if (world == null) {
      sender.sendMessage("worldが見つかりません")
      return
    }
    
    val type = spawnRateManager.spawnCategories().firstOrNull {
      it.name.lowercase() == categoryName.lowercase()
    }
    if (type == null) {
      sender.sendMessage("カテゴリが見つかりません")
      return
    }
    function(world, type)
  }
}