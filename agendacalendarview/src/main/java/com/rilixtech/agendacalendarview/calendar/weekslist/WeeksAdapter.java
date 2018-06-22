package com.rilixtech.agendacalendarview.calendar.weekslist;

import android.util.Log;
import android.widget.RelativeLayout;
import com.rilixtech.agendacalendarview.CalendarManager;
import com.rilixtech.agendacalendarview.R;
import com.rilixtech.agendacalendarview.models.IDayItem;
import com.rilixtech.agendacalendarview.models.IWeekItem;
import com.rilixtech.agendacalendarview.utils.DateHelper;
import com.rilixtech.agendacalendarview.event.DayClickedEvent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rilixtech.agendacalendarview.widgets.EventIndicatorView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeekViewHolder> {

  private static final String TAG = WeeksAdapter.class.getSimpleName();

  private static final long FADE_DURATION = 250;

  private Context mContext;
  private Calendar mCalendarToday;
  private List<IWeekItem> mWeeksList = new ArrayList<>();
  private boolean mDragging;
  private boolean mAlphaSet;
  private int mDayTextColor, mPastDayTextColor, mCurrentDayColor;

  // this always reuse, so we need to make it a class scope.
  private CalendarManager mCalendarManager;
  private Locale mCalendarManagerLocale;
  SimpleDateFormat mSdfMonthDateFormat;

  public WeeksAdapter(Context context, Calendar today, int dayTextColor, int currentDayTextColor,
      int pastDayTextColor) {
    this.mCalendarToday = today;
    this.mContext = context;
    this.mDayTextColor = dayTextColor;
    this.mCurrentDayColor = currentDayTextColor;
    this.mPastDayTextColor = pastDayTextColor;

    configureByCalendarManager();
  }

  private void configureByCalendarManager() {
    mCalendarManager = CalendarManager.getInstance();
    mCalendarManagerLocale = mCalendarManager.getLocale();
    mSdfMonthDateFormat =
        new SimpleDateFormat(mContext.getResources().getString(R.string.month_name_format),
            mCalendarManagerLocale);
  }

  public void updateWeeksItems(List<IWeekItem> weekItems) {
    this.mWeeksList.clear();
    this.mWeeksList.addAll(weekItems);
    notifyDataSetChanged();
  }

  public List<IWeekItem> getWeeksList() {
    return mWeeksList;
  }

  public boolean isDragging() {
    return mDragging;
  }

  public void setDragging(boolean dragging) {
    if (dragging != mDragging) {
      mDragging = dragging;
      notifyItemRangeChanged(0, mWeeksList.size());
    }
  }

  public boolean isAlphaSet() {
    return mAlphaSet;
  }

  public void setAlphaSet(boolean alphaSet) {
    mAlphaSet = alphaSet;
  }

  @Override public WeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.list_item_week, parent, false);
    return new WeekViewHolder(view);
  }

  @Override public void onBindViewHolder(WeekViewHolder weekViewHolder, int position) {
    IWeekItem weekItem = mWeeksList.get(position);
    weekViewHolder.bindWeek(weekItem, mCalendarToday);
  }

  @Override public int getItemCount() {
    return mWeeksList.size();
  }

  class WeekViewHolder extends RecyclerView.ViewHolder {

    private List<RelativeLayout> mCells;
    private TextView mTvMonth;
    private FrameLayout mMonthBackground;

    public WeekViewHolder(View itemView) {
      super(itemView);
      mTvMonth = itemView.findViewById(R.id.month_label);
      mMonthBackground = itemView.findViewById(R.id.month_background);
      LinearLayout daysContainer = itemView.findViewById(R.id.week_days_container);
      mCells = new ArrayList<>();
      for (int i = 0; i < daysContainer.getChildCount(); i++) {
        mCells.add((RelativeLayout) daysContainer.getChildAt(i));
      }
    }

    private TextView tvWeekMonth;
    private View vWeekCircleView;
    private TextView tvWeekDay;
    private EventIndicatorView eivWeekIndicatorView;

    private void bindWeek(IWeekItem weekItem, Calendar today) {
      Log.d(TAG, "bindWeek called");

      setUpMonthOverlay();

      List<IDayItem> dayItems = weekItem.getDayItems();
      Log.d(TAG, "dayItems = " + dayItems.size());

      for (int c = 0; c < dayItems.size(); c++) {
        final IDayItem dayItem = dayItems.get(c);
        RelativeLayout cellItem = mCells.get(c);
        if (dayItem.isWeekend()) {
          cellItem.setBackgroundColor(mCalendarManager.getWeekendsColor());
        } else {
          cellItem.setBackgroundColor(dayItem.getColor());
        }

        tvWeekMonth = (TextView) cellItem.getChildAt(0);
        vWeekCircleView = cellItem.getChildAt(1);
        tvWeekDay = (TextView) cellItem.getChildAt(2);
        eivWeekIndicatorView = (EventIndicatorView) cellItem.getChildAt(3);

        tvWeekMonth.setVisibility(View.GONE);
        tvWeekDay.setTextColor(mDayTextColor);
        tvWeekMonth.setTextColor(mDayTextColor);
        vWeekCircleView.setVisibility(View.GONE);

        // show event indicator
        eivWeekIndicatorView.setIndicatorAmount(dayItem.getEventTotal());

        cellItem.setOnClickListener(v -> {
          Log.d(TAG, "Clicked item");
          EventBus.getDefault().post(new DayClickedEvent(dayItem));
        });

        // Display the day
        tvWeekDay.setText(String.format(mCalendarManagerLocale, "%d", dayItem.getValue()));

        // Highlight first day of the month
        if (dayItem.isFirstDayOfTheMonth() && !dayItem.isSelected()) {
          highlightFirstDayOfMonth(dayItem);
        }

        // Check if this day is in the past
        if (today.getTime().after(dayItem.getDate()) && !DateHelper.sameDate(today, dayItem.getDate())) {
          tvWeekDay.setTextColor(mPastDayTextColor);
          tvWeekMonth.setTextColor(mPastDayTextColor);
        }

        // Highlight the cell if this day is today
        if (dayItem.isToday() && !dayItem.isSelected()) {
          tvWeekDay.setTextColor(mCurrentDayColor);
        }

        // Show a circle if the day is selected
        if (dayItem.isSelected()) showSelectedDayCircle();

        // Check if the month label has to be displayed
        if (dayItem.getValue() == 15) displayMonthLabel(weekItem);
      }
    }

    private void highlightFirstDayOfMonth(IDayItem dayItem) {
      tvWeekMonth.setVisibility(View.VISIBLE);
      tvWeekMonth.setText(dayItem.getMonth());
      tvWeekDay.setTypeface(null, Typeface.BOLD);
      tvWeekMonth.setTypeface(null, Typeface.BOLD);
    }

    private void showSelectedDayCircle() {
      tvWeekDay.setTextColor(mDayTextColor);
      vWeekCircleView.setVisibility(View.VISIBLE);
      GradientDrawable drawable = (GradientDrawable) vWeekCircleView.getBackground();
      drawable.setStroke((int) (1 * Resources.getSystem().getDisplayMetrics().density),
          mDayTextColor);
    }
    private void displayMonthLabel(IWeekItem weekItem) {
      mTvMonth.setVisibility(View.VISIBLE);
      String month = mSdfMonthDateFormat.format(weekItem.getDate()).toUpperCase();
      if (mCalendarToday.get(Calendar.YEAR) != weekItem.getYear()) {
        month = month + String.format(" %d", weekItem.getYear());
      }
      mTvMonth.setText(month);
    }

    private void setUpMonthOverlay() {
      mTvMonth.setVisibility(View.GONE);

      if (isDragging()) {
        AnimatorSet animatorSetFadeIn = new AnimatorSet();
        animatorSetFadeIn.setDuration(FADE_DURATION);
        ObjectAnimator animatorTxtAlphaIn =
            ObjectAnimator.ofFloat(mTvMonth, "alpha", mTvMonth.getAlpha(), 1f);
        //ObjectAnimator animatorBackgroundAlphaIn =
        //    ObjectAnimator.ofFloat(mMonthBackground, "alpha", mMonthBackground.getAlpha(), 1f);
        animatorSetFadeIn.playTogether(animatorTxtAlphaIn
            //animatorBackgroundAlphaIn
        );
        animatorSetFadeIn.addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {
            setAlphaSet(true);
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        });
        animatorSetFadeIn.start();
      } else {
        AnimatorSet animatorSetFadeOut = new AnimatorSet();
        animatorSetFadeOut.setDuration(FADE_DURATION);
        ObjectAnimator animatorTxtAlphaOut =
            ObjectAnimator.ofFloat(mTvMonth, "alpha", mTvMonth.getAlpha(), 0f);
        //ObjectAnimator animatorBackgroundAlphaOut =
        //    ObjectAnimator.ofFloat(mMonthBackground, "alpha", mMonthBackground.getAlpha(), 0f);
        animatorSetFadeOut.playTogether(animatorTxtAlphaOut
            //animatorBackgroundAlphaOut
        );
        animatorSetFadeOut.addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {
            setAlphaSet(false);
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        });
        animatorSetFadeOut.start();
      }

      if (isAlphaSet()) {
        mTvMonth.setAlpha(1f);
      } else {
        mTvMonth.setAlpha(0f);
      }
    }
  }
}
