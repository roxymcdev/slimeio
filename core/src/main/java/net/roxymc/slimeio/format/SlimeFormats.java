package net.roxymc.slimeio.format;

import net.roxymc.slimeio.codec.world.v12.SlimeChunkCodec_v12;
import net.roxymc.slimeio.codec.world.v12.SlimeChunkSectionCodec_v12;
import net.roxymc.slimeio.codec.world.v12.SlimeWorldCodec_v12;

public final class SlimeFormats {
    public static final SlimeFormat FORMAT_V12 = new SlimeFormat(
            (byte) 12,
            SlimeWorldCodec_v12.INSTANCE,
            SlimeChunkCodec_v12.INSTANCE,
            SlimeChunkSectionCodec_v12.INSTANCE
    );

    public static final SlimeFormat LATEST = FORMAT_V12;

    private SlimeFormats() {
    }

    public static SlimeFormat forVersion(byte version) {
        return switch (version) {
            case 12 -> FORMAT_V12;
            default -> throw new IllegalArgumentException("Unsupported version: " + version);
        };
    }
}
