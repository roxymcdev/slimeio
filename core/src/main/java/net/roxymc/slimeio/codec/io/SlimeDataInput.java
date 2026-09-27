package net.roxymc.slimeio.codec.io;

import net.roxymc.slimeio.util.function.IOBiFunction;
import net.roxymc.slimeio.util.function.IOFunction;
import net.roxymc.slimeio.util.function.IOSupplier;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface SlimeDataInput extends DataInput {
    <T> T readSized(IOBiFunction<? super SlimeDataInput, ? super Integer, ? extends T> reader) throws IOException;

    default <T> List<T> readList(IOSupplier<? extends T> reader) throws IOException {
        int length = readInt();
        List<T> list = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            list.add(reader.get());
        }

        return list;
    }

    default <T> T readCompressed(IOFunction<? super SlimeDataInput, ? extends T> reader) throws IOException {
        return readCompressed((in, length) -> reader.apply(in));
    }

    <T> T readCompressed(IOBiFunction<? super SlimeDataInput, ? super Integer, ? extends T> reader) throws IOException;
}
