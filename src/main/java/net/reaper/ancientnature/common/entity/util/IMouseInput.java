package net.reaper.ancientnature.common.entity.util;

@FunctionalInterface
public interface IMouseInput {
    void onMouseClick(int pButton);

    default boolean isActionDenied(int pActionId) {
        return false;
    }
}
