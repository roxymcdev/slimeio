package net.roxymc.slimeio.data.world.chunk;

import net.roxymc.slimeio.data.SlimeSnapshottable;
import net.roxymc.slimeio.data.world.biome.SlimeBiomesData;
import net.roxymc.slimeio.data.world.block.state.SlimeBlockStatesData;
import net.roxymc.slimeio.util.NibbleArray;
import org.jspecify.annotations.Nullable;

public interface SlimeChunkSectionData<T> extends SlimeSnapshottable<SlimeChunkSectionData<T>> {
    @Nullable NibbleArray blockLight();

    @Nullable NibbleArray skyLight();

    SlimeBlockStatesData<T> blockStates();

    SlimeBiomesData<T> biomes();
}
