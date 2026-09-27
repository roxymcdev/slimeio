package net.roxymc.slimeio.data.world.block.entity;

public record SlimeBlockEntitySnapshot<T>(T data) implements SlimeBlockEntityData<T> {
}
