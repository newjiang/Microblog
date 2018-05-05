package com.example.jiang.microblog.view.share;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.at.AtActivity;
import com.example.jiang.microblog.view.share.adapter.GridViewImageAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class ShareActivity extends BaseActivity implements View.OnClickListener {

    public static final int TAKE_RECORD = 0;
    public static final int TAKE_CAMERA = 1;
    public static final int OPEN_ALBUM = 2;
    public static final int AT_FRIENDS = 3;

    //TODO　压缩后图片的存储位置
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/microblog/img/";
    //TODO 临时图片的文件名后缀
    private final String PHOTO_NAME = "temp.jpg";

    private EditText editText;

    private ImageView takeVideo;
    private ImageView takePhoto;
    private ImageView takeGallery;
    private ImageView atFrient;
    private ImageView topic;

    private GridView gridView;
    private GridViewImageAdapter adapter;

    private List<Map<String, Object>> pictures = new ArrayList<>();

    private File temp;
    private Uri imageUri;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("分享微博");
        }
        boolean ispermission = (ContextCompat.checkSelfPermission(this, PERMISSIONS[0]) != 0 ||
                ContextCompat.checkSelfPermission(this, PERMISSIONS[1]) != 0 ||
                ContextCompat.checkSelfPermission(this, PERMISSIONS[2]) != 0 );

        if(ispermission){
            ActivityCompat.requestPermissions(this,PERMISSIONS,1);
        }
        initView();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.photo_gridview);
        adapter = new GridViewImageAdapter(this, pictures);
        gridView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.share_content);

        takeVideo = (ImageView) findViewById(R.id.take_video);
        takePhoto = (ImageView) findViewById(R.id.take_photo);
        takeGallery = (ImageView) findViewById(R.id.select_gallery);
        atFrient = (ImageView) findViewById(R.id.at_friend);
        topic = (ImageView) findViewById(R.id.topic);

        takeVideo.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        takeGallery.setOnClickListener(this);
        atFrient.setOnClickListener(this);
        topic.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_video:
                startActivityForResult(new Intent(ShareActivity.this, RecordActivity.class), TAKE_RECORD);
                break;
            case R.id.take_photo:
                if (pictures.size() < 9) {
                    openCamera();
                } else {
                    Toast.makeText(this, "最多分享9张图片", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.select_gallery:
                if (pictures.size() < 9) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "最多分享9张图片", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.at_friend:
                startActivityForResult(new Intent(ShareActivity.this, AtActivity.class), AT_FRIENDS);
                break;
            case R.id.topic:
                Toast.makeText(ShareActivity.this, "topic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_weibo:
                Toast.makeText(ShareActivity.this, "share", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_RECORD:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra(IntentKey.VIDEO_PATH);
                    editText.setText(path);
                }
                break;
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (hasSdcard()) {
                        if (temp != null) {
                            uploadImage(temp.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case OPEN_ALBUM:
                if (resultCode == RESULT_OK) {
                    //TODO 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //TODO 4.4及以上系统使用这个方法处理图片
                        String path = handleImageOnKitKat(data);
                        uploadImage(path);
                    }
                }
                break;
            case AT_FRIENDS:
                if (resultCode == RESULT_OK) {
                    String friends = data.getStringExtra(IntentKey.AT_FRIEND);
                    editText.setText(editText.getText() + friends);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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
    private void openCamera() {
        //TODO 创建File的对象，用于存储拍照后图片的文件夹
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        temp = new File(dir, System.currentTimeMillis() + PHOTO_NAME);
        try {
            if (temp.exists()) {
                temp.delete();
            }
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(temp);
        } else {
            imageUri = FileProvider.getUriForFile(ShareActivity.this, "com.example.microblog.fileprovider", temp);
        }
        //TODO 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_CAMERA);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_ALBUM); //TODO 打开相册
    }

    /**
     * 上传图片到页面
     * @param path
     */
    private void uploadImage(final String path) {
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        Luban.with(this)
                .load(path)                //TODO 传人要压缩的图片列表
                .ignoreBy(100)             //TODO 忽略不压缩图片的大小
                .setTargetDir(IMAGE_DIR)  //TODO 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onSuccess(File file) {
                        photoPath(file.getAbsolutePath());
                        //TODO 删除拍照后的原图
                        if (temp != null) {
                            if (temp.exists()) {
                                temp.delete();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        pictures.add(map);
        adapter.notifyDataSetChanged();
    }

    private String  handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    /**
     * 获得图片的路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1 && grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this,"拒绝了权限：" + permissions[0],Toast.LENGTH_SHORT).show();
        }
    }
}