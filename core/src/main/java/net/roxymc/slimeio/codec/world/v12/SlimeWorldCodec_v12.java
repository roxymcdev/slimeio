package net.roxymc.slimeio.codec.world.v12;

import net.roxymc.slimeio.codec.SlimeDecodeContext;
import net.roxymc.slimeio.codec.SlimeEncodeContext;
import net.roxymc.slimeio.codec.io.SlimeDataInput;
import net.roxymc.slimeio.codec.io.SlimeDataOutput;
import net.roxymc.slimeio.codec.world.SlimeChunkCodec;
import net.roxymc.slimeio.codec.world.SlimeWorldCodec;
import net.roxymc.slimeio.data.world.SlimeWorldData;
import net.roxymc.slimeio.data.world.SlimeWorldSnapshot;
import net.roxymc.slimeio.data.world.chunk.SlimeChunkData;

import java.io.IOException;
import java.util.List;

public final class SlimeWorldCodec_v12 implements SlimeWorldCodec {
    public static final SlimeWorldCodec INSTANCE = new SlimeWorldCodec_v12();

    private SlimeWorldCodec_v12() {
    }

    @Override
    public <T> SlimeWorldData<T> decode(SlimeDataInput in, SlimeDecodeContext<T> ctx) throws IOException {
        int version = in.readInt();

        List<SlimeChunkData<T>> chunks = in.readCompressed(cin -> {
            SlimeChunkCodec chunkCodec = ctx.format().chunkCodec();

            return cin.readList(() -> chunkCodec.decode(cin, ctx));
        });
        T extraData = in.readCompressed(ctx.tagIO()::read);

        return new SlimeWorldSnapshot<>(version, chunks, extraData);
    }

    @Override
    public <T> void encode(SlimeDataOutput out, SlimeWorldData<T> world, SlimeEncodeContext<T> ctx) throws IOException {
        out.writeInt(world.version());

        out.writeCompressed(cout -> {
            SlimeChunkCodec chunkCodec = ctx.format().chunkCodec();

            cout.writeList(world.chunks(), chunk -> chunkCodec.encode(cout, chunk, ctx));
        });
        out.writeCompressed(world.extraData(), ctx.tagIO()::write);
    }
}
