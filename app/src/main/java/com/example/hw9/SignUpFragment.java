package com.example.hw9;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends DialogFragment {

    public static final String SENDED_USER_NAME = "sendedUserName";
    public static final String SENDED_PASWORD = "sendedPasword";

    EditText UserName, PassWord;
    String inputUser, inputPassWord;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String userName, String passWord) {

        Bundle args = new Bundle();
        args.putString(SENDED_USER_NAME, userName);
        args.putString(SENDED_PASWORD, passWord);
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sign_up, null, false);
        builder.setView(view);
        initEditTexts(view);

        builder.setPositiveButton(" Sign Up ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginFragment fragment = (LoginFragment) getTargetFragment();
                fragment.getUserInfo(UserName.getText().toString(), PassWord.getText().toString());
            }
        }).setNeutralButton(" back ", null);

        return builder.create();
    }

    public void initEditTexts(View v) {
        UserName = v.findViewById(R.id.editText_userName_signUp);
        PassWord = v.findViewById(R.id.editText_passWord_signUp);
        if (getArguments().getString(SENDED_USER_NAME) != null) {
            UserName.setText(getArguments().getString(SENDED_USER_NAME).toString());
        }
        if (getArguments().getString(SENDED_PASWORD) != null) {
            PassWord.setText(getArguments().getString(SENDED_PASWORD).toString());
        }

    }
}
