package com.example.androiddevelopment.glumcilegende.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BBLOJB on 21.12.2017..
 */

public class MyService {

    /**
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    public static Retrofit getRetrofitInstance(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServiceContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static MyApiEndpointInterface apiInterface(){
        MyApiEndpointInterface apiService = getRetrofitInstance().create(MyApiEndpointInterface.class);

        return apiService;
    }
}
