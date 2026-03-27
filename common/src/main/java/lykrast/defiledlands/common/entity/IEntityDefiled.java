package lykrast.defiledlands.common.entity;

public interface IEntityDefiled {

    default boolean affectedByBlastem() {
        return false;
    }

    default boolean affectedByVilespine() {
        return false;
    }
}
