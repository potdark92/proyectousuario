package com.comfacesar.comfacesar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Usuario;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioSesionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioSesionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioSesionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    private EditText nombreCuentaEditText;
    private EditText contraseñaEditText;
    private Button iniciarSesionButton;
    private Button registrarmeButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view_permanente == null)
        {
            nombreCuentaEditText = view_permanente.findViewById(R.id.nombreCuentaEditText);
            contraseñaEditText = view_permanente.findViewById(R.id.contraseñaCuentaEditText);
            iniciarSesionButton = view_permanente.findViewById(R.id.iniciarSesionButton);
            registrarmeButton = view_permanente.findViewById(R.id.registrarmeButton);
            iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(nombreCuentaEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(view_permanente.getContext(), "Ingrese el nombre de su cuenta de usuario", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(contraseñaEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(view_permanente.getContext(), "Ingrese la contraseña de su cuenta de usuario", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Usuario usuario = new Usuario();
                    usuario.nombre_cuenta_usuario = nombreCuentaEditText.getText().toString();
                    usuario.contraseña_usuario = contraseñaEditText.getText().toString();
                    HashMap<String, String> params = new Gestion_usuario().validar_usuario(usuario);
                    Response.Listener<String> stringListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(view_permanente.getContext(), "usuario : " + response, Toast.LENGTH_LONG).show();
                        }
                    };
                    StringRequest stringRequest = com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton.volley_consulta(com.comfacesar.ServiAmigo.Extra.WebService.getUrl(),params,stringListener, com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton.errorListener());
                }
            });
            registrarmeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            view_permanente = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
        }
        return view_permanente;
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
}