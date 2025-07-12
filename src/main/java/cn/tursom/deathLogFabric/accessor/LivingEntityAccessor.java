package cn.tursom.deathLogFabric.accessor;

import net.minecraft.network.chat.Component;

public interface LivingEntityAccessor {
    Component getDeathLogFabric$deathMessage();
    void setDeathLogFabric$deathMessage(Component message);
}
