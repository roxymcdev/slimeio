package net.roxymc.slimeio.data.world.entity;

public record SlimeEntitySnapshot<T>(T data) implements SlimeEntityData<T> {
}
