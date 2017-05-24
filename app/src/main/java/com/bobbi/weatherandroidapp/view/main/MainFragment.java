package com.bobbi.weatherandroidapp.view.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobbi.weatherandroidapp.R;
import com.bobbi.weatherandroidapp.data.Weather;
import com.bobbi.weatherandroidapp.util.UnitLocale;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements MainContract.View{
    @Bind(R.id.textview_main_city)
    TextView textview_main_city;
    @Bind(R.id.textView_main_conditions)
    TextView textView_main_conditions;
    @Bind(R.id.textView_main_current_temperature)
    TextView textView_main_current_temperature;
    @Bind(R.id.imageView_main_icon)
    ImageView imageView_main_icon;

    private MainContract.Presenter mPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showWeather(Weather weather) {
        textview_main_city.setText(weather.getName());
        textView_main_current_temperature.setText(String.format("%.1f°", weather.getMain().getTemp()));
        textView_main_conditions.setText(weather.getWeather().get(0).getDescription());
        imageView_main_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), getIcon(weather.getWeather().get(0).getId())));
    }

    private static int getIcon(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.mipmap.ic_thunderstorm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.mipmap.ic_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.mipmap.ic_rain;
        } else if (weatherId == 511) {
            return R.mipmap.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.mipmap.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.mipmap.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.mipmap.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.mipmap.ic_thunderstorm;
        } else if (weatherId == 800) {
            return R.mipmap.ic_clear;
        } else if (weatherId == 801) {
            return R.mipmap.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.mipmap.ic_cloudy;
        }
        return -1;
    }
}
