package com.example.juego_luca_ortiz.Model
import android.content.Context
import android.widget.Button
import android.widget.TextView

//variable turno iría aqui tb
class Jugador(private var txtView: Button, private var puntos:Int) {

    fun setPuntos(nuevosPuntos:Int) {
        puntos = nuevosPuntos
    }
    fun setTxtView(nuevotxtView: Button){
        txtView = nuevotxtView
    }
    fun getTxtView(): Button {
        return txtView
    }
    fun getPuntos(): Int {
        return puntos
    }
    constructor(context: Context) : this(Button(context), 0)
}
