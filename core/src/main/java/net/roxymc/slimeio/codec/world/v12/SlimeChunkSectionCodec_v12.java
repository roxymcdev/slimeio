package net.roxymc.slimeio.codec.world.v12;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.codec.world.SlimeChunkSectionCodec;
import net.roxymc.slimeio.data.world.biome.SlimeBiomesSnapshot;
import net.roxymc.slimeio.data.world.block.state.SlimeBlockStatesSnapshot;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkSectionData;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkSectionSnapshot;
import net.roxymc.slimeio.util.NibbleArray;
import net.roxymc.slimeio.util.Nibbles;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

public final class SlimeChunkSectionCodec_v12 implements SlimeChunkSectionCodec {
    public static final SlimeChunkSectionCodec INSTANCE = new SlimeChunkSectionCodec_v12();

    private static final int LIGHT_NIBBLES = 16 * 16 * 16;
    private static final int LIGHT_BYTES = LIGHT_NIBBLES / Nibbles.PER_BYTE;

    private SlimeChunkSectionCodec_v12() {
    }

    @Override
    public <T> SlimeChunkSectionData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> ctx) throws IOException {
        NibbleArray blockLight = decodeLightData(in);
        NibbleArray skyLight = decodeLightData(in);

        SlimeBlockStatesSnapshot<T> blockStates = new SlimeBlockStatesSnapshot<>(ctx.tagIO().readSized(in));
        SlimeBiomesSnapshot<T> biomes = new SlimeBiomesSnapshot<>(ctx.tagIO().readSized(in));

        return new SlimeChunkSectionSnapshot<>(blockLight, skyLight, blockStates, biomes);
    }

    private @Nullable NibbleArray decodeLightData(SlimeDataInput in) throws IOException {
        if (!in.readBoolean()) {
            return null;
        }

        byte[] data = new byte[LIGHT_BYTES];
        in.readFully(data);

        return new NibbleArray(data);
    }

    @Override
    public <T> void encode(SlimeDataOutput out, SlimeChunkSectionData<T> section, SlimeEncodeContext<T> ctx) throws IOException {
        encodeLightData(out, section.blockLight());
        encodeLightData(out, section.skyLight());

        ctx.tagIO().writeSized(out, section.blockStates().data());
        ctx.tagIO().writeSized(out, section.biomes().data());
    }

    private void encodeLightData(SlimeDataOutput out, @Nullable NibbleArray data) throws IOException {
        out.writeBoolean(data != null);

        if (data == null) {
            return;
        }

        if (data.size() != LIGHT_NIBBLES) {
            throw new IOException("Expected " + LIGHT_NIBBLES + " nibbles, got " + data.size());
        }

        out.write(data.data());
    }
}