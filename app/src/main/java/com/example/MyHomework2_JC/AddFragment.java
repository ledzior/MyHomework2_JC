package com.example.MyHomework2_JC;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;



public class AddFragment extends Fragment {

    private View view;
    private OnAddInteractionListener mListener;
    private TextInputLayout inputTitle, inputDirector, inputPremiere;
    private TextInputEditText editTitle, editDirector, editPremiere;
    public AddFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_add, container, false);
        inputTitle = view.findViewById(R.id.titleLayout);
        inputDirector = view.findViewById(R.id.directorLayout);
        inputPremiere = view.findViewById(R.id.premiereLayout);

        editTitle = view.findViewById(R.id.titleEdit);
        editDirector = view.findViewById(R.id.directorEdit);
        editPremiere = view.findViewById(R.id.premiereEdit);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editDirector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPremiere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnAddInteractionListener) {
            mListener = (OnAddInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Film film = new Film(MainActivity.filmList.size(), editDirector.getText().toString(), editTitle.getText().toString(), editPremiere.getText().toString(),  0, "basketball_ante");
                film.setImage();
                mListener.add(film);
            }
        });
    }

    public interface OnAddInteractionListener {
        void add(Film film);
    }

}


