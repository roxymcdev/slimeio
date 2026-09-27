package net.roxymc.slimeio.codec.world.v12;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.codec.world.SlimeChunkCodec;
import net.roxymc.slimeio.codec.world.SlimeChunkSectionCodec;
import net.roxymc.slimeio.data.world.block.entity.SlimeBlockEntityData;
import net.roxymc.slimeio.data.world.block.entity.SlimeBlockEntitySnapshot;
import net.roxymc.slimeio.data.world.chunk.*;
import net.roxymc.slimeio.data.world.entity.SlimeEntityData;
import net.roxymc.slimeio.data.world.entity.SlimeEntitySnapshot;

import java.io.IOException;
import java.util.List;

public final class SlimeChunkCodec_v12 implements SlimeChunkCodec {
    public static final SlimeChunkCodec INSTANCE = new SlimeChunkCodec_v12();

    private static final String TILE_ENTITIES = "tileEntities";
    private static final String ENTITIES = "entities";

    private SlimeChunkCodec_v12() {
    }

    @Override
    public <T> SlimeChunkData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> ctx) throws IOException {
        int x = in.readInt();
        int z = in.readInt();

        SlimeChunkSectionCodec sectionCodec = ctx.format().chunkSectionCodec();
        List<SlimeChunkSectionData<T>> sections = in.readList(() -> sectionCodec.decode(in, ctx));

        SlimeHeightmapsData<T> heightmaps = new SlimeHeightmapsSnapshot<>(ctx.tagIO().readSized(in));

        List<SlimeBlockEntityData<T>> blockEntities = ctx.tagIO().readNamedList(in, TILE_ENTITIES, SlimeBlockEntitySnapshot::new);
        List<SlimeEntityData<T>> entities = ctx.tagIO().readNamedList(in, ENTITIES, SlimeEntitySnapshot::new);

        T extraData = ctx.tagIO().readSized(in);

        return new SlimeChunkSnapshot<>(x, z, sections, heightmaps, blockEntities, entities, extraData);
    }

    @Override
    public <T> void encode(SlimeDataOutput out, SlimeChunkData<T> chunk, SlimeEncodeContext<T> ctx) throws IOException {
        out.writeInt(chunk.x());
        out.writeInt(chunk.z());

        SlimeChunkSectionCodec sectionCodec = ctx.format().chunkSectionCodec();
        out.writeList(chunk.sections(), section -> sectionCodec.encode(out, section, ctx));

        ctx.tagIO().writeSized(out, chunk.heightmaps().data());

        ctx.tagIO().writeNamedList(out, TILE_ENTITIES, chunk.blockEntities(), SlimeBlockEntityData::data);
        ctx.tagIO().writeNamedList(out, ENTITIES, chunk.entities(), SlimeEntityData::data);

        ctx.tagIO().writeSized(out, chunk.extraData());
    }
}