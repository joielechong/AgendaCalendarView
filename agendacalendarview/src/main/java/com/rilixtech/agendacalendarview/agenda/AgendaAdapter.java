package com.rilixtech.agendacalendarview.agenda;

import com.rilixtech.agendacalendarview.models.CalendarEvent;
import com.rilixtech.agendacalendarview.render.DefaultEventRenderer;
import com.rilixtech.agendacalendarview.render.EventRenderer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import com.rilixtech.stickylistheaders.StickyListHeadersAdapter;

/**
 * Adapter for the agenda, implements StickyListHeadersAdapter.
 * Days as sections and CalendarEvents as list items.
 */
public class AgendaAdapter extends BaseAdapter implements StickyListHeadersAdapter {

  private List<CalendarEvent> mEvents = new ArrayList<>();
  private EventRenderer eventRenderer;
  private int mCurrentDayColor;

  public AgendaAdapter(int currentDayTextColor) {
    this.mCurrentDayColor = currentDayTextColor;
    this.eventRenderer = new DefaultEventRenderer();
  }

  public AgendaAdapter(int currentDayTextColor, EventRenderer<?> eventRenderer) {
    this.mCurrentDayColor = currentDayTextColor;
    this.eventRenderer = eventRenderer;
  }

  public void updateEvents(List<CalendarEvent> events) {
    this.mEvents.clear();
    this.mEvents.addAll(events);
    notifyDataSetChanged();
  }

  @Override public View getHeaderView(int position, View convertView, ViewGroup parent) {
    AgendaHeaderView agendaHeaderView = (AgendaHeaderView) convertView;
    if (agendaHeaderView == null) {
      agendaHeaderView = AgendaHeaderView.inflate(parent);
    }
    agendaHeaderView.setDay(getItem(position).getInstanceDay(), mCurrentDayColor);
    return agendaHeaderView;
  }

  @Override public long getHeaderId(int position) {
    return mEvents.get(position).getInstanceDay().getTimeInMillis();
  }

  @Override public int getCount() {
    return mEvents.size();
  }

  @Override public CalendarEvent getItem(int position) {
    return mEvents.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final CalendarEvent event = getItem(position);
    convertView = LayoutInflater.from(parent.getContext()).inflate(eventRenderer.getEventLayout(), parent, false);
    eventRenderer.render(convertView, event);
    return convertView;
  }
}
