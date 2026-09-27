package net.roxymc.slimeio.codec.io;

import com.github.luben.zstd.Zstd;
import net.roxymc.slimeio.util.function.IOConsumer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SlimeDataOutputStream extends DataOutputStream implements SlimeDataOutput {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public SlimeDataOutputStream(OutputStream out) {
        super(out);
    }

    protected byte[] capture(IOConsumer<? super SlimeDataOutput> writer) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writer.accept(new SlimeDataOutputStream(out));
            return out.toByteArray();
        }
    }

    @Override
    public void writeSized(IOConsumer<? super SlimeDataOutput> writer) throws IOException {
        writeSized(capture(writer));
    }

    @Override
    public void writeCompressed(IOConsumer<? super SlimeDataOutput> writer) throws IOException {
        byte[] raw = capture(writer);
        byte[] compressed = raw.length > 0 ? Zstd.compress(raw) : EMPTY_BYTE_ARRAY;

        writeInt(compressed.length);
        writeInt(raw.length);

        write(compressed);
    }
}
