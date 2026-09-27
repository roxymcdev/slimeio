package net.roxymc.slimeio;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInputStream;
import net.roxymc.slimeio.codec.io.SlimeDataOutputStream;
import net.roxymc.slimeio.data.world.SlimeWorldData;
import net.roxymc.slimeio.format.SlimeFormat;
import net.roxymc.slimeio.format.SlimeFormats;
import net.roxymc.slimeio.tag.SlimeTagIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class SlimeIO<T> {
    private final SlimeTagIO<T> tagIO;

    public SlimeIO(SlimeTagIO<T> tagIO) {
        this.tagIO = tagIO;
    }

    public SlimeWorldData<T> decode(InputStream is) throws IOException {
        SlimeDataInputStream in = new SlimeDataInputStream(is);

        if (in.readShort() != SlimeFormat.SLIME_HEADER) {
            throw new IOException("Not a slime world");
        }

        byte version = in.readByte();
        SlimeFormat format = SlimeFormats.forVersion(version);

        return format.worldCodec().decode(in, new SlimeDecodeContext<>(format, tagIO));
    }

    public void encode(OutputStream os, SlimeWorldData<T> world) throws IOException {
        encode(os, world, SlimeFormats.LATEST);
    }

    public void encode(OutputStream os, SlimeWorldData<T> world, SlimeFormat format) throws IOException {
        SlimeDataOutputStream out = new SlimeDataOutputStream(os);

        out.writeShort(SlimeFormat.SLIME_HEADER);
        out.writeByte(format.version());

        format.worldCodec().encode(out, world, new SlimeEncodeContext<>(format, tagIO));
    }
}
