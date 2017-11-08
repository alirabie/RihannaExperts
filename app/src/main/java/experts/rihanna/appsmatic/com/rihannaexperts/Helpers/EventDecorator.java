package experts.rihanna.appsmatic.com.rihannaexperts.Helpers;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Date;


/**
 * Created by Eng Ali on 11/5/2017.
 */
public class EventDecorator implements DayViewDecorator {
    private  int color;

    private  int day ;
    private  int month;
    private  int year;

    public EventDecorator(int color, int day, int month, int year) {
        this.color = color;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.getDay()==this.day&&(day.getMonth()+1)==this.month&day.getYear()==this.year;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(6, color));
    }
}
