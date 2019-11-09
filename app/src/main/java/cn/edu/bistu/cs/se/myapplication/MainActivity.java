package cn.edu.bistu.cs.se.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase sqlDB ;
    private boolean isTwoPane;
    EditText et;
    ArrayList<Map<String, Object>> result;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            isTwoPane = true;
            System.out.println("isTwoPane = true");

        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            isTwoPane = false;
            System.out.println("isTwoPane = false");

        }




        dbHelper =new MyDataBaseHelper(MainActivity.this,
                "myDict.db3", 1);;
        sqlDB = dbHelper.getReadableDatabase();;
        listView = (ListView) findViewById(R.id.listView1);
        firstListview();
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map map=result.get(i);
                final int id=(int)map.get("id");
                String word=map.get("word")+"";
                String mean_word=map.get("mean_word")+"";
                String example_sentence=map.get("example_sentence")+"";
                if (isTwoPane) {
                    TextView wordtext=findViewById(R.id.word);
                    TextView word_meantext=findViewById(R.id.word_mean);
                    TextView example_sentencetext=findViewById(R.id.example_sentence);

                    wordtext.setText(word);
                    word_meantext.setText(mean_word);
                    example_sentencetext.setText(example_sentence);

                } else {

                    ContentActivity.actionStart(MainActivity.this, word,mean_word,example_sentence);

                }
            }
        });

        //长按与短按操作
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map map=result.get(i);
                final int id=(int)map.get("id");
                String word=map.get("word")+"";
                String mean_word=map.get("mean_word")+"";
                String example_sentence=map.get("example_sentence")+"";
                System.out.println("id="+id);
                System.out.println("word="+word);
                System.out.println("mean_word="+mean_word);
                System.out.println("example_sentence="+example_sentence);

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View view_custom = inflater.inflate(R.layout.add, null,false);
                final EditText wordet =view_custom.findViewById(R.id.word);
                final EditText mean_wordet=view_custom.findViewById(R.id.word_mean);
                final EditText example_sentenceet =view_custom.findViewById(R.id.example_sentence);

                wordet.setText(word);
                mean_wordet.setText(mean_word);
                example_sentenceet.setText(example_sentence);
                builder.setView(view_custom);
                builder.setCancelable(true);
                builder.setTitle("修改单词");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        String strword = wordet.getText().toString();
                        String strword_mean = mean_wordet.getText().toString();
                        String strexample_sentence=example_sentenceet.getText().toString();

                        sqlDB.execSQL("update dict set word =? , mean_word =? , example_sentence =? where _id = ? ",
                                new Object[] {strword, strword_mean, strexample_sentence,id});
                        System.out.println("after-id="+id);
                        System.out.println("after-strword="+strword);
                        System.out.println("after-strword_mean="+strword_mean);
                        System.out.println("after-strexample_sentence="+strexample_sentence);
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG)
                                .show();
                        firstListview();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                AlertDialog alert=builder.create();
            }
        });


       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Map map=result.get(i);
                final int id=(int)map.get("id");
                String word=map.get("word")+"";
                TextView textView=new TextView(MainActivity.this);
                textView.setTextSize(20);
                textView.setText("即将删除的单词为  "+word);

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除确认");
                builder.setView(textView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlDB.execSQL("delete from dict where _id=?",
                                new String[]{id+""});
                        firstListview();
                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                AlertDialog alert=builder.create();




                return true;
            }
        });
*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
               Map map=result.get(i);
               final int id=(int)map.get("id");
               String word=map.get("word")+"";
               String mean_word=map.get("mean_word")+"";
               String example_sentence=map.get("example_sentence")+"";
               System.out.println("id="+id);
               System.out.println("word="+word);
               System.out.println("mean_word="+mean_word);
               System.out.println("example_sentence="+example_sentence);

               android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
               final View view_custom = inflater.inflate(R.layout.add, null,false);
               final EditText wordet =view_custom.findViewById(R.id.word);
               final EditText mean_wordet=view_custom.findViewById(R.id.word_mean);
               final EditText example_sentenceet =view_custom.findViewById(R.id.example_sentence);

               wordet.setText(word);
               mean_wordet.setText(mean_word);
               example_sentenceet.setText(example_sentence);
               builder.setView(view_custom);
               builder.setCancelable(true);
               builder.setTitle("修改单词");
               builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {



                       String strword = wordet.getText().toString();
                       String strword_mean = mean_wordet.getText().toString();
                       String strexample_sentence=example_sentenceet.getText().toString();

                       sqlDB.execSQL("update dict set word =? , mean_word =? , example_sentence =? where _id = ? ",
                               new Object[] {strword, strword_mean, strexample_sentence,id});
                       System.out.println("after-id="+id);
                       System.out.println("after-strword="+strword);
                       System.out.println("after-strword_mean="+strword_mean);
                       System.out.println("after-strexample_sentence="+strexample_sentence);
                       Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG)
                               .show();
                       firstListview();


                   }
               });
               builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               builder.show();
               AlertDialog alert=builder.create();

           }
       });*/
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // 加载xml中的上下文菜单

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.longtimepress, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        long i=menuInfo.id;
        Map map=result.get((int)i);
        final int id=(int)map.get("id");
        String word=map.get("word")+"";
        String mean_word=map.get("mean_word")+"";
        String example_sentence=map.get("example_sentence")+"";
        switch (item.getItemId()) {
            case R.id.edit:
                System.out.println("id="+id);
                System.out.println("word="+word);
                System.out.println("mean_word="+mean_word);
                System.out.println("example_sentence="+example_sentence);

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View view_custom = inflater.inflate(R.layout.add, null,false);
                final EditText wordet =view_custom.findViewById(R.id.word);
                final EditText mean_wordet=view_custom.findViewById(R.id.word_mean);
                final EditText example_sentenceet =view_custom.findViewById(R.id.example_sentence);

                wordet.setText(word);
                mean_wordet.setText(mean_word);
                example_sentenceet.setText(example_sentence);
                builder.setView(view_custom);
                builder.setCancelable(true);
                builder.setTitle("修改单词");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        String strword = wordet.getText().toString();
                        String strword_mean = mean_wordet.getText().toString();
                        String strexample_sentence=example_sentenceet.getText().toString();

                        sqlDB.execSQL("update dict set word =? , mean_word =? , example_sentence =? where _id = ? ",
                                new Object[] {strword, strword_mean, strexample_sentence,id});
                        System.out.println("after-id="+id);
                        System.out.println("after-strword="+strword);
                        System.out.println("after-strword_mean="+strword_mean);
                        System.out.println("after-strexample_sentence="+strexample_sentence);
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG)
                                .show();
                        firstListview();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                AlertDialog alert=builder.create();
                break;

            case R.id.delete:

                TextView textView=new TextView(MainActivity.this);
                textView.setTextSize(20);
                textView.setText("即将删除的单词为  "+word);

                android.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("删除确认");
                builder1.setView(textView);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlDB.execSQL("delete from dict where _id=?",
                                new String[]{id+""});
                        firstListview();
                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("取消",null);
                builder1.show();
                AlertDialog alert1=builder1.create();

                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            final View view_custom = inflater.inflate(R.layout.add, null,false);


            builder.setView(view_custom);
            builder.setCancelable(true);
            AlertDialog alert=builder.create();
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {




                    EditText word =view_custom.findViewById(R.id.word);
                    EditText word_mean=view_custom.findViewById(R.id.word_mean);
                    EditText example_sentence =view_custom.findViewById(R.id.example_sentence);


                    String strword = word.getText().toString();
                    String strword_mean = word_mean.getText().toString();
                    String strexample_sentence=example_sentence.getText().toString();

                    sqlDB.execSQL("INSERT INTO dict VALUES(NULL,?,?,?)", new String[] {
                            strword, strword_mean, strexample_sentence});

                    Toast.makeText(getApplicationContext(), "插入成功", Toast.LENGTH_LONG)
                            .show();
                    firstListview();


                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();






            return true;
        }
        if (id == R.id.search){
            et=new EditText(MainActivity.this);
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            AlertDialog alertDialog=builder.setView(et)
                    .setTitle("搜索单词")
                    .setPositiveButton("搜索", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String key = et.getText().toString();
                System.out.println("et=" + key);

                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                            "SELECT * FROM dict WHERE word LIKE ? OR mean_word LIKE ? OR example_sentence LIKE ?",
                            new String[]{"%" + key + "%", "%" + key + "%", "%" + key + "%"});
                    ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();

                    while (cursor.moveToNext()) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("word", cursor.getString(1));
                        map.put("mean_word", cursor.getString(2));
                        map.put("example_sentence", cursor.getString(3));
                        result.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(
                        getApplicationContext(), result, R.layout.line, new String[]{
                        "word", "mean_word", "example_sentence"}, new int[]{R.id.textView1,
                        R.id.textView2, R.id.textView3});
                listView.setAdapter(simpleAdapter);

            }
        }).create();
        alertDialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    public void firstListview(){
        String key ="";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM dict WHERE word LIKE ? OR mean_word LIKE ? OR example_sentence LIKE ?",
                new String[]{"%" + key + "%", "%" + key + "%", "%" + key + "%"});
        result = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",cursor.getInt(0));
            map.put("word", cursor.getString(1));
            map.put("mean_word", cursor.getString(2));
            map.put("example_sentence", cursor.getString(3));
            result.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getApplicationContext(), result, R.layout.line, new String[]{
                "word", "mean_word", "example_sentence"}, new int[]{R.id.textView1,
                R.id.textView2, R.id.textView3});
        listView.setAdapter(simpleAdapter);
    }


}
