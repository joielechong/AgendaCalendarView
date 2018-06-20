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
import org.greenrobot.eventbus.EventBus;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeekViewHolder> {

  private static final String TAG = WeeksAdapter.class.getSimpleName();

  private static final long FADE_DURATION = 250;

  private Context mContext;
  private Calendar mToday;
  private List<IWeekItem> mWeeksList = new ArrayList<>();
  private boolean mDragging;
  private boolean mAlphaSet;
  private int mDayTextColor, mPastDayTextColor, mCurrentDayColor;

  public WeeksAdapter(Context context, Calendar today, int dayTextColor, int currentDayTextColor,
      int pastDayTextColor) {
    this.mToday = today;
    this.mContext = context;
    this.mDayTextColor = dayTextColor;
    this.mCurrentDayColor = currentDayTextColor;
    this.mPastDayTextColor = pastDayTextColor;
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
    weekViewHolder.bindWeek(weekItem, mToday);
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
      setUpChildren(daysContainer);
    }

    private void bindWeek(IWeekItem weekItem, Calendar today) {
      Log.d(TAG, "bindWeek called");
      setUpMonthOverlay();
      //if(true) return;

      List<IDayItem> dayItems = weekItem.getDayItems();
      Log.d(TAG, "dayItems = " + dayItems.size());

      for (int c = 0; c < dayItems.size(); c++) {
        final IDayItem dayItem = dayItems.get(c);
        RelativeLayout cellItem = mCells.get(c);
        //TextView tvMonth = cellItem.findViewById(R.id.view_day_month_label);
        //View circleView = cellItem.findViewById(R.id.view_day_circle_selected);
        //TextView tvDay = cellItem.findViewById(R.id.view_day_day_label);
        //EventIndicatorView indicatorView = cellItem.findViewById(R.id.view_day_event_indicator_eiv);
        TextView tvMonth = (TextView) cellItem.getChildAt(0);
        View circleView = cellItem.getChildAt(1);
        TextView tvDay = (TextView) cellItem.getChildAt(2);
        EventIndicatorView indicatorView = (EventIndicatorView) cellItem.getChildAt(3);

        // show event indicator
        //int totalIndicator = eventPerDay(dayItem);
        indicatorView.setIndicatorAmount(dayItem.getEventTotal());

        cellItem.setOnClickListener(v -> {
          Log.d(TAG, "Clicked item");
          EventBus.getDefault().post(new DayClickedEvent(dayItem));
        });
        tvMonth.setVisibility(View.GONE);
        tvDay.setTextColor(mDayTextColor);
        tvMonth.setTextColor(mDayTextColor);
        circleView.setVisibility(View.GONE);

        tvDay.setTypeface(null, Typeface.NORMAL);
        tvMonth.setTypeface(null, Typeface.NORMAL);

        // Display the day
        tvDay.setText(Integer.toString(dayItem.getValue()));

        // Highlight first day of the month
        if (dayItem.isFirstDayOfTheMonth() && !dayItem.isSelected()) {
          tvMonth.setVisibility(View.VISIBLE);
          tvMonth.setText(dayItem.getMonth());
          tvDay.setTypeface(null, Typeface.BOLD);
          tvMonth.setTypeface(null, Typeface.BOLD);
        }

        // Check if this day is in the past
        if (today.getTime().after(dayItem.getDate()) && !DateHelper.sameDate(today,
            dayItem.getDate())) {
          tvDay.setTextColor(mPastDayTextColor);
          tvMonth.setTextColor(mPastDayTextColor);
        }

        // Highlight the cell if this day is today
        if (dayItem.isToday() && !dayItem.isSelected()) {
          tvDay.setTextColor(mCurrentDayColor);
        }

        // Show a circle if the day is selected
        if (dayItem.isSelected()) {
          tvDay.setTextColor(mDayTextColor);
          circleView.setVisibility(View.VISIBLE);
          GradientDrawable drawable = (GradientDrawable) circleView.getBackground();
          drawable.setStroke((int) (1 * Resources.getSystem().getDisplayMetrics().density),
              mDayTextColor);
        }

        // Check if the month label has to be displayed
        if (dayItem.getValue() == 15) {
          mTvMonth.setVisibility(View.VISIBLE);
          SimpleDateFormat monthDateFormat =
              new SimpleDateFormat(mContext.getResources().getString(R.string.month_name_format),
                  CalendarManager.getInstance().getLocale());
          String month = monthDateFormat.format(weekItem.getDate()).toUpperCase();
          if (today.get(Calendar.YEAR) != weekItem.getYear()) {
            month = month + String.format(" %d", weekItem.getYear());
          }
          mTvMonth.setText(month);
        }
      }
    }

    private void setUpChildren(LinearLayout daysContainer) {
      mCells = new ArrayList<>();
      for (int i = 0; i < daysContainer.getChildCount(); i++) {
        mCells.add((RelativeLayout) daysContainer.getChildAt(i));
      }
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
