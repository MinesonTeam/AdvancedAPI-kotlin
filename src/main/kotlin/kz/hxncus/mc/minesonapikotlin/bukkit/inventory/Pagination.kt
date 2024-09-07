package kz.hxncus.mc.minesonapikotlin.bukkit.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class Pagination(val inventory: Inventory) {
    val currentPages: MutableMap<String, Int> by lazy { mutableMapOf() }
    val pages: MutableList<Inventory> by lazy { ArrayList() }

    fun openPage(player: Player, pageIndex: Int) {
        currentPages[player.name] = pageIndex
        val inventory: Inventory = if (pageIndex < 0 || pageIndex >= currentPages.size) {
                                       this.inventory
                                   } else {
                                       getPage(pageIndex)
                                   }
        player.openInventory(inventory)
    }

    fun getCurrentPage(player: Player): Int = currentPages[player.name] ?: 0

    fun hasNextPage(player: Player): Boolean {
        val nextPage = currentPages[player.name]?.plus(1) ?: 0
        return nextPage < currentPages.size
    }

    fun hasPreviousPage(player: Player): Boolean {
        val previousPage = currentPages[player.name]?.minus(1) ?: 0
        return previousPage >= 0 && previousPage < currentPages.size
    }

    fun getNextPage(player: Player): Inventory {
        val nextPageIndex = (currentPages[player.name] ?: 0) + 1
        currentPages[player.name] = nextPageIndex
        return getPage(nextPageIndex)
    }

    fun getPreviousPage(player: Player): Inventory {
        val previousPageIndex = (currentPages[player.name] ?: 0) - 1
        currentPages[player.name] = previousPageIndex
        return getPage(previousPageIndex)
    }

    fun getPage(pageIndex: Int): Inventory {
        return pages[pageIndex]
    }

    fun addPage(inventory: Inventory): Pagination {
        return this.addPages(listOf(inventory))
    }

    fun addPages(vararg inventories: Inventory): Pagination {
        return this.addPages(listOf(*inventories))
    }

    fun addPages(inventories: List<Inventory>): Pagination {
        for (simpleInventory in inventories) {
            this.addPage(simpleInventory)
        }
        return this
    }

    fun setPage(pageIndex: Int, inventory: Inventory): Pagination {
        pages[pageIndex] = inventory
        return this
    }
}