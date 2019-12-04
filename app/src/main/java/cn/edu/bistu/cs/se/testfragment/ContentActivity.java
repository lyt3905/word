package cn.edu.bistu.cs.se.testfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String word, String mean_word,String example_sentence) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra("word", word);
        intent.putExtra("mean_word", mean_word);
        intent.putExtra("example_sentence", example_sentence);
        context.startActivity(intent);
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_m_s);
        String word = getIntent().getStringExtra("word");
        String mean_word = getIntent().getStringExtra("mean_word");
        String example_sentence = getIntent().getStringExtra("example_sentence");
       TextView textView1=findViewById(R.id.textView1);
       TextView textView2=findViewById(R.id.textView2);
       TextView textView3=findViewById(R.id.textView3);
       textView1.setText(word);
       textView2.setText(mean_word);
       textView3.setText(example_sentence);


    }

}
