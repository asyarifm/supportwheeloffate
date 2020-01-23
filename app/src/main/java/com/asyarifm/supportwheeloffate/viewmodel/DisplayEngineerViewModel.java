package com.asyarifm.supportwheeloffate.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.asyarifm.supportwheeloffate.R;
import com.asyarifm.supportwheeloffate.model.Engineer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayEngineerViewModel extends ViewModel {
    private ArrayList<Engineer> engineerList;

    private String urlString = "https://private-e9d084-supportwheeloffate5.apiary-mock.com/engineers";
    private Context ctx;

    private MutableLiveData<String[]> engineersName;
    private MutableLiveData<String> errorMessage;

    public DisplayEngineerViewModel(Context ctx) {
        this.ctx = ctx;
        engineerList = new ArrayList<>();
        fetchEngineersData();

        engineersName = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public ArrayList<Engineer> getEngineerList() {
        return engineerList;
    }

    private String[] initEngineersName() {
        String[] engineersName = new String[engineerList.size()];
        int index = 0;
        for (Engineer each:engineerList) {
            engineersName[index] = each.getName();
            index++;
        }

        return engineersName;
    }

    public LiveData<String[]> getEngineersName() {
        return engineersName;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void fetchEngineersData() {
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray array = response.getJSONArray("engineers");
                            for(int i=0;i<array.length();i++){
                                JSONObject engineer = array.getJSONObject(i);
                                engineerList.add(new Engineer(engineer.getInt("id"), engineer.getString("name")));
                            }

                            engineersName.postValue(initEngineersName());
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        errorMessage.postValue(ctx.getString(R.string.error_no_internet_connection));
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
