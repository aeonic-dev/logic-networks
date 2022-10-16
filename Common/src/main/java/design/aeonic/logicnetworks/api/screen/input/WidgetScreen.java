package design.aeonic.logicnetworks.api.screen.input;

import javax.annotation.Nullable;

public interface WidgetScreen {

    /**
     * Widgets should be created with positions relative to the screen; the pose stack will be translated before
     * rendering so you won't need to worry about absolute coordinates.
     */
    <W extends InputWidget> W addWidget(W widget);

    void removeWidget(InputWidget widget);

    @Nullable InputWidget getWidgetAt(int x, int y);

    @Nullable InputWidget getFocusedWidget();

    void setFocus(InputWidget widget);

    void clearFocus(InputWidget widget);
}
