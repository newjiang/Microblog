package com.example.jiang.microblog.view.share;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.test.WebViewActivity;
import com.example.jiang.microblog.view.at.AtActivity;
import com.example.jiang.microblog.view.share.adapter.GridViewAddImgesAdpter;

import net.bither.util.NativeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShareActivity extends BaseActivity implements  View.OnClickListener {

    private final int PHOTO_REQUEST_CAREMA = 1;//TODO 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;//TODO 从相册中选择"temp_photo.jpg";
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private ImageView selectVideo;
    private ImageView selectPhoto;
    private ImageView atFriends;
    private ImageView shareWeibo;
    private ImageView topic;

    private GridView gridView;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    private List<Map<String, Object>> datas = new ArrayList<>();;
    private Dialog dialog;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("哈哈哈");
        }
        initView();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.photo_gridview);
        selectPhoto = (ImageView) findViewById(R.id.select_photo);
        selectVideo = (ImageView) findViewById(R.id.select_video);
        atFriends = (ImageView) findViewById(R.id.at_friend);
        shareWeibo = (ImageView) findViewById(R.id.share_weibo);
        topic = (ImageView) findViewById(R.id.topic);
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gridView.setAdapter(gridViewAddImgesAdpter);
        selectVideo.setOnClickListener(this);
        selectPhoto.setOnClickListener(this);
        atFriends.setOnClickListener(this);
        shareWeibo.setOnClickListener(this);
        topic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_video:
                Toast.makeText(ShareActivity.this, "video", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShareActivity.this, RecordingActivity.class));
                break;
            case R.id.select_photo:
                if (datas.size() > 9) {
                    Toast.makeText(ShareActivity.this, "最多只能选择9张图片", Toast.LENGTH_SHORT).show();
                } else {
                    gallery();
                }
                break;
            case R.id.at_friend:
                startActivity(new Intent(ShareActivity.this,AtActivity.class));
                break;
            case R.id.topic:
                Toast.makeText(ShareActivity.this, "topic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_weibo:
                Toast.makeText(ShareActivity.this, "share", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShareActivity.this,WebViewActivity.class));
                break;
        }
    }

    /**
     * 选择图片对话框
     */
    public void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_picture, null);
        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) view.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //TODO 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        //TODO 设置宽度
        lp.width = display.getWidth();
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //TODO 取消
                dialog.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO  拍照
                camera();
            }
        });
        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO  从系统相册选取照片
                gallery();
            }
        });
    }

    /**
     * 拍照
     */
    public void camera() {
        //TODO  判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            File dir = new File(IMAGE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            tempFile = new File(dir, System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
            //TODO 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            Intent intent = new Intent();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(intent.CATEGORY_DEFAULT);
            //TODO  开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * 从相册获取
     */
    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                //TODO  从相册返回的数据
                if (data != null) {
                    //TODO  得到图片的全路径
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //TODO 好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //TODO 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //TODO 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //TODO 最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);

                    uploadImage(path);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                if (resultCode != RESULT_CANCELED) {
                    //TODO  从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
                            uploadImage(tempFile.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("images", "拿到照片path=" + tempFile.getPath());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.e("images", "源文件存在" + path);
                } else {
                    Log.e("images", "源文件不存在" + path);
                }
                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
                //TODO 图片压缩
                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);
                if (file.exists()) {
                    Log.e("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.e("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);
            }
        }.start();

    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}