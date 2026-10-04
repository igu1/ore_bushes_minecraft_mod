package me.ez.orebushes.Events;

/**
 * Villager trades on 26.1.x.
 *
 * <p>Minecraft 26.1 replaced the old {@code VillagerTradesEvent} /
 * {@code WandererTradesEvent} injection hooks with a fully data-driven system:
 * individual trades are {@code villager_trade} datapack registry entries and
 * professions reference {@code trade_set} entries (see
 * {@code net.minecraft.world.item.trading.VillagerTrade} / {@code TradeSet}).
 * NeoForge no longer exposes an event for appending offers.
 *
 * <p>Porting the seed trades therefore means shipping {@code villager_trade}
 * JSON entries plus overriding the vanilla {@code farmer/*} and
 * {@code wandering_trader} {@code trade_set}s (reproducing the vanilla trades in
 * each). That is tracked as follow-up work; until then the {@code
 * enableVillagerTrades} config flag has no effect on this branch.
 */
public final class VillagerTradeHandler {
    private VillagerTradeHandler() {}
}
