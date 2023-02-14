package com.example.bookaticket;

import androidx.lifecycle.ViewModel;

import com.example.bookaticket.Model.Station;

import java.util.LinkedList;
import java.util.List;

public class HomePageFragmentViewModel extends ViewModel {
    private List<Station> data = new LinkedList<>();

    List<Station> getData(){
        return data;
    }

    void setData(List<Station> list){
        data = list;
    }
}
