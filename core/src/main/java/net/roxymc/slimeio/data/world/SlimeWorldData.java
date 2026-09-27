package net.roxymc.slimeio.data.world;

import net.roxymc.slimeio.data.SlimeExtraData;
import net.roxymc.slimeio.data.SlimeSnapshottable;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkData;

import java.util.List;

public interface SlimeWorldData<T> extends SlimeExtraData<T>, SlimeSnapshottable<SlimeWorldData<T>> {
    int version();

    List<SlimeChunkData<T>> chunks();
}
