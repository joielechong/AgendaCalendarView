package com.rilixtech.sample;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rilixtech.agendacalendarview.models.CalendarEvent;
import com.rilixtech.agendacalendarview.render.EventRenderer;

public class DrawableEventRenderer extends EventRenderer<DrawableCalendarEvent> {

  //public DrawableEventRenderer() {
  //  super;
  //}

  @Override public void render(View view, DrawableCalendarEvent event) {
    ImageView imageView = view.findViewById(R.id.view_agenda_event_image);
    TextView txtTitle = view.findViewById(R.id.view_agenda_event_title);
    TextView txtLocation = view.findViewById(R.id.view_agenda_event_location);
    LinearLayout descriptionContainer = view.findViewById(R.id.view_agenda_event_description_container);
    LinearLayout locationContainer = view.findViewById(R.id.view_agenda_event_location_container);

    descriptionContainer.setVisibility(View.VISIBLE);

    imageView.setImageDrawable(view.getContext().getResources().getDrawable(event.getDrawableId()));

    txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));

    txtTitle.setText(event.getTitle());
    txtLocation.setText(event.getLocation());
    if (event.getLocation().length() > 0) {
      locationContainer.setVisibility(View.VISIBLE);
      txtLocation.setText(event.getLocation());
    } else {
      locationContainer.setVisibility(View.GONE);
    }

    if (event.getTitle().equals(view.getResources().getString(R.string.agenda_event_no_events))) {
      txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));
    } else {
      txtTitle.setTextColor(view.getResources().getColor(R.color.theme_text_icons));
    }
    descriptionContainer.setBackgroundColor(event.getColor());
    txtLocation.setTextColor(view.getResources().getColor(R.color.theme_text_icons));
  }

  @Override public int getEventLayout() {
    return R.layout.view_agenda_drawable_event;
  }

  @Override public Class<DrawableCalendarEvent> getRenderType() {
    return DrawableCalendarEvent.class;
  }

  @Override public EventRenderer<?> copy() {
    return new DrawableEventRenderer();
  }

  // endregion
}
