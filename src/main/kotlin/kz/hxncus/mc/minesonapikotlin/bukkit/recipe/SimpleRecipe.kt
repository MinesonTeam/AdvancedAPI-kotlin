package kz.hxncus.mc.minesonapikotlin.bukkit.recipe

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import kz.hxncus.mc.minesonapikotlin.util.NamespacedKey
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class SimpleRecipe : CraftingRecipe {
    private val plugin: MinesonAPI
    private val recipeType: RecipeType

    constructor(plugin: MinesonAPI, key: String, result: ItemStack, recipeType: RecipeType) : super(NamespacedKey(key), result) {
        this.plugin = plugin
        this.recipeType = recipeType
    }

    constructor(simpleRecipe: SimpleRecipe) : super(simpleRecipe.key, simpleRecipe.result) {
        this.plugin = simpleRecipe.plugin
        this.recipeType = simpleRecipe.recipeType
    }

    fun createRecipe() {
        if (this.recipeType == RecipeType.SHAPED) {
            val recipe = ShapedRecipe(this.key, this.result)
            plugin.server.addRecipe(recipe)
        } else {
            val recipe = ShapelessRecipe(this.key, this.result)
            plugin.server.addRecipe(recipe)
        }
    }
}
