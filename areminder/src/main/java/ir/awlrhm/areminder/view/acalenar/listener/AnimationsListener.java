package ir.awlrhm.areminder.view.acalenar.listener;

import android.view.View;

import ir.awlrhm.areminder.view.acalenar.PersianHorizontalCalendar;
import ir.awlrhm.areminder.view.acalenar.enums.PersianViewPagerType;

import org.joda.time.DateTime;

/**
 * Created by Tasnim on 11/12/2017.
 */

public interface AnimationsListener {
    PersianHorizontalCalendar setHeightToCenterContainer(int height);

    PersianHorizontalCalendar setTopMarginToAnimContainer(int margin);

    PersianHorizontalCalendar setWeekPagerVisibility(int visibility);

    PersianHorizontalCalendar setMonthPagerVisibility(int visibility);

    PersianHorizontalCalendar setAnimatedContainerVisibility(int visibility);

    PersianHorizontalCalendar setMonthPagerAlpha(float alpha);

    PersianHorizontalCalendar setWeekPagerAlpha(float alpha);

    PersianHorizontalCalendar scrollToDate(DateTime dateTime, boolean scrollMonthPager, boolean scrollWeekPager, boolean animate);

    PersianHorizontalCalendar animateContainerAddView(View view);

    PersianHorizontalCalendar animateContainerRemoveViews();

    PersianHorizontalCalendar updateMarks();

    PersianHorizontalCalendar changeViewPager(PersianViewPagerType persianViewPagerType);
}
