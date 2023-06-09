# NAME

## About

*

## Requirement

* java

## Author

* kigawa
    * kigawa.8390@gmail.com

# Making

## Version

### Example: 9.1.2

* **9**: major, destructive
* **1**: miner, new function
* **2**: miner, bug fix

## ToDo

湧き率
発光
マイクラ内でチーム
GUI
チーム内インベントリ


    /**
     * Sets the world's ticks per {@link SpawnCategory} mob spawns value
     * <p>
     * This value determines how many ticks there are between attempts to
     * spawn {@link SpawnCategory} mobs.
     * <p>
     * <b>Example Usage:</b>
     * <ul>
     * <li>A value of 1 will mean the server will attempt to spawn {@link SpawnCategory} mobs in
     *     this world on every tick.
     * <li>A value of 400 will mean the server will attempt to spawn {@link SpawnCategory} mobs
     *     in this world every 400th tick.
     * <li>A value below 0 will be reset back to Minecraft's default.
     * </ul>
     * <p>
     * <b>Note:</b>
     * If set to 0, {@link SpawnCategory} mobs spawning will be disabled for this world.
     * <p>
     * Minecraft default: 1.
     *
     * @param spawnCategory the category spawn
     * @param ticksPerCategorySpawn the ticks per {@link SpawnCategory} mob spawns value you
     *     want to set the world to
     */
    World.setTicksPerSpawns(@NotNull SpawnCategory spawnCategory, int ticksPerCategorySpawn);