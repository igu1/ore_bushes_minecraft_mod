package me.ez.orebushes;

/**
 * Bush world generation on 26.1.x.
 *
 * <p>Configured/placed features are datapack registries since 1.21. On 26.1 the
 * old {@code RANDOM_PATCH} feature was removed and features must be declared in a
 * {@code RegistrySetBuilder} (bootstrapped to JSON under
 * {@code data/orebushes/worldgen/...}). Porting the full set is tracked as
 * follow-up work; until then this branch ships without natural bush spawning.
 */
public final class WorldGen {
    private WorldGen() {}
}
