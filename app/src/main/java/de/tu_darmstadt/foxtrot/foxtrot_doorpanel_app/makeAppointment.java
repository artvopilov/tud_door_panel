package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class makeAppointment extends AppCompatActivity {

    private WeekViewEvent activeEvent = null;
    private int slotColor = 0x77770000;
    private int activeSlotColor = 0xFF550000;
    private List<WeekViewEvent> events;
    private  boolean nameEdited = false;
    private  boolean mailEdited = false;
    private  boolean messageEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        events = new ArrayList<WeekViewEvent>();
        WeekViewEvent testEvent = new WeekViewEvent();
        testEvent.setStartTime(Calendar.getInstance());
        testEvent.setName("free slot");
        Calendar end = Calendar.getInstance();
        end.add(Calendar.HOUR,1);
        testEvent.setEndTime(end);
        testEvent.setColor(getResources().getColor(R.color.colorSlot));
        events.add(testEvent);
        WeekViewEvent testEvent2 = new WeekViewEvent();
        Calendar start2 = Calendar.getInstance();
        start2.add(Calendar.HOUR,2);
        testEvent2.setStartTime(start2);
        testEvent2.setName("free slot");
        Calendar end2 = Calendar.getInstance();
        end2.add(Calendar.HOUR,3);
        testEvent2.setEndTime(end2);
        testEvent2.setColor(getResources().getColor(R.color.colorSlot));
        events.add(testEvent2);

        WeekViewEvent testEvent3 = new WeekViewEvent();
        Calendar start3 = Calendar.getInstance();
        start3.add(Calendar.DATE,1);
        testEvent3.setStartTime(start3);
        testEvent3.setName("team meeting");
        Calendar end3 = Calendar.getInstance();
        end3.add(Calendar.HOUR,1);
        end3.add(Calendar.DATE,1);
        testEvent3.setEndTime(end3);
        testEvent3.setColor(getResources().getColor(R.color.colorEvent));
        events.add(testEvent3);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setEnabled(false);

        // Get a reference for the week view in the layout.
        final WeekView mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {


                if (event.getColor()==getResources().getColor(R.color.colorSlot)) {

                    if (!(activeEvent == null)) {
                        activeEvent.setColor(getResources().getColor(R.color.colorSlot));
                    }
                    activeEvent = event;
                    activeEvent.setColor(getResources().getColor(R.color.colorSlotActive));
                    mWeekView.notifyDatasetChanged();


                    if (nameEdited && mailEdited && messageEdited) {

                        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                        fab.setEnabled(true);
                    }

                }



            }
        });

        ((EditText) findViewById(R.id.editName)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                nameEdited = true;
                Log.v("DoorPanel", "name edited" );
                if (activeEvent != null && mailEdited && messageEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        ((EditText) findViewById(R.id.editEMail)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mailEdited = true;
                Log.v("DoorPanel", "mail edited" );
                if (activeEvent != null && nameEdited && messageEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        ((EditText) findViewById(R.id.editMessage)).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                messageEdited = true;
                Log.v("DoorPanel", "message edited" );
                if (activeEvent != null && mailEdited && nameEdited){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSlotActive)));
                    fab.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> theseEvents = new ArrayList<WeekViewEvent>();

                for (WeekViewEvent event: events) {
                    if (event.getStartTime().get(Calendar.YEAR)==newYear && event.getStartTime().get(Calendar.MONTH)==newMonth){
                        theseEvents.add(event);
                    }
                }

                return theseEvents;
            }
        });

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });


    }

}
