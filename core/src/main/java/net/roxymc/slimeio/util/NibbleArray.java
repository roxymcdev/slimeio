package net.roxymc.slimeio.util;

import java.util.Arrays;
import java.util.StringJoiner;

public record NibbleArray(byte[] data) {
    public NibbleArray {
        data = data.clone();
    }

    public int size() {
        return data.length * Nibbles.PER_BYTE;
    }

    public byte get(int index) {
        int value = data[index / 2];
        return (byte) (index % 2 == 0 ? value & 0xF : (value & 0xF0) >> 4);
    }

    @Override
    public byte[] data() {
        return data.clone();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof NibbleArray that)) {
            return false;
        }

        return Arrays.equals(this.data, that.data);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        for (int i = 0; i < data.length * 2; i++) {
            joiner.add(Byte.toString(get(i)));
        }

        return joiner.toString();
    }
}
