package lykrast.defiledlands.common.entity;

/**
 * 污秽之地原生生物的标记接口。
 * 实现此接口的生物默认不受爆炸植物（Blastem）和恶棘（Vilespine）的影响，
 * 除非它们通过覆写相应方法明确声明受影响。
 */
public interface IEntityDefiled {

    /**
     * 该生物是否受成熟爆炸植物（Blastem）爆炸的影响。
     * 返回 false 表示该生物不会触发 Blastem 爆炸。
     */
    default boolean affectedByBlastem() {
        return false;
    }

    /**
     * 该生物是否受恶棘（Vilespine）伤害的影响。
     * 返回 false 表示该生物不会受到 Vilespine 的伤害。
     */
    default boolean affectedByVilespine() {
        return false;
    }
}
