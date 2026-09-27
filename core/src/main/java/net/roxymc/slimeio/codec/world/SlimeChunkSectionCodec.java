package net.roxymc.slimeio.codec.world;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkSectionData;

import java.io.IOException;

public interface SlimeChunkSectionCodec {
    <T> SlimeChunkSectionData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> context) throws IOException;

    <T> void encode(SlimeDataOutput out, SlimeChunkSectionData<T> world, SlimeEncodeContext<T> context) throws IOException;
}
