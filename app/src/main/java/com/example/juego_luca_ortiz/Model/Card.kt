package com.example.juego_luca_ortiz.Model

import android.widget.ImageView
import android.widget.TextView

class Card(private var img: ImageView) {
    // Puedes agregar más atributos y métodos según sea necesario
    fun getImg():ImageView{
        return img
    }
    // Ejemplo de un método para establecer una imagen en el ImageView
    fun setImageResource(resId: Int) {
        img.setImageResource(resId)
    }




}