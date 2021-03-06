package com.comfacesar.comfacesar.Adaptador;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Activities.Detalle_Articulo_Activity;
import com.comfacesar.comfacesar.Dialog.MensajeDarMeGustaDialog;
import com.comfacesar.comfacesar.Dialog.MensajeUsuarioSaliendo;
import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Item.ItemNoticia;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.HomeFragment;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_me_gusta_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Me_gusta_noticia;
import com.example.modelo.Noticia;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.ViewHolder> {


    private List<ItemNoticia> mItems;
    public View view;
    private  FragmentManager fragmentManager;
    private Activity actividad;


    public AdapterNoticia(Activity _actividad,List<ItemNoticia> items, FragmentManager fragmentManager) {
        this.mItems = items;
        this.fragmentManager = fragmentManager;
        this.actividad= _actividad;
    }

    ///////////////////////////////////////////
    @Override
    public int getItemViewType(int position) {

        return mItems.get(position).getNoticia().categoria_noticia_manual_noticia;
    }


///////////////////////////////////////////////////////
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup contexto, int type) {

        // inflamos la vista y la enviamos al view holder
        view = null;
        switch (type)
        {
            case ListItem.SEXUALIDAD:
                view=LayoutInflater.from(contexto.getContext()).inflate(R.layout.sexualidad_tipo_noticia,null,false);
                break;
            case ListItem.EMBARAZO:
                view=LayoutInflater.from(contexto.getContext()).inflate(R.layout.embarazo_tipo_noticia,null,false);
                break;
            case ListItem.MALTRATO:
                view=LayoutInflater.from(contexto.getContext()).inflate(R.layout.maltrato_tipo_noticia,null,false);
                break;
            case ListItem.IDENTDAD:
                view=LayoutInflater.from(contexto.getContext()).inflate(R.layout.identidad_tipo_noticia,null,false);
                break;
        }
        return new AdapterNoticia.ViewHolder(view);
    }

    //////////////////////////////////////////
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ItemNoticia item = mItems.get(i);
        viewHolder.setDatos(item, fragmentManager);
    }
    /////////////////////
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    //////////////////////
    public class ViewHolder  extends RecyclerView.ViewHolder {


        private final TextView tituto_textview;
        private final ImageView imagen_noticiaImageView;
        private final TextView contenido_TextView;
        private final CheckBox megusta_CheckBox;
        private boolean comprobando_usuario = false;
        private ItemNoticia itemNoticia;
        private View view;
        private FrameLayout frameLayout;
        private boolean cambiando_estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tituto_textview = itemView.findViewById(R.id.titulo_textView_itemNoticia);
            imagen_noticiaImageView = itemView.findViewById(R.id.imagen_imageView_itemNoticia);
            contenido_TextView = itemView.findViewById(R.id.contenido_textView_itemNoticia);
            megusta_CheckBox = itemView.findViewById(R.id.me_gusta_checkBox_itemNoticia);
            megusta_CheckBox.setChecked(false);
            view = itemView;
            this.frameLayout = frameLayout;

        }

        public void setDatos(final ItemNoticia item, final FragmentManager fragmentManager)
        {
            this.itemNoticia = item;
            tituto_textview.setText(item.getNoticia().titulo_noticia);
            contenido_TextView.setText(item.getNoticia().contenido_noticia);
            megusta_CheckBox.setText(item.getNoticia().numero_me_gusta + " me gusta");
            if(item.getImagen() !="")
            {
                Picasso.with(view.getContext()).load(item.getImagen()).into(imagen_noticiaImageView);
            }
            if(Gestion_usuario.getUsuario_online() != null)
            {
                tengo_me_gusta();
            }

            megusta_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    megusta_CheckBox.setEnabled(false);
                    if(Gestion_usuario.getUsuario_online() != null && !cambiando_estado)
                    {
                        HashMap<String,String> params = new Gestion_me_gusta_noticia().dar_me_gusta(item.getNoticia().id_notiticia, Gestion_usuario.getUsuario_online().id_usuario);
                        Response.Listener<String> stringListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                consultar_noticia();
                            }
                        };
                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tengo_me_gusta();
                            }
                        };
                        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
                        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                    }
                    if(Gestion_usuario.getUsuario_online() == null)
                    {

                        if(megusta_CheckBox.isChecked())
                        {
                            MensajeDarMeGustaDialog mensajeUsuarioSaliendo = MensajeDarMeGustaDialog.nuevaUbstancia("Para indicar que te gusta este articulo inicia sesion");
                            mensajeUsuarioSaliendo.show(fragmentManager, "missiles");
                            megusta_CheckBox.setChecked(false);
                        }
                    }
                    megusta_CheckBox.setEnabled(true);
                }
            });
            if(item.getImagen() != "")
            {
                Picasso.with(view.getContext()).load(item.getImagen()).into(imagen_noticiaImageView);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getNoticia().tipo_creacion_noticia == 2)
                    {
                        Uri uri = Uri.parse("http://www.comfacesar.com/articulo.aspx?idc=" + item.getNoticia().id_generacion_noticia);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                        ContextCompat.startActivity(view.getContext(),myIntent,null);
                    }
                }
            });
            imagen_noticiaImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirArticulo();
                }
            });
            LinearLayout linearLayout = view.findViewById(R.id.cuerpo1LinearLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirArticulo();
                }
            });
        }

        private void abrirArticulo()
        {
            Intent intent= new Intent(actividad, Detalle_Articulo_Activity.class);
            intent.putExtra("id_noticia", itemNoticia.getNoticia().id_notiticia);
            intent.putExtra("titulo", itemNoticia.getNoticia().titulo_noticia);
            intent.putExtra("contenido", itemNoticia.getNoticia().contenido_noticia);
            intent.putExtra("fecha", itemNoticia.getNoticia().fecha_registro_noticia);
            intent.putExtra("hora", itemNoticia.getNoticia().hora_registro_noticia);
            intent.putExtra("imagen", itemNoticia.getImagen());
            intent.putExtra("categoria", itemNoticia.getNoticia().categoria_noticia_manual_noticia);
            HomeFragment.id_articulo_selecionado = itemNoticia.getNoticia().id_notiticia;

            Detalle_Articulo_Activity.escuchadoMeGusta = new Detalle_Articulo_Activity.EscuchadoMeGusta() {
                @Override
                public void meMeGusta() {
                    tengo_me_gusta();
                    consultar_noticia();
                }
            };

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Explode explode= new Explode();
                explode.setDuration(1000);
                actividad.getWindow().setExitTransition(new AutoTransition());
                actividad.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(actividad).toBundle());

            }
            else{
                actividad.startActivity(intent);
            }
        }

        private void tengo_me_gusta()
        {
            HashMap<String,String> params = new Gestion_me_gusta_noticia().me_gusta_por_noticia_y_usuario(Gestion_usuario.getUsuario_online().id_usuario, itemNoticia.getNoticia().id_notiticia);

            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    ArrayList<Me_gusta_noticia> me_gusta_noticiaArraYList = new Gestion_me_gusta_noticia().generar_json(response);
                    cambiando_estado = true;
                    if(me_gusta_noticiaArraYList.isEmpty())
                    {
                        megusta_CheckBox.setChecked(false);
                    }
                    else
                    {
                        megusta_CheckBox.setChecked(true);
                    }
                    cambiando_estado = false;
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
        }

        private void consultar_noticia()
        {
            HashMap<String,String> params = new Gestion_noticia().noticia_por_id(itemNoticia.getNoticia().id_notiticia);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                if(response != "")
                {
                    ArrayList<Noticia> noticias = new Gestion_noticia().generar_json(response);
                    if(!noticias.isEmpty())
                    {
                        itemNoticia.setNoticia(noticias.get(0));
                        megusta_CheckBox.setText(itemNoticia.getNoticia().numero_me_gusta + " me gusta");
                    }
                }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
        }
    }
}
