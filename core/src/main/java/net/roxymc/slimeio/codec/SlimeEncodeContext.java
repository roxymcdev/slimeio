package net.roxymc.slimeio.codec;

import net.roxymc.slimeio.format.SlimeFormat;
import net.roxymc.slimeio.tag.SlimeTagIO;

public record SlimeEncodeContext<T>(SlimeFormat format, SlimeTagIO<T> tagIO) {
}
