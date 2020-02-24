package com.example.aluno.trabalhoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirTelaAlimento(View v){
        try {
            Intent i = new Intent(MainActivity.this, TelaAlimento.class);
            startActivity(i);
        }
        catch(Exception ex){
            Toast.makeText(this,"Erro: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void abrirTelaConsumo(View v){
        try {
            Intent i = new Intent(MainActivity.this, TelaConsumo.class);
            startActivity(i);
        }
        catch(Exception ex){
            Toast.makeText(this,"Erro: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void abrirRelatorio(View v){
        try {
            Intent i = new Intent(MainActivity.this, Relatorio.class);
            startActivity(i);
        }
        catch(Exception ex){
            Toast.makeText(this,"Erro: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
