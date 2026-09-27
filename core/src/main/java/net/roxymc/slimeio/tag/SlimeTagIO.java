package net.roxymc.slimeio.tag;

import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface SlimeTagIO<T> {
    T read(SlimeDataInput in, int length) throws IOException;

    default T readSized(SlimeDataInput in) throws IOException {
        return in.readSized(this::read);
    }

    <E> List<E> readNamedList(
            SlimeDataInput in, String key, Function<? super T, ? extends E> wrap
    ) throws IOException;

    void write(SlimeDataOutput out, T tag) throws IOException;

    default void writeSized(SlimeDataOutput out, T tag) throws IOException {
        out.writeSized(out0 -> write(out0, tag));
    }

    <E> void writeNamedList(
            SlimeDataOutput out, String key, Collection<? extends E> list, Function<? super E, ? extends T> unwrap
    ) throws IOException;
}