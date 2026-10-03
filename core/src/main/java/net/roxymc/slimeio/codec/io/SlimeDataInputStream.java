package net.roxymc.slimeio.codec.io;

import com.github.luben.zstd.ZstdInputStreamNoFinalizer;
import net.roxymc.slimeio.util.function.IOBiFunction;
import net.roxymc.slimeio.util.io.LimitedInputStream;

import java.io.*;

public class SlimeDataInputStream extends DataInputStream implements SlimeDataInput {
    public SlimeDataInputStream(InputStream in) {
        super(in);
    }

    private <T> T readSized(
            InputStream in, int length, IOBiFunction<? super SlimeDataInputStream, ? super Integer, ? extends T> reader
    ) throws IOException {
        LimitedInputStream limitedIn = new LimitedInputStream(in, length);
        T value = reader.apply(new SlimeDataInputStream(limitedIn), length);

        int unread = limitedIn.available();
        if (unread > 0) {
            throw new IOException("%d of %d bytes left unread".formatted(unread, length));
        }

        return value;
    }

    @Override
    public <T> T readSized(IOBiFunction<? super SlimeDataInput, ? super Integer, ? extends T> reader) throws IOException {
        int length = readInt();

        return readSized(in, length, reader);
    }

    @Override
    public <T> T readCompressed(IOBiFunction<? super SlimeDataInput, ? super Integer, ? extends T> reader) throws IOException {
        int compressedLength = readInt();
        int rawLength = readInt();

        return readSized(in, compressedLength, (cin, $) -> {
            // we need to free up zstd input stream resources, but we can't close our input stream
            FilterInputStream noCloseIn = new FilterInputStream(cin.in) {
                @Override
                public void close() {
                }
            };

            try (InputStream zstdIn = new ZstdInputStreamNoFinalizer(noCloseIn)) {
                T value = readSized(zstdIn, rawLength, reader);

                // if raw bytes were empty, compressed bytes may contain marker data. we should skip it
                zstdIn.transferTo(OutputStream.nullOutputStream());

                return value;
            }
        });
    }
}
