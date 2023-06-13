package net.kigawa.oyucraft.oyubingo.spawnrate

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.executors.CommandExecutor
import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.oyucraft.oyubingo.command.AbstractCommand
import net.kigawa.oyucraft.oyubingo.command.SubCommand
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.SpawnCategory

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
      StringArgument("category")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {SpawnCategory.values().map(SpawnCategory::name)})
    )
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("get spawn rate")
      
      val world = args.get("world")?.let {Bukkit.getWorld(it.toString())}
      if (world == null) {
        sender.sendMessage("worldが見つかりません")
        return@CommandExecutor
      }
      val category = args.get("category")
        ?.let {arg-> SpawnCategory.values().firstOrNull {it.name.lowercase() == arg.toString().lowercase()}}
      if (category == null) {
        sender.sendMessage("カテゴリが見つかりません")
        return@CommandExecutor
      }
      
      sender.sendMessage(spawnRateManager.get(world, category).toString())
    })
  
  
  @SubCommand
  fun set(): CommandAPICommand = CommandAPICommand("set")
    .withArguments(
      StringArgument("world")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {Bukkit.getWorlds().map(World::name)})
    )
    .withArguments(
      StringArgument("category")
        .replaceSuggestions(ArgumentSuggestions.stringCollection {SpawnCategory.values().map(SpawnCategory::name)})
    )
    .executes(CommandExecutor {sender: CommandSender, args: CommandArguments->
      sender.sendMessage("set spawn rate ${args.get("")}")
      
      useSpawnRateManager(sender, args.get("world"), args.get("world")) {world, category->
        val
        
        spawnRateManager.set(world,category)
      }
    })
  
  private fun useSpawnRateManager(
    sender: CommandSender,
    worldName: Any?,
    categoryName: Any?,
    function: (World, SpawnCategory)->Unit,
  ) {
    val world = worldName?.let {Bukkit.getWorld(it.toString())}
    if (world == null) {
      sender.sendMessage("worldが見つかりません")
      return
    }
    val category = args.get("category")
      ?.let {arg-> SpawnCategory.values().firstOrNull {it.name.lowercase() == arg.toString().lowercase()}}
    if (category == null) {
      sender.sendMessage("カテゴリが見つかりません")
      return
    }
    function(world, category)
  }
}