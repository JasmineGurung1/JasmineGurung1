package com.example.drugstoreskincare.singleProductPage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.Fragments.home.adapters.SliderAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.api.response.RegisterResponse;
import com.example.drugstoreskincare.api.response.Slider;
import com.example.drugstoreskincare.utils.SharedPrefUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SingleProductActivity extends AppCompatActivity {
    public static String key = "pKey";
    Product product;
    SliderView imageSlider;
    ProgressBar addingCartPR;
    ImageView backIV, plusIV, minusIV;
    TextView name, price, desc, oldPrice, quantityTV;
    LinearLayout addToCartLL;
    int quantity = 1;
    boolean isAdding = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);


        setContentView(R.layout.activity_single_product);
        backIV = findViewById(R.id.backIV);
        imageSlider = findViewById(R.id.imageSlider);
        name = findViewById(R.id.productNameTV);
        price = findViewById(R.id.productPriceTV);
        quantityTV = findViewById(R.id.quantityTV);
        oldPrice = findViewById(R.id.oldPriceTV);
        addToCartLL = findViewById(R.id.addToCartLL);
        addingCartPR = findViewById(R.id.addingCartPR);
        desc = findViewById(R.id.decTV);
        plusIV = findViewById(R.id.plusIV);
        minusIV = findViewById(R.id.minusIV);
        setOnclickListners();
        if (getIntent().getSerializableExtra(key) != null) {
            product = (Product) getIntent().getSerializableExtra(key);
            setProduct(product);
        }

    }

    private void setOnclickListners() {

           backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    private void setProduct(Product product) {
        setSliders(product.getImages());
        name.setText(product.getName());
        Toast.makeText(getApplicationContext(),"hello ",Toast.LENGTH_LONG).show();

        if (product.getDiscountPrice() == 0 || product.getDiscountPrice() == null){
            price.setText("Rs." + product.getPrice());
            oldPrice.setVisibility(View.VISIBLE);
        }else{
            price.setText("Rs." + product.getDiscountPrice());
            oldPrice.setText("Rs." + product.getPrice());

        }
        desc.setText(product.getDescription());
    }



    private void setSliders(List<String> images) {
        List<Slider> sliders = new ArrayList<>();
        for (int i = 0; i<images.size(); i++){
            Slider slider = new Slider();
            slider.setImage(images.get(i));
            sliders.add(slider);

        }
        SliderAdapter sliderAdapter = new SliderAdapter(sliders,this);
        sliderAdapter.setClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onSliderClick(int position, Slider slider) {

            }
        });
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);

    }

}
