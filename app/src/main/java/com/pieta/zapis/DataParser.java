package com.pieta.zapis;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class DataParser {

    public static HashMap<String, Long> parse(String[] s, String param){
        Data[] datas = new Data[s.length];
        for(int i=0; i<s.length; i++){
            String[] tmp = s[i].split(",");
            Data d =new Data(tmp[0], Long.parseLong(tmp[1]), Integer.parseInt(tmp[2]),
                             Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]));
            datas[i] = d;
        }

        LocalDate localDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        ArrayList<Data> correctDate = new ArrayList<>();
        switch (param){
            case "7days":
                day -=7;
                if(day<=0)
                    day=30+day;

                for(Data d : datas){
                    if(d.getDay()>=day)correctDate.add(d);
                }
                break;
            case "month":
                for(Data d : datas){
                    if(d.getMonth()==month)correctDate.add(d);
                }
                break;
            case "year":
                for(Data d : datas){
                    if(d.getYear()==year)correctDate.add(d);
                }
                break;
        }

        HashMap<String, Long> result = new HashMap<>();
        Iterator<Data> it = correctDate.iterator();
        while(it.hasNext()){
            Data d = it.next();
            result.put(d.getName(), d.getTime());
        }

        return result;
    }
}
