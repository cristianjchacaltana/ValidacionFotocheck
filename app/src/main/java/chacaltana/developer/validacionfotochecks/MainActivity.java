package chacaltana.developer.validacionfotochecks;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button mReconocerButton;
    ImageView mFotochecksImageView;
    TextView mValidacionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // enlazado de los elementos
        mReconocerButton = findViewById(R.id.button_recognize);
        mFotochecksImageView = findViewById(R.id.image_main);
        mValidacionTextView = findViewById(R.id.text_validacion);


        // escuchador de click
        mReconocerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacionFotochecks();
            }
        });

    }



    public void validacionFotochecks() {

        // creacion del objeto FirebaseVisionImage
        FirebaseVisionImage firebaseVisionImage =
                FirebaseVisionImage.fromBitmap(

                        BitmapFactory.decodeResource(
                                getApplicationContext().
                                getResources(), R.drawable.falso)

        );



        // creacion del objeto que reconoce
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance()
                                                                                .getOnDeviceTextRecognizer();


        // procesamos la imagen
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage)
                                    .addOnCompleteListener(new OnCompleteListener<FirebaseVisionText>() {
                                        @Override
                                        public void onComplete(@NonNull Task<FirebaseVisionText> task) {

                                            if ( task.isSuccessful() ){
                                                mostrarInformacion(task.getResult());
                                            } else {
                                                mValidacionTextView.setText("validacion");
                                            }

                                        }
                                    });


    }


    public void mostrarInformacion(FirebaseVisionText firebaseVisionText) {

        String resultado = firebaseVisionText.getText();



        if ( resultado.equals("CODO01") ) {

            mValidacionTextView.setText("fotocheck valido");

        } else {
            mValidacionTextView.setText("fotocheck falsificado");
        }


    }





}
