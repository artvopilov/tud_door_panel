package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models;

import android.app.Application;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.Worker;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.retrieveEmployee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tablet {
    private int id;
    private String roomNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

}
