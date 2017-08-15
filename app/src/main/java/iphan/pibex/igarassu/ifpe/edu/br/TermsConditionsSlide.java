package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import agency.tango.materialintroscreen.SlideFragment;

/**
 * Created by gabri on 15/08/2017.
 */

public class TermsConditionsSlide extends SlideFragment {

    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_conditions_slide, container, false);
    }

    @Override
    public boolean canMoveFurther() {
        checkBox = (CheckBox) getActivity().findViewById(R.id.cb_concordo);
        if(checkBox.isChecked()){
            SharedPref.updateIntroStatus(getContext(), true);

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent);
            getActivity().finish();

        }
        return checkBox.isChecked();

    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getActivity().getResources().getString(R.string.slide_4_checkbox_error);
    }

    @Override
    public int backgroundColor() {
        return R.color.slide_4;
    }

    @Override
    public int buttonsColor() {
        return R.color.slide_button;
    }
}
