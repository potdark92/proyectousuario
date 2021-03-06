package com.comfacesar.comfacesar.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.Dialog.Inicio_sesion_dialog;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioSesionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioSesionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioSesionFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private TextView registrarmeTextView;
    private SignInButton googleSignInButton;
    private final int TIPO_CUENTA_GOOGLE = 1;
    private final int TIPO_CUENTA_FACEBOOK = 2;

    public InicioSesionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioSesionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioSesionFragment newInstance(String param1, String param2) {
        InicioSesionFragment fragment = new InicioSesionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private static View view_permanente;
    private Button iniciarSesionButton;
    private GoogleApiClient googleApiClient;
    private final int SIGN_IN_CODE = 777;
    private GoogleSignInClient googleSignInClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_permanente = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
        registrarmeTextView = view_permanente.findViewById(R.id.registrarmeTextView);
        iniciarSesionButton = view_permanente.findViewById(R.id.iniciarSesionButton);
        registrarmeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContainertwoActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });
        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inicio_sesion_dialog inicio_sesion_dialog =Inicio_sesion_dialog.newInstancia(new Inicio_sesion_dialog.IniciarSesion() {
                    @Override
                    public void sesionIniciada(Inicio_sesion_dialog inicio_sesion_dialog) {
                        Intent intent = new Intent(getActivity(), ContainerActivity.class);
                        startActivity(intent);
                        salvarSesion();
                        inicio_sesion_dialog.dismiss();
                    }
                });
                inicio_sesion_dialog.show(getFragmentManager(), "missiles");
            }
        });
        googleSignInButton = view_permanente.findViewById(R.id.googleSignInButton);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

        return view_permanente;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE)
        {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult)
    {
        if(googleSignInResult.isSuccess())
        {
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            HashMap<String, String> params = new Gestion_usuario().generar_token_tipo_google_facebook(googleSignInAccount.getAccount().name, TIPO_CUENTA_GOOGLE);
            googleSignInClient.signOut();
            String idToken = googleSignInAccount.getIdToken();
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        Integer.parseInt(response);
                        Toast.makeText(view_permanente.getContext(), "Error al iniciar sesion", Toast.LENGTH_LONG).show();
                    }
                    catch(NumberFormatException exc)
                    {
                        ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                        if(usuarios.isEmpty())
                        {
                            Toast.makeText(view_permanente.getContext(), "Error en el sistema", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Gestion_usuario.setUsuario_online(usuarios.get(0));
                            Toast.makeText(view_permanente.getContext(), "Sesion iniciada", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), ContainerActivity.class);
                            salvarSesion();
                            startActivity(intent);
                        }
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view_permanente.getContext(), "Error en el sistema", Toast.LENGTH_LONG).show();
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);

        }
        else
        {
            Toast.makeText(view_permanente.getContext(), "No logueado", Toast.LENGTH_SHORT).show();
            googleSignInClient.signOut();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void salvarSesion()
    {
        SharedPreferences prefs = getActivity().getSharedPreferences("SESION_USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putString("TOKEN", Gestion_usuario.getUsuario_online().token);
        myEditor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Gestion_usuario.getUsuario_online() != null)
        {
            getActivity().finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
