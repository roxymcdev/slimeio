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
    public SlimeChunkSectionSnapshot(SlimeChunkSectionData<T> data) {
        this(data.blockLight(), data.skyLight(), data.blockStates(), data.biomes());
    }

    @Override
    public SlimeChunkSectionData<T> snapshot() {
        return this;
    }
}
