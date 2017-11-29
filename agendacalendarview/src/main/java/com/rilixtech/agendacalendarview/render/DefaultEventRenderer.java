package com.rilixtech.agendacalendarview.render;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rilixtech.agendacalendarview.R;
import com.rilixtech.agendacalendarview.models.BaseCalendarEvent;

/**
 * Class helping to inflate our default layout in the AgendaAdapter
 */
public class DefaultEventRenderer extends EventRenderer<BaseCalendarEvent> {

  // region class - EventRenderer

  @Override public void render(@NonNull View view, @NonNull BaseCalendarEvent event) {
    TextView tvTitle = view.findViewById(R.id.view_agenda_event_title);
    TextView tvLocation = view.findViewById(R.id.view_agenda_event_location);
    LinearLayout descriptionContainer = view.findViewById(R.id.view_agenda_event_description_container);
    LinearLayout locationContainer = view.findViewById(R.id.view_agenda_event_location_container);
    LinearLayout descriptionContainerLeft = view.findViewById(R.id.view_agenda_event_left);

    descriptionContainer.setVisibility(View.VISIBLE);
    tvTitle.setTextColor(view.getResources().getColor(android.R.color.black));

    tvTitle.setText(event.getTitle());
    tvLocation.setText(event.getLocation());
    if (event.getLocation().length() > 0) {
      locationContainer.setVisibility(View.VISIBLE);
      tvLocation.setText(event.getLocation());
    } else {
      locationContainer.setVisibility(View.GONE);
    }

    if (event.getTitle().equals(view.getResources().getString(R.string.agenda_event_no_events))) {
      tvTitle.setTextColor(view.getResources().getColor(R.color.blue_selected));
    } else {
      tvTitle.setTextColor(view.getResources().getColor(R.color.theme_text_icons));
    }
    descriptionContainerLeft.setBackgroundColor(event.getColor());
    tvLocation.setTextColor(view.getResources().getColor(R.color.theme_text_icons));
  }

  @Override public int getEventLayout() {
    return R.layout.view_agenda_event;
  }

  // endregion
}
