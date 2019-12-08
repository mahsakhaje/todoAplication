package com.example.hw9;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hw9.Repositories.UserRepository;
import com.google.android.material.snackbar.Snackbar;

import model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public static final int REQUEST_CODE = 1;
    public static final String USER = "user";
    Button mLogin, mSignUp;
    boolean setInSignUp = false;
    EditText mUserName, mPassword;
    String inputUser;
    String inputPass;
    String inputUserSended, inputPassSended;
    boolean UserSigndUp;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mLogin = v.findViewById(R.id.loginBTN);
        mSignUp = v.findViewById(R.id.signUpBTN);
        mUserName = v.findViewById(R.id.userNameET);
        mPassword = v.findViewById(R.id.passWordET);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mUserName.getText().toString().isEmpty() || !mPassword.getText().toString().isEmpty()) {
                    inputUser = mUserName.getText().toString();
                    inputPass = mPassword.getText().toString();
                    SignUpFragment signUpFragment = SignUpFragment.newInstance(inputUser, inputPass);
                    signUpFragment.setTargetFragment(LoginFragment.this, REQUEST_CODE);
                    signUpFragment.show(getFragmentManager(), "tag");

                } else {
                    SignUpFragment signUpFragment = SignUpFragment.newInstance(null, null);
                    signUpFragment.setTargetFragment(LoginFragment.this, REQUEST_CODE);
                    signUpFragment.show(getFragmentManager(), "tag");

                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserSigndUp) {
                    if (mUserName.getText().toString().equals(inputUserSended) && mPassword.getText().toString().equals(inputPassSended)) {
                        User user = new User();
                        user.setUserName(inputUserSended);
                        user.setPassword(inputPassSended);
                        UserRepository.getInstance(getActivity()).addUser(user);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra(USER,user);
                        startActivity(intent);

                    } else {
                        Snackbar snackbar = Snackbar.make(view, " incorect userName or passWord!!!! ", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(view, " you should sign up first !!!! ", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return v;
    }

    public void getUserInfo(String userName, String password) {
        UserSigndUp = true;
        inputUserSended = userName;
        inputPassSended = password;
        mUserName.setText(inputUserSended.toString());
        mPassword.setText(inputPassSended.toString());

    }

}
