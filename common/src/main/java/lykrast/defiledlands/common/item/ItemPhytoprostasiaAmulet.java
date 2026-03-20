package lykrast.defiledlands.common.item;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * Phytoprostasia Amulet - 邪恶植物护符
 * 1. 抵抗邪恶荆棘（Vilespine）的伤害
 * 2. 加速爆炸植物（Blastem）的成熟
 * 
 * 注意：实际功能需要在 Vilespine 和 Blastem 方块中检查玩家是否佩戴此护身符
 */
public class ItemPhytoprostasiaAmulet extends Item implements ICurioItem {
    public ItemPhytoprostasiaAmulet(Properties properties) {
        super(properties);
    }
    
    // 护身符的效果在方块类中检查
    // VilespineBlock 会检查玩家是否佩戴此护身符来决定是否造成伤害
    // BlastemBlock 会检查玩家是否佩戴此护身符来加速成熟
}
