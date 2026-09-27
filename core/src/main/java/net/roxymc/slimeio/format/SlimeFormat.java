package net.roxymc.slimeio.format;

import net.roxymc.slimeio.codec.world.SlimeChunkCodec;
import net.roxymc.slimeio.codec.world.SlimeChunkSectionCodec;
import net.roxymc.slimeio.codec.world.SlimeWorldCodec;

public record SlimeFormat(
        byte version,
        SlimeWorldCodec worldCodec,
        SlimeChunkCodec chunkCodec,
        SlimeChunkSectionCodec chunkSectionCodec
) {
    public static final short SLIME_HEADER = (short) 0xB10B; // packed bytes {-79, 11}
}
