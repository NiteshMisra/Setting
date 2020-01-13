package com.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Objects;

public class PreferenceFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preference, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Spinner spinner = view.findViewById(R.id.pref_lang_spinner);
        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),R.array.languages,android.R.layout.simple_spinner_item);
        lAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(lAdapter);
        CheckBox checkbox = view.findViewById(R.id.pref_checkBox);
        checkbox.setChecked(true);
    }

}
