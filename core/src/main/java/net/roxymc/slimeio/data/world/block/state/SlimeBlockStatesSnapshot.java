package net.roxymc.slimeio.data.world.block.state;

public record SlimeBlockStatesSnapshot<T>(T data) implements SlimeBlockStatesData<T> {
}
