package com.example.irvinetaste;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zhangzhenyu
 * @date 2021/2/23 下午1:59
 */

public interface RestaurantApiService {
    @Headers("Authorization:Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx")
    @GET("businesses/search")
    public Call<NearbyRestaurants> getRestaurants(@Query("term") String term, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") int radius,
                                           @Query("categories") String categories, @Query("limit") int limit, @Query("offset") int offset, @Query("price") String price,
                                           @Query("open_now") Boolean openNow);


    @Headers("Authorization:Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx")
    @GET("businesses/search")
    public Call<NearbyRestaurants> getRecommend( @Query("latitude") double latitude, @Query("longitude") double longitude);

    // search term and location(default: irvine)
    // https://api.yelp.com/v3/businesses/search?location=irvine&term=delis
    @Headers("Authorization:Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx")
    @GET("businesses/search")
    public Call<SearchRestaurantResponse> getRestaurantsByLocation(@Query("term") String term, @Query("location") String location, @Query("sort_by") String sort_by);

    @Headers("Authorization:Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx")
    @GET("businesses/{id}")
    public Call<Restaurant> getRestaurantByID(@Path("id") String id);

}
