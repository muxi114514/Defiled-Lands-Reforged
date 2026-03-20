package lykrast.defiledlands.common.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;

public class EffectEventHandler {

    public static void register() {
        // 监听实体受伤事件，处理 Vulnerability 效果
        EntityEvent.LIVING_HURT.register((entity, source, amount) -> EventResult.pass());
        // Blastem 碰撞检测在 ForgeEventHandler 中通过原生 Forge tick 事件实现
    }
}
