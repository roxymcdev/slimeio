package net.roxymc.slimeio.data.world;

import net.roxymc.slimeio.data.SlimeSnapshottable;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkData;

import java.util.List;

public record SlimeWorldSnapshot<T>(
        int version,
        List<SlimeChunkData<T>> chunks,
        T extraData
) implements SlimeWorldData<T> {
    public SlimeWorldSnapshot {
        chunks = chunks.stream().map(SlimeSnapshottable::snapshot).toList();
    }

    public SlimeWorldSnapshot(SlimeWorldData<T> data) {
        this(data.version(), data.chunks(), data.extraData());
    }

    @Override
    public SlimeWorldData<T> snapshot() {
        return this;
    }
}
