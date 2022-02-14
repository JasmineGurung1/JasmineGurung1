package com.example.drugstoreskincare.Home.Fragments.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.Fragments.home.adapters.CategoryAdapter;
import com.example.drugstoreskincare.Home.Fragments.home.adapters.ShopAdapter;
import com.example.drugstoreskincare.Home.Fragments.home.adapters.SliderAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.Category;
import com.example.drugstoreskincare.api.response.CategoryResponse;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.api.response.Slider;
import com.example.drugstoreskincare.api.response.SliderResponse;
import com.example.drugstoreskincare.utils.DataHolder;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView allProductsRV, categoryRV;
    ProgressBar loadingProgress;
    SliderView imageSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductsRV = view.findViewById(R.id.allProductRV);
        categoryRV = view.findViewById(R.id.categoryRV);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        imageSlider = view.findViewById(R.id.imageSlider);
        serverCall();
        getCategoriesOnline();
        getProductOnline();
        getSliders();

    }

    private void getProductOnline() {
        toggleProgress(true);
        Call <AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleProgress(false);
                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                initiateRecyclerView(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initiateRecyclerView(List<Product> products) {
        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext());
        allProductsRV.setAdapter(shopAdapter);

    }

    private void getSliders() {
        Call<SliderResponse> sliderResponseCall = ApiClient.getClient().getSliders();
        sliderResponseCall.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        setSliders(response.body().getSliders());
                        
                    } 
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });


    }
    
    private void setSliders(List<Slider> sliders) {
        SliderAdapter sliderAdapter = new SliderAdapter(sliders, getContext());
        sliderAdapter.setClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onSliderClick(int position, Slider slider) {
                Toast.makeText(getContext(), "from This is item in position " + position, Toast.LENGTH_SHORT).show();
                
            }
        });
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

    }

            

    private void getCategoriesOnline() {
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        DataHolder.categories = response.body().getCategories();
                        showCategories(response.body().getCategories());

                    }
                }
            }



            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });



    }

    private void showCategories(List<Category> categories) {
        List<Category> temp;
        if (categories.size() > 8) {
            temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                temp.add(categories.get(categories.size() - i - 1));
            }
        } else {
            temp = categories;
        }
        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories,getContext(),true);
        categoryRV.setAdapter(categoryAdapter);
    }



    private void serverCall() {
        toggleProgress(true);
        Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleProgress(false);
                setProductRecyclerView(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void toggleProgress(Boolean toggle){
        if (toggle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }

    private void setProductRecyclerView (List<Product> products) {
        allProductsRV.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        allProductsRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext());
        allProductsRV.setAdapter(shopAdapter);
    }
}

