package net.roxymc.slimeio.data.world.chunk;

import net.roxymc.slimeio.data.world.biome.SlimeBiomesData;
import net.roxymc.slimeio.data.world.block.state.SlimeBlockStatesData;
import net.roxymc.slimeio.util.NibbleArray;
import org.jspecify.annotations.Nullable;

public record SlimeChunkSectionSnapshot<T>(
        @Nullable NibbleArray blockLight,
        @Nullable NibbleArray skyLight,
        SlimeBlockStatesData<T> blockStates,
        SlimeBiomesData<T> biomes
) implements SlimeChunkSectionData<T> {
    public SlimeChunkSectionSnapshot {
        if (blockLight != null) {
            blockLight = blockLight.clone();
        }

        if (skyLight != null) {
            skyLight = skyLight.clone();
        }
    }

    public SlimeChunkSectionSnapshot(SlimeChunkSectionData<T> data) {
        this(data.blockLight(), data.skyLight(), data.blockStates(), data.biomes());
    }

    @Override
    public @Nullable NibbleArray blockLight() {
        return blockLight != null ? blockLight.clone() : null;
    }

    @Override
    public @Nullable NibbleArray skyLight() {
        return skyLight != null ? skyLight.clone() : null;
    }

    @Override
    public SlimeChunkSectionData<T> snapshot() {
        return this;
    }
}
