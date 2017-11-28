package com.rilixtech.agendacalendarview;

import com.rilixtech.agendacalendarview.models.CalendarEvent;
import com.rilixtech.agendacalendarview.models.IDayItem;

import java.util.Calendar;

public interface CalendarPickerController {
    void onDaySelected(IDayItem dayItem);

    void onEventSelected(CalendarEvent event);

    void onScrollToDate(Calendar calendar);
}
