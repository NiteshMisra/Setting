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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class GeneralFragment extends Fragment {

    private String userId;
    private ConstraintLayout layout;
    private TextView noInternet;
    private EditText name;
    private TextView mobileNo;
    private TextView emailId;
    private TextView userName;
    private Button saveProfile;
    private SwipeRefreshLayout gRefresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.general_layout);
        noInternet = view.findViewById(R.id.general_no_internet);
        name = view.findViewById(R.id.general_name);
        mobileNo = view.findViewById(R.id.general_mobile_no);
        emailId = view.findViewById(R.id.general_email_id);
        userName = view.findViewById(R.id.general_user_name);
        saveProfile = view.findViewById(R.id.general_save);
        gRefresh = view.findViewById(R.id.general_refresh);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setCursorVisible(true);
            }
        });
        gRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLayout();
            }
        });
        loadLayout();
    }

    private void loadLayout() {
        if (isInternet()){
            gRefresh.setRefreshing(true);
            layout.setVisibility(View.GONE);
            noInternet.setText("Please wait...");
            noInternet.setVisibility(View.VISIBLE);
            saveProfile.setVisibility(View.GONE);

            final Map<String,String> headers = new HashMap<>();
            headers.put("User-ID","1");
            headers.put("Authorization","67nPxwLC/yyGc");

            saveProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isInternet()){
                        name.setCursorVisible(false);
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("Updating Name");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        RetrofitClient.getInstance().getApi().updateName(headers,userId,name.getText().toString()).enqueue(new Callback<UpdateNameResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<UpdateNameResponse> call, @NonNull Response<UpdateNameResponse> response) {
                                if(response.isSuccessful()){
                                    assert response.body() != null;
                                    if (response.body().getData() != null){
                                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    assert response.errorBody() != null;
                                    Toast.makeText(getActivity(),"error " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
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
                                })
                                .create()
                                .show();
                    }
                }
            });

            RetrofitClient.getInstance().getApi().getUserDetails(headers,1).enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        if (response.body().getData() != null){
                            List<GeneralModel> userData = response.body().getData();
                            name.setText(userData.get(0).getName());
                            mobileNo.setText(userData.get(0).getPhone());
                            emailId.setText(userData.get(0).getEmail());
                            userName.setText(userData.get(0).getUsername());
                            userId = userData.get(0).getId();
                            layout.setVisibility(View.VISIBLE);
                            noInternet.setVisibility(View.GONE);
                            saveProfile.setVisibility(View.VISIBLE);
                        }
                    }else{
                        layout.setVisibility(View.GONE);
                        noInternet.setText("No Response from server\nRefresh to try again");
                        noInternet.setVisibility(View.VISIBLE);
                        saveProfile.setVisibility(View.GONE);
                    }
                    gRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                    gRefresh.setRefreshing(false);
                    layout.setVisibility(View.GONE);
                    noInternet.setText("No Response from server\nRefresh to try again");
                    noInternet.setVisibility(View.VISIBLE);
                    saveProfile.setVisibility(View.GONE);
                }
            });
        }else{
            gRefresh.setRefreshing(false);
            layout.setVisibility(View.GONE);
            noInternet.setText("No Internet Connection\nRefresh to try again");
            noInternet.setVisibility(View.VISIBLE);
            saveProfile.setVisibility(View.GONE);
        }
    }

    private Boolean isInternet(){
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}
