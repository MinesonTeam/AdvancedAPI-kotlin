package kz.hxncus.mc.minesonapikotlin.bukkit.world

import org.bukkit.Difficulty
import org.bukkit.block.Biome
import org.bukkit.block.data.BlockData
import org.bukkit.metadata.MetadataValue

/**
 * Class World settings.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class WorldSettings {
    private val autoSave: Boolean? = null
    private val tripleBiomeMap: Map<Triple<Int, Int, Int>, Biome>? = null
    private val tripleBlockDataMap: Map<Triple<Int, Int, Int>, BlockData>? = null
    private val chunkBoolMap: Map<Pair<Int, Int>, Boolean>? = null
    private val clearWeatherDuration: Int? = null
    private val difficulty: Difficulty? = null
    private val fullTime: Long? = null
    private val hardcore: Boolean? = null
    private val keepSpawnInMemory: Boolean? = null
    private val strMetadataValMap: Map<String, MetadataValue>? = null
    private val storm: Boolean? = null
    private val time: Long? = null

    /**
     * Apply simple world.
     *
     * @param world the world
     * @return the simple world
     */
    fun apply(world: SimpleWorld): SimpleWorld {
        this.autoSave?.let { world.setAutoSave(it) }
        for ((key, value) in tripleBiomeMap!!) {
            world.setBiome(key.first, key.second, key.third, value)
        }
        //		FunctionalUtil.acceptIfTrue(() -> this.tripletBlockDataMap != null, consumerWorld -> {
//			for (final Map.Entry<Triplet<Integer, Integer, Integer>, BlockData> entry : this.tripletBlockDataMap.entrySet()) {
//				final Triplet<Integer, Integer, Integer> key = entry.getKey();
//				consumerWorld.setBlockData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue());
//			}
//		}, world);
//		FunctionalUtil.acceptIfTrue(() -> this.chunkBoolMap != null, consumerWorld -> {
//			for (final Map.Entry<Pair<Integer, Integer>, Boolean> entry : this.chunkBoolMap.entrySet()) {
//				final Pair<Integer, Integer> key = entry.getKey();
//				consumerWorld.setChunkForceLoaded(key.getLeft(), key.getRight(), entry.getValue());
//			}
//		}, world);
//		FunctionalUtil.acceptIfTrue(() -> this.clearWeatherDuration != null, consumerWorld -> consumerWorld.setClearWeatherDuration(this.clearWeatherDuration), world);
//		FunctionalUtil.acceptIfTrue(() -> this.difficulty != null, consumerWorld -> consumerWorld.setDifficulty(this.difficulty), world);
//		FunctionalUtil.acceptIfTrue(() -> this.fullTime != null, consumerWorld -> consumerWorld.setFullTime(this.fullTime), world);
//		FunctionalUtil.acceptIfTrue(() -> this.hardcore != null, consumerWorld -> consumerWorld.setHardcore(this.hardcore), world);
//		FunctionalUtil.acceptIfTrue(() -> this.storm != null, consumerWorld -> consumerWorld.setStorm(this.storm), world);
        return world
    }
}
