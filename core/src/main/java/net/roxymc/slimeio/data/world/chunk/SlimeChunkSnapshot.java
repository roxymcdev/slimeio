package net.roxymc.slimeio.data.world.chunk;

import net.roxymc.slimeio.data.SlimeSnapshottable;
import net.roxymc.slimeio.data.world.block.entity.SlimeBlockEntityData;
import net.roxymc.slimeio.data.world.entity.SlimeEntityData;

import java.util.List;

public record SlimeChunkSnapshot<T>(
        int x,
        int z,
        List<SlimeChunkSectionData<T>> sections,
        SlimeHeightmapsData<T> heightmaps,
        List<SlimeBlockEntityData<T>> blockEntities,
        List<SlimeEntityData<T>> entities,
        T extraData
) implements SlimeChunkData<T> {
    public SlimeChunkSnapshot {
        sections = sections.stream().map(SlimeSnapshottable::snapshot).toList();
        blockEntities = List.copyOf(blockEntities);
        entities = List.copyOf(entities);
    }

    public SlimeChunkSnapshot(SlimeChunkData<T> data) {
        this(data.x(), data.z(), data.sections(), data.heightmaps(), data.blockEntities(), data.entities(), data.extraData());
    }

    @Override
    public SlimeChunkData<T> snapshot() {
        return this;
    }
}
