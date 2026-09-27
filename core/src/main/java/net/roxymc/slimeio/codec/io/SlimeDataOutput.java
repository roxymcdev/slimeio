package net.roxymc.slimeio.codec.io;

import net.roxymc.slimeio.util.function.IOBiConsumer;
import net.roxymc.slimeio.util.function.IOConsumer;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;

public interface SlimeDataOutput extends DataOutput {
    default void writeSized(byte[] bytes) throws IOException {
        writeInt(bytes.length);
        write(bytes);
    }

    void writeSized(IOConsumer<? super SlimeDataOutput> writer) throws IOException;

    default <T> void writeList(Collection<? extends T> list, IOConsumer<? super T> writer) throws IOException {
        writeInt(list.size());

        for (T element : list) {
            writer.accept(element);
        }
    }

    void writeCompressed(IOConsumer<? super SlimeDataOutput> writer) throws IOException;

    default <T> void writeCompressed(T value, IOBiConsumer<? super SlimeDataOutput, ? super T> writer) throws IOException {
        writeCompressed(out -> writer.accept(out, value));
    }
}
