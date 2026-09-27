package net.roxymc.slimeio.util.function;

import java.io.IOException;

@FunctionalInterface
public interface IOConsumer<T> {
    void accept(T t) throws IOException;
}
