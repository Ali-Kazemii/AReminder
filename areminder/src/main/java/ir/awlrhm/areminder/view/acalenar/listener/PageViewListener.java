package ir.awlrhm.areminder.view.acalenar.listener;

import ir.awlrhm.areminder.view.acalenar.PersianHorizontalCalendar;

import org.joda.time.DateTime;

/**
 * Created by Tasnim on 11/12/2017.
 */

public interface PageViewListener {
    PersianHorizontalCalendar onDayClick(DateTime dateTime);
}
