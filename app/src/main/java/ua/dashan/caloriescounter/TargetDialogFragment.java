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
private Button okButton,cancelButton;
private    MaterialSpinner lifeStyleSpinner, targetSpinner,sexSpinner;
   private DatabaseHelpher helpher;
private EditText userTarget,weight,size,age;
 private    double physLoadFactor;
    private double percentOfWeightChange;
    private double formula;
    private int weightNumber,ageNumber,sizeNumber;
    private int sex;
//https://github.com/ganfra/MaterialSpinner

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.target,container,false);
      //  getDialog().setTitle("Ваша цель");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        userTarget=(EditText)v.findViewById(R.id.userTarget);
        okButton=(Button)v.findViewById(R.id.okButton);

        weight=(EditText)v.findViewById(R.id.weight);
        size=(EditText)v.findViewById(R.id.size);
        age=(EditText)v.findViewById(R.id.age);





        helpher=new DatabaseHelpher(getActivity());

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
               // Toast.makeText(getActivity(), "Position= "+position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case -1:
                        physLoadFactor=0.0;
                        break;
                    case 0:
                       physLoadFactor=1.2;
                        break;
                    case 1:
                        physLoadFactor=1.375;
                        break;
                    case 2:
                        physLoadFactor=1.4625;
                        break;
                    case 3:
                        physLoadFactor=1.6375;
                        break;
                    case 4:
                        physLoadFactor=1.9;
                        break;

                }
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
        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case -1:
                        percentOfWeightChange=0.0;
                        break;
                    case 0:
                        percentOfWeightChange=0.85;
                        break;
                    case 1:
                        percentOfWeightChange=1.0;
                        break;
                    case 2:
                        percentOfWeightChange=1.15;
                        break;
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] ITEMS3 = {"Мужчина", "Женщина"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner = (MaterialSpinner)v.findViewById(R.id.sexSpinner);
        sexSpinner.setAdapter(adapter3);
       sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               switch (position){
                   case -1:
                       sex=0;
                       break;
                   case 0:
                       // для мужчин 10 х вес (кг) + 6,25 х высоту (см) – 5 х возраст (лет) + 5
                       //для женщин 655.1 + 9.563 х вес (кг) + 1.85 х рост (см) — 4.676 х возраст (лет)
                      //formula=(10*weightNumber+6.25*sizeNumber-5*ageNumber+5)*physLoadFactor*percentOfWeightChange;
                       sex=1;
                       break;
                   case 1:
                       //formula=(655.1+9.564*weightNumber+1.85*sizeNumber-4.676*ageNumber)*physLoadFactor*percentOfWeightChange;
                       sex=2;
                       break;
           }}

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userTarget.getText().toString().equals("")&& !userTarget.getText().toString().startsWith("0") ){

                    int target = Integer.parseInt(userTarget.getText().toString());
                    if(target>0){

                        helpher.insertTargetIntoDB(target);
                        CaloriesCounterFragment.targetET.setText(target+" каллорий");
                        CaloriesCounterFragment.waveProgressbar.setMaxProgress(target);
                        dismiss();}}


                else if((weight.getText().toString().equals("")||size.getText().toString().equals("")||age.getText().toString().equals("")||physLoadFactor==0||
                       percentOfWeightChange==0||sex==0||weight.getText().toString().startsWith("0")||size.getText().toString().startsWith("0")
                ||age.getText().toString().startsWith("0"))&&(userTarget.getText().toString().equals("")||userTarget.getText().toString().startsWith("0")) ){
                   Toast.makeText(getActivity(),"чтото не так(",Toast.LENGTH_SHORT).show();

               }
                   else  if(/*!weight.getText().toString().equals("")||!size.getText().toString().equals("")||!age.getText().toString().equals("")||physLoadFactor!=0.0||
                           percentOfWeightChange!=0.0||sex!=0&&*/userTarget.getText().toString().equals("") ){
                       if(sex==1){//для мужчин
                           weightNumber=Integer.parseInt(weight.getText().toString());
                           ageNumber=Integer.parseInt(age.getText().toString());
                           sizeNumber=Integer.parseInt(size.getText().toString());
                           formula=(10*weightNumber+6.25*sizeNumber-5*ageNumber+5)*physLoadFactor*percentOfWeightChange;
                       int compCountTarget=(int)formula;
                       helpher.insertTargetIntoDB(compCountTarget);
                       CaloriesCounterFragment.targetET.setText(compCountTarget+" каллорий");
                           CaloriesCounterFragment.waveProgressbar.setMaxProgress(compCountTarget);
                       dismiss();
                   }
                       if(sex==2){//для женщин
                           weightNumber=Integer.parseInt(weight.getText().toString());
                           ageNumber=Integer.parseInt(age.getText().toString());
                           sizeNumber=Integer.parseInt(size.getText().toString());
                           formula=(655.1+9.564*weightNumber+1.85*sizeNumber-4.676*ageNumber)*physLoadFactor*percentOfWeightChange;
                           int compCountTarget=(int)formula;
                           helpher.insertTargetIntoDB(compCountTarget);
                           CaloriesCounterFragment.targetET.setText(compCountTarget+" каллорий");
                           CaloriesCounterFragment.waveProgressbar.setMaxProgress(compCountTarget);
                           dismiss();
                       }}

            }
        });
        return v;
    }



}
