package com.comfacesar.comfacesar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.Dialog.MensajeInicioSesionDialog;
import com.comfacesar.comfacesar.R;
import com.example.gestion.Gestion_usuario;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsesoriaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsesoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsesoriaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView salud_sexual_y_reproductiva_ImageView;
    private ImageView identidad_ImageView;
    private ImageView maltrato_ImageView;
    private ImageView embarazo_ImageView;
    public static FragmentManager fragmentManager;
    private String especialidad;
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AsesoriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsesoriaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsesoriaFragment newInstance(String param1, String param2) {
        AsesoriaFragment fragment = new AsesoriaFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_asesoria, container, false);
        maltrato_ImageView = view.findViewById(R.id.maltrato_imageview_asesoria);
        salud_sexual_y_reproductiva_ImageView = view.findViewById(R.id.salud_sexual_reproductiva_imageview_asesoria);
        embarazo_ImageView = view.findViewById(R.id.embarazo_imageview_asesoria);
        identidad_ImageView = view.findViewById(R.id.identidad_imageview_asesoria);
        final Fragment fragment = null;
        maltrato_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    ChatActivosFragment.tipoAsesoria = 3;
                    abrir_ventana_administradores();
                }
                else
                {
                    mensaje_usuario_no_conectado();
                }
            }
        });
        salud_sexual_y_reproductiva_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    ChatActivosFragment.tipoAsesoria = 1;
                    abrir_ventana_administradores();
                }
                else
                {
                    mensaje_usuario_no_conectado();
                }
            }
        });
        embarazo_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    ChatActivosFragment.tipoAsesoria = 4;
                    abrir_ventana_administradores();
                }
                else
                {
                    mensaje_usuario_no_conectado();
                }
            }
        });
        identidad_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    ChatActivosFragment.tipoAsesoria = 2;
                    abrir_ventana_administradores();
                }
                else
                {
                    mensaje_usuario_no_conectado();
                }
            }
        });
        return view;
    }

    private void mensaje_usuario_no_conectado()
    {
        MensajeInicioSesionDialog detalleAsesorDialog = MensajeInicioSesionDialog.nuevaInstancia("Inicie sesion para poder acceder al chat");
        try {
            detalleAsesorDialog.show(getActivity().getSupportFragmentManager(), "missiles");
        } catch (IllegalStateException ignored) {

        }
    }

    public void abrir_ventana_administradores()
    {
        /*Fragment fragment = new ChatActivosFragment();
        ChatActivosFragment.fragmentManager = fragmentManager;
        fragmentManager.beginTransaction().replace(R.id.container2,fragment).commit();*/
        Intent intent = new Intent(view.getContext(), ContainertwoActivity.class);
        intent.putExtra("id",10);
        startActivity(intent);
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
