package alex34567.meatrespawner.items

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.registries.IForgeRegistry

fun registerItemBlock(registry: IForgeRegistry<Item>, block: Block) {
    val itemBlock = ItemBlock(block)
    itemBlock.registryName = block.registryName!!
    itemBlock.unlocalizedName = block.unlocalizedName
    registry.register(itemBlock)
}
