package net.roxymc.slimeio.codec.world;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkData;

import java.io.IOException;

public interface SlimeChunkCodec {
    <T> SlimeChunkData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> context) throws IOException;

    <T> void encode(SlimeDataOutput out, SlimeChunkData<T> world, SlimeEncodeContext<T> context) throws IOException;
}
