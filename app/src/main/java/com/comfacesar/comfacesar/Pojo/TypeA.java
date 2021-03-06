package com.comfacesar.comfacesar.Pojo;

import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;


public class TypeA{
    private String imagen;
    private Noticia noticia;
    private Imagen_noticia imagen_noticia;

    public TypeA(Noticia noticia, Imagen_noticia imagen_noticia) {
        this.imagen=imagen_noticia.url_imagen_noticia;
        this.noticia = noticia;
        this.imagen_noticia = imagen_noticia;
    }

    public TypeA(Noticia noticia) {
        this.imagen="";
        this.noticia = noticia;
        imagen_noticia = null;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public Imagen_noticia getImagen_noticia() {
        return imagen_noticia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getListItemType() {
        return noticia.categoria_noticia_manual_noticia;
    }
}
