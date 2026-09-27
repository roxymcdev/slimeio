package net.roxymc.slimeio.codec.world;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.data.world.SlimeWorldData;

import java.io.IOException;

public interface SlimeWorldCodec {
    <T> SlimeWorldData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> context) throws IOException;

    <T> void encode(SlimeDataOutput out, SlimeWorldData<T> world, SlimeEncodeContext<T> context) throws IOException;
}
