package com.example.juego_luca_ortiz.View
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.juego_luca_ortiz.Model.Card
import com.example.juego_luca_ortiz.Model.Jugador
import com.example.juego_luca_ortiz.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    //lista de imágene

    private lateinit var img1:ImageView
    private lateinit var img2:ImageView

    private var playSound = true
    //si acaso cambiar
    //private lateinit var ibSonido:ImageButton
    private lateinit var ibSonido:FloatingActionButton
    /*
    *  lateinit var touch_mp3: MediaPlayer
    lateinit var yes_mp3: MediaPlayer
    lateinit var no_mp3: MediaPlayer
    * lateinit var win_mp3: MediaPlayer
    * */
    private lateinit var sonido_mp3: MediaPlayer
    private lateinit var bg_mp3 :MediaPlayer

    //barajar
    private var imgClonadas = arrayOf(11, 12, 13, 14, 15, 16, 21 , 22 ,23, 24, 25 ,26)
    private val cardList = mutableListOf<Card>()
    private var turno = 1
    //objetos jugador
    private lateinit var j1: Jugador
    private lateinit var j2: Jugador

    //variable para saber si escojo la 1º/2º img
    private var numImg = 1
    //cambiar a un objeto (?)
    private var escuchaSonido  = true

    private lateinit var card1: Card
    private lateinit var card2: Card
    private lateinit var card3: Card
    private lateinit var card4: Card
    private lateinit var card5: Card
    private lateinit var card6: Card
    private lateinit var card7: Card
    private lateinit var card8: Card
    private lateinit var card9: Card
    private lateinit var card10: Card
    private lateinit var card11: Card
    private lateinit var card12: Card

    private lateinit var ibJuego:ImageButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        j2 = Jugador(findViewById(R.id.txtView_j2), 0)
        j1 = Jugador(findViewById(R.id.txtView_j1), 0)
        card1 = Card(findViewById(R.id.imageView))
        card2 = Card(findViewById(R.id.imageView2))
        card3 = Card(findViewById(R.id.imageView3))
        card4 = Card(findViewById(R.id.imageView4))
        card5 = Card(findViewById(R.id.imageView5))
        card6 = Card(findViewById(R.id.imageView6))
        card7 = Card(findViewById(R.id.imageView7))
        card8 = Card(findViewById(R.id.imageView8))
        card9 = Card(findViewById(R.id.imageView9))
        card10 = Card(findViewById(R.id.imageView10))
        card11 = Card(findViewById(R.id.imageView11))
        card12 = Card(findViewById(R.id.imageView12))

        cardList.add(card1)
        cardList.add(card2)
        cardList.add(card3)
        cardList.add(card4)
        cardList.add(card5)
        cardList.add(card6)
        cardList.add(card7)
        cardList.add(card8)
        cardList.add(card9)
        cardList.add(card10)
        cardList.add(card11)
        cardList.add(card12)
        ibJuego  = findViewById(R.id.ibJuego)
        //funcionalidad extra para informar al usuario del funcionamiento del juego
        ibJuego.setOnClickListener {
            Toast.makeText(this, "Cuando falles el turno pasará a tu rival... ", Toast.LENGTH_SHORT).show()
            // ocultación del Toast después de 3 segundos
            Handler().postDelayed({

                val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
                val toastView = toast.view
                if (toastView is LinearLayout) {
                    val toastIcon = toastView.getChildAt(0) as ImageView
                    toastIcon.visibility = View.GONE
                }
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            }, 3000)
        }
        coordinarVista()

    }
    private fun coordinarVista(){
        //cardList
        ibSonido = findViewById(R.id.imageButton)
        ibSonido.setColorFilter(Color.BLACK)
        //sin .mp3
        sonido("background", true)
        //etiqueta para cada img .tag
        for((i, card) in cardList.withIndex()){
            card.getImg().tag = i.toString()
            //acaban siendo 11 i (0-11)
        }
        //barajar
        imgClonadas.shuffle()

        j1.getTxtView().setTextColor(Color.BLACK)
        j2.getTxtView().setTextColor(Color.GRAY)
    }
    @SuppressLint("DiscouragedApi")
    private fun sonido(sound:String, loop:Boolean = false){
        val resId = resources.getIdentifier(sound, "raw", packageName)
        if(sound == "background"){
            bg_mp3 = MediaPlayer.create(this, resId)
            //decirle si es loop
            bg_mp3.isLooping =loop
            /*if(!bg_mp3.isPlaying){
                bg_mp3.start()
            }
            * */
           onStart()
        }else{
            if(playSound){
                //click (otros tipos de sonido)
                sonido_mp3 = MediaPlayer.create(this, resId)
                sonido_mp3.setOnCompletionListener(MediaPlayer.OnCompletionListener{
                        mediaPlayer ->  mediaPlayer.stop()
                    mediaPlayer.release()

                })
                reanudarSonido1()
            }




        }
    }
    //métodos override que son llamados cuando la app se pausa/reanuda
    override fun onPause() {
        super.onPause()
        pausarSonido()
    }
    override fun onStart() {
        super.onStart()
        reanudarSonido()

    }

    private fun pausarSonido() {
        bg_mp3.let {
            if (it.isPlaying) {
                it.pause()
            }
        }


    }
    private fun pausarSonido1(){
        sonido_mp3.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }
    private fun reanudarSonido() {
        bg_mp3.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }
    private fun reanudarSonido1() {
        sonido_mp3.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }


    //método para que haya música (fondo o sonidos esporádicos)
    fun habilitarBGMusic(v: View){
        playSound = !playSound
        if(escuchaSonido){
            pausarSonido()

            //además set color filter crea una animación en el botón
            ibSonido.setImageResource(R.drawable.baseline_volume_off_24)
            ibSonido.setColorFilter(Color.BLACK)
            ibSonido.rippleColor = Color.GREEN
        }else{
           //suene
            reanudarSonido()
            //reanudarSonido1()
            ibSonido.setImageResource(R.drawable.baseline_volume_up_24)
            ibSonido.setColorFilter(Color.BLACK)
            ibSonido.rippleColor = Color.RED
        }
        escuchaSonido= !escuchaSonido
    }

    fun seleccionarCard(imgCard: View){
        //nombre de sonido
        sonido("tocar")
        //verificar las duplicadas
        verificar(imgCard)
    }
    private fun verificar(imagen:View){
        val imgViewSelected =(imagen as ImageView)
        val tag = imagen.tag.toString().toInt()
        //recorro la list de img y según su tag las selecciono para verificar si son iguales
        when (imgClonadas[tag]) {
            11, 21 -> imgViewSelected.setImageResource(R.drawable.animal)
            12, 22 -> imgViewSelected.setImageResource(R.drawable.foozzie)
            13, 23 -> imgViewSelected.setImageResource(R.drawable.gonzo)
            14, 24 -> imgViewSelected.setImageResource(R.drawable.kermit)
            15, 25 -> imgViewSelected.setImageResource(R.drawable.peggy)
            16, 26 -> imgViewSelected.setImageResource(R.drawable.walter)
        }
        //guardar img seleccionada
        if(numImg ==1){
            img1 = imgViewSelected
            numImg =2
            imgViewSelected.isEnabled = false
        }else if(numImg == 2){
            img2 = imgViewSelected
            numImg = 1
            imgViewSelected.isEnabled = false
            deshabilitarImg()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({imgIguales()}, 1000)
        }
    }
    @SuppressLint("SetTextI18n")
    //modificar el txtview y vaciar el tag
    private fun imgIguales(){
         if(img1.drawable.constantState == img2.drawable.constantState){
                 sonido("yes")
                 if(turno==1){
                     j1.setPuntos(j1.getPuntos() + 1)
                     val puntos = j1.getPuntos()
                     j1.getTxtView().text = "Jugador 1: $puntos"

                 }else if(turno==2){
                     j2.setPuntos(j2.getPuntos() + 1)
                     val puntos = j2.getPuntos()
                     j2.getTxtView().text = "Jugador 2: $puntos"

                 }
             img1.isEnabled =false
             img2.isEnabled = false
             img1.tag = ""
             img2.tag = ""
        }else{
            sonido("no")
             img1.setImageResource(R.drawable.oculta)
             img2.setImageResource(R.drawable.oculta)
             if(turno == 1){
                 turno = 2
                 j1.getTxtView().setTextColor(Color.GRAY)
                 j2.getTxtView().setTextColor(Color.BLACK)

             }else if(turno == 2){
                 turno = 1
                 j2.getTxtView().setTextColor(Color.GRAY)
                 j1.getTxtView().setTextColor(Color.BLACK)
             }
        }
            //tag
            for(card in cardList){
                card.getImg().isEnabled = card.getImg().tag.toString().isNotEmpty()
            }
            finJuego()

    }



    private fun finJuego(){
        var vacio =0
        val msg: String
        for(card in cardList){
            if(card.getImg().tag.toString().isEmpty()){
                vacio++
            }

        }
        //mensaje personalizado para el ganador
        if(vacio>=11){
            msg = if(j1.getPuntos()>j2.getPuntos()){
                "¡HA GANADO EL JUGADOR 1!"
            }else if(j2.getPuntos()>j1.getPuntos()){
                "¡HA GANADO EL JUGADOR 2!"
            }else{
                "!EMPATE!"
            }

            
            bg_mp3.stop()
            bg_mp3.release()
            sonido("win")
            val builder = AlertDialog.Builder(this)
            builder.setTitle(msg).setMessage("PUNTOS \n J1: "+ j1.getPuntos() + "\n J2: " + j2.getPuntos()).setPositiveButton("Jugar de nuevo", DialogInterface.OnClickListener{
                    dialogInterface, i -> val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

            })
                .setNegativeButton("SALIR", DialogInterface.OnClickListener{
                    dialogInterface, i ->  finish()
                })
            val ad = builder.create()
            ad.show()

        }
    }
    private fun deshabilitarImg(){
        for(card in cardList){
            card.getImg().isEnabled = false
        }
    }

}
