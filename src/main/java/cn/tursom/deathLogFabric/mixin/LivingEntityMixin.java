package cn.tursom.deathLogFabric.mixin;

import cn.tursom.deathLogFabric.accessor.LivingEntityAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityAccessor {
    // 添加私有字段
    private Component deathLogFabric$deathMessage;

    // 提供访问方法
    @Override
    public Component getDeathLogFabric$deathMessage() {
        return deathLogFabric$deathMessage;
    }

    @Override
    public void setDeathLogFabric$deathMessage(Component message) {
        this.deathLogFabric$deathMessage = message;
    }
}
