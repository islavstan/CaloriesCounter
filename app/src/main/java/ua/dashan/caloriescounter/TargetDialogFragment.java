package ua.dashan.caloriescounter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;

import fr.ganfra.materialspinner.MaterialSpinner;

public class TargetDialogFragment extends DialogFragment {
Button okButton,cancelButton;
    MaterialSpinner lifeStyleSpinner;
    MaterialSpinner targetSpinner;
    MaterialSpinner sexSpinner;
//https://github.com/ganfra/MaterialSpinner

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.target,container,false);
      //  getDialog().setTitle("Ваша цель");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        okButton=(Button)v.findViewById(R.id.okButton);
        cancelButton=(Button)v.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        String[] ITEMS = {"Малая активность", "Средняя активность", "Высокая активность"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifeStyleSpinner = (MaterialSpinner)v.findViewById(R.id.lifeStyleSpinner);
        lifeStyleSpinner.setAdapter(adapter);

        String[] ITEMS2 = {"Похудение", "Удержание веса", "Набор массы"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpinner = (MaterialSpinner)v.findViewById(R.id.targetSpinner);
        targetSpinner.setAdapter(adapter2);


        String[] ITEMS3 = {"Мужчина", "Женщина"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner = (MaterialSpinner)v.findViewById(R.id.sexSpinner);
        sexSpinner.setAdapter(adapter3);


        return v;
    }



}
