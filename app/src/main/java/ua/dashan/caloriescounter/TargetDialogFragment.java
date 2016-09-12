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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.ganfra.materialspinner.MaterialSpinner;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;

public class TargetDialogFragment extends DialogFragment {
Button okButton,cancelButton;
    MaterialSpinner lifeStyleSpinner, targetSpinner,sexSpinner;
    DatabaseHelpher helpher;
EditText userTarget;
//https://github.com/ganfra/MaterialSpinner

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.target,container,false);
      //  getDialog().setTitle("Ваша цель");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        userTarget=(EditText)v.findViewById(R.id.userTarget);
        okButton=(Button)v.findViewById(R.id.okButton);
        helpher=new DatabaseHelpher(getActivity());
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int target = Integer.parseInt(userTarget.getText().toString());
                helpher.insertTargetIntoDB(target);
                CaloriesCounterFragment.targetET.setText(target+" каллорий");
                dismiss();
            }
        });
        cancelButton=(Button)v.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        String[] ITEMS = {"Минимум или отсуствие нагрузки", "3 раза в неделю", "5 раз в неделю", "Каждый день","Ежедневная физ. нагрузка + физ. работа"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifeStyleSpinner = (MaterialSpinner)v.findViewById(R.id.lifeStyleSpinner);
        lifeStyleSpinner.setAdapter(adapter);
        lifeStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "Position= "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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
