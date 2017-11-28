package com.rilixtech.agendacalendarview.render;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.rilixtech.agendacalendarview.models.CalendarEvent;

import java.lang.reflect.ParameterizedType;

/**
 * Base class for helping layout rendering
 */
public abstract class EventRenderer<T extends CalendarEvent> {
    public abstract void render(final View view, final T event);

    @LayoutRes
    public abstract int getEventLayout();

    public Class<T> getRenderType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
