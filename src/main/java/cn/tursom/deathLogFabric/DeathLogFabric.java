package cn.tursom.deathLogFabric;

import cn.tursom.deathLogFabric.accessor.LivingEntityAccessor;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.slf4j.Logger;

public class DeathLogFabric implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        // 在实体确认死亡时记录死亡信息
        // 必须在此处记录，因为 AFTER_DEATH 在 die 执行之后调用，die 会清空死亡信息
        // 如果在 ALLOW_DEATH 就打印死亡记录，会造成有些实体没死亡却打印了死亡信息
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, damageAmount) -> {
            Component deathMessage = entity.getCombatTracker().getDeathMessage();
            ((LivingEntityAccessor) entity).setDeathLogFabric$deathMessage(deathMessage);
            return true;
        });

        // 在实体确认死亡后记录死亡信息
        ServerLivingEntityEvents.AFTER_DEATH.register(((entity, damageSource) -> {
            Component deathMessage = ((LivingEntityAccessor) entity).getDeathLogFabric$deathMessage();
            ((LivingEntityAccessor) entity).setDeathLogFabric$deathMessage(null);
            if (deathMessage == null) {
                deathMessage = entity.getCombatTracker().getDeathMessage();
            }

            ComponentContents deathMessageContents = deathMessage.getContents();
            if (deathMessageContents instanceof TranslatableContents translatableContents) {
                LOGGER.info("living death: [{}] [{}] {}",
                        entity.getType(),
                        translatableContents.getKey(),
                        deathMessage.getString());
            } else {
                LOGGER.info("living death: [{}] {}", entity.getType(), deathMessage.getString());
            }
        }));
    }
}
