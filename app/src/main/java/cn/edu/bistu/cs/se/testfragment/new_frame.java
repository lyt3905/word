package cn.edu.bistu.cs.se.testfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.bistu.cs.se.testfragment.db.MyDataBaseHelper;

public class new_frame extends Fragment {
    private boolean isTwoPane;
    EditText et;
    private static MyDataBaseHelper dbHelper;
    private static SQLiteDatabase sqlDB ;
    ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();;

    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper =MyDataBaseHelper.getInstance(getActivity());
        sqlDB = dbHelper.getReadableDatabase();;
        View view = inflater.inflate(R.layout.list, container, false);
        listView = view.findViewById(R.id.listView1);

        registerForContextMenu(listView);
        firstListview();

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("dbHelper.getWritableDatabase().getVersion();="+dbHelper.getWritableDatabase().getVersion());
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; // 可以找到news_content_layout布局时，为双页模式
        } else {
            isTwoPane = false; // 找不到news_content_layout布局时，为单页模式
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map map=result.get(i);
                final int id=(int)map.get("id");
                String word=map.get("word")+"";
                String mean_word=map.get("mean_word")+"";
                String example_sentence=map.get("example_sentence")+"";
                if (isTwoPane) {
                    contentFrame contentFrame = (contentFrame)
                            getFragmentManager().findFragmentById(R.id.news_content_fragment);
                    contentFrame.refresh(word, mean_word,example_sentence);
                } else {
                    ContentActivity.actionStart(getActivity(), word,mean_word,example_sentence);
                }
            }
        });




    }



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // 加载xml中的上下文菜单

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.longtimepressfornew, menu);

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

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
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

                        sqlDB.execSQL("update new set word =? , mean_word =? , example_sentence =? where _id = ? ",
                                new Object[] {strword, strword_mean, strexample_sentence,id});
                        System.out.println("after-id="+id);
                        System.out.println("after-strword="+strword);
                        System.out.println("after-strword_mean="+strword_mean);
                        System.out.println("after-strexample_sentence="+strexample_sentence);
                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG)
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

                TextView textView=new TextView(getActivity());
                textView.setTextSize(20);
                textView.setText("即将删除的单词为  "+word);

                android.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("删除确认");
                builder1.setView(textView);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlDB.execSQL("delete from new where _id=?",
                                new String[]{id+""});
                        firstListview();
                        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("取消",null);
                builder1.show();
                AlertDialog alert1=builder1.create();

                break;
            case R.id.searchinnew:
            {
                et = new EditText(getActivity());
                android.app.AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                AlertDialog alertDialog = builder3.setView(et)
                        .setTitle("搜索单词")
                        .setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String key = et.getText().toString();
                                System.out.println("et=" + key);

                                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                                        "SELECT * FROM dict WHERE word LIKE ? OR mean_word LIKE ? OR example_sentence LIKE ?",
                                        new String[]{"%" + key + "%", "%" + key + "%", "%" + key + "%"});

                                result.clear();
                                while (cursor.moveToNext()) {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("id", cursor.getInt(0));
                                    map.put("word", cursor.getString(1));
                                    map.put("mean_word", cursor.getString(2));
                                    map.put("example_sentence", cursor.getString(3));
                                    result.add(map);
                                }
                                SimpleAdapter simpleAdapter = new SimpleAdapter(
                                        getContext(), result, R.layout.line,
                                        new String[]{"word", "mean_word", "example_sentence"},
                                        new int[]{R.id.textView1, R.id.textView2, R.id.textView3});
                                listView.setAdapter(simpleAdapter);

                            }
                        }).create();
                alertDialog.show();


            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }












    public void firstListview(){
        String key ="";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM new WHERE word LIKE ? OR mean_word LIKE ? OR example_sentence LIKE ?",
                new String[]{"%" + key + "%", "%" + key + "%", "%" + key + "%"});
        result.clear();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",cursor.getInt(0));
            map.put("word", cursor.getString(1));
            map.put("mean_word", cursor.getString(2));
            map.put("example_sentence", cursor.getString(3));
            result.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getActivity(), result, R.layout.line, new String[]{
                "word", "mean_word", "example_sentence"}, new int[]{R.id.textView1,
                R.id.textView2, R.id.textView3});
        listView.setAdapter(simpleAdapter);
    }
}
