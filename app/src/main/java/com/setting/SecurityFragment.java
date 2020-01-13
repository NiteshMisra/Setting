package com.setting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecurityFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText currentPassword = view.findViewById(R.id.security_current_password);
        final EditText newPassword = view.findViewById(R.id.security_new_password);
        final EditText confirmPassword = view.findViewById(R.id.security_confirm_password);
        Button changeBtn = view.findViewById(R.id.security_change_btn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cuString = currentPassword.getText().toString();
                String nString = newPassword.getText().toString();
                String coString = confirmPassword.getText().toString();
                confirmPassword.setCursorVisible(false);
                newPassword.setCursorVisible(false);
                currentPassword.setCursorVisible(false);

                if (cuString.isEmpty() || nString.isEmpty() || coString.isEmpty() || (!coString.equals(nString))){
                    if (cuString.isEmpty() || nString.isEmpty() || coString.isEmpty()){
                        Toast.makeText(getActivity(),"Field is empty",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),"New Passwords not matched",Toast.LENGTH_LONG).show();
                    }
                }else{
                    if (isInternet()){
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("Updating Password");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        Map<String,String> headers = new HashMap<>();
                        headers.put("User-ID","1");
                        headers.put("Authorization","67nPxwLC/yyGc");
                        RetrofitClient.getInstance().getApi().changesecurity(headers,"1",nString,cuString,coString).enqueue(new Callback<UpdateNameResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<UpdateNameResponse> call, @NonNull Response<UpdateNameResponse> response) {
                                if(response.isSuccessful()){
                                    assert response.body() != null;
                                    if (response.body().getData() != null){
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    assert response.errorBody() != null;
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"error " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<UpdateNameResponse> call, @NonNull Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setMessage("No Internet Connection");
                        dialog.setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create()
                                .show();
                    }
                }
            }
        });

    }

    private Boolean isInternet(){
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}
