package pl.school.apkaszkolna.services;

import org.springframework.stereotype.Service;
import java.util.Calendar;

@Service
public class Dzis {

    private Calendar now;

    public Dzis() {
        this.now = Calendar.getInstance();
    }

    public String getDate(){
        String dzien = now.get(Calendar.DAY_OF_MONTH)+"."+(now.get(Calendar.MONTH)+1)+"."+now.get(Calendar.YEAR);
        return dzien;
    }

}