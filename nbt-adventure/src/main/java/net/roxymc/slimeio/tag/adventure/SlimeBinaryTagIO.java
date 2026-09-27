package net.roxymc.slimeio.tag.adventure;

import net.kyori.adventure.nbt.*;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.tag.SlimeTagIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public final class SlimeBinaryTagIO implements SlimeTagIO<CompoundBinaryTag> {
    public static final SlimeBinaryTagIO INSTANCE = new SlimeBinaryTagIO();

    private SlimeBinaryTagIO() {
    }

    @Override
    public CompoundBinaryTag read(SlimeDataInput in, int length) throws IOException {
        return length > 0 ? BinaryTagIO.reader().read(in) : CompoundBinaryTag.empty();
    }

    @Override
    public <E> List<E> readNamedList(
            SlimeDataInput in, String key, Function<? super CompoundBinaryTag, ? extends E> wrap
    ) throws IOException {
        ListBinaryTag listTag = readSized(in).getList(key, BinaryTagTypes.COMPOUND);

        List<E> list = new ArrayList<>(listTag.size());

        for (BinaryTag tag : listTag) {
            list.add(wrap.apply((CompoundBinaryTag) tag));
        }

        return list;
    }

    @Override
    public void write(SlimeDataOutput out, CompoundBinaryTag tag) throws IOException {
        if (!tag.isEmpty()) {
            BinaryTagIO.writer().write(tag, out);
        }
    }

    @Override
    public <E> void writeNamedList(
            SlimeDataOutput out, String key, Collection<? extends E> list, Function<? super E, ? extends CompoundBinaryTag> unwrap
    ) throws IOException {
        ListBinaryTag.Builder<CompoundBinaryTag> listTag = ListBinaryTag.builder(BinaryTagTypes.COMPOUND, list.size());

        for (E element : list) {
            listTag.add(unwrap.apply(element));
        }

        writeSized(out, CompoundBinaryTag.builder(1).put(key, listTag.build()).build());
    }
}
