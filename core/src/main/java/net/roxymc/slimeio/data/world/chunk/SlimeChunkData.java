package net.roxymc.slimeio.data.world.chunk;

import net.roxymc.slimeio.data.SlimeExtraData;
import net.roxymc.slimeio.data.SlimeSnapshottable;
import net.roxymc.slimeio.data.world.block.entity.SlimeBlockEntityData;
import net.roxymc.slimeio.data.world.entity.SlimeEntityData;

import java.util.List;

public interface SlimeChunkData<T> extends SlimeExtraData<T>, SlimeSnapshottable<SlimeChunkData<T>> {
    int x();

    int z();

    List<SlimeChunkSectionData<T>> sections();

    SlimeHeightmapsData<T> heightmaps();

    List<SlimeBlockEntityData<T>> blockEntities();

    List<SlimeEntityData<T>> entities();
}
