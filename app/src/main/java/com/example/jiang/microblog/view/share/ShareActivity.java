package com.example.jiang.microblog.view.share;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.view.activity.MainActivity;
import com.example.jiang.microblog.view.share.adapter.GridViewImageAdapter;
import com.example.jiang.microblog.view.share.at.AtActivity;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.web.WeiboPageUtils;
import com.zhy.changeskin.SkinManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.example.jiang.microblog.R.id.share_weibo;


public class ShareActivity extends BaseActivity implements View.OnClickListener ,WbShareCallback{

    public static final int TAKE_RECORD = 0;
    public static final int TAKE_CAMERA = 1;
    public static final int OPEN_ALBUM = 2;
    public static final int AT_FRIENDS = 3;

    WbShareHandler shareHandler = new WbShareHandler(ShareActivity.this);
    private AuthInfo mAuthInfo;
    private WeiboPageUtils pageUtils;

    //TODO　压缩后图片的存储位置
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/microblog/img/";
    //TODO 临时图片的文件名后缀
    private final String PHOTO_NAME = "temp.jpg";

    private EditText editText;
    private TextView charCount;

    private ImageView takeVideo;
    private ImageView takePhoto;
    private ImageView takeGallery;
    private ImageView atFrient;
    private ImageView topic;
    private ImageView shareWeibo;

    private GridView gridView;
    private GridViewImageAdapter adapter;

    private List<Map<String, Object>> pictures = new ArrayList<>();

    private File temp;
    private Uri imageUri;
    private String videoPath = "";

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
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
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        WbSdk.install(this, mAuthInfo);
        shareHandler.registerApp();
        pageUtils = WeiboPageUtils.getInstance(ShareActivity.this, mAuthInfo);
        initView();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.photo_gridview);
        adapter = new GridViewImageAdapter(this, pictures);
        gridView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.share_content);
        charCount = (TextView) findViewById(R.id.char_count);
        editText .addTextChangedListener(watcher);
        takeVideo = (ImageView) findViewById(R.id.take_video);
        takePhoto = (ImageView) findViewById(R.id.take_photo);
        takeGallery = (ImageView) findViewById(R.id.select_gallery);
        atFrient = (ImageView) findViewById(R.id.at_friend);
        topic = (ImageView) findViewById(R.id.topic);
        shareWeibo = (ImageView) findViewById(share_weibo);

        takeVideo.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        takeGallery.setOnClickListener(this);
        atFrient.setOnClickListener(this);
        topic.setOnClickListener(this);
        shareWeibo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_video:
                if (pictures.isEmpty()) {
                    startActivityForResult(new Intent(ShareActivity.this, RecordActivity.class), TAKE_RECORD);
                } else {
                    Toast.makeText(this, "不能同时分享图片和视频", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.take_photo:
                if (videoPath == null || videoPath.equals("")) {
                    if (pictures.size() < 9) {
                        openCamera();
                    } else {
                        Toast.makeText(this, "最多分享9张图片", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "不能同时分享图片和视频", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.select_gallery:
                if (videoPath == null || videoPath.equals("")) {
                    if (pictures.size() < 9) {
                        openAlbum();
                    } else {
                        Toast.makeText(this, "最多分享9张图片", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "不能同时分享图片和视频", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.at_friend:
                startActivityForResult(new Intent(ShareActivity.this, AtActivity.class), AT_FRIENDS);
                break;
            case R.id.topic:
                pageUtils.shareToWeibo("");
                break;
            case share_weibo:
                Toast.makeText(ShareActivity.this, "share", Toast.LENGTH_SHORT).show();
                String text = String.valueOf(editText.getText());
                shareWeiboByH5(text,pictures,videoPath);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_RECORD:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra(IntentKey.VIDEO_PATH);
                    videoPath = path;
                    Log.e("视频路径：", videoPath);
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
                    SpannableStringBuilder highlight = TextColorTools.highlight(editText.getText() + friends, friends);
                    editText.setText(highlight);
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

    /**
     * 字数监听
     */
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 140) {
                s.delete(140, s.length());
            }
            int num = 140 - s.length();
            charCount.setText(String.valueOf(num));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//TODO ------------------------------------------------------------------------------------------------------
    @Override
    public void onWbShareSuccess() {
        startActivity(new Intent(ShareActivity.this, MainActivity.class));
    }
    @Override
    public void onWbShareCancel() {

    }
    @Override
    public void onWbShareFail() {

    }
//TODO ------------------------------------------------------------------------------------------------------

    /**
     *  第三方应用发送请求消息到微博，唤起微博分享界面。
     * @param text
     * @param pictures
     * @param videoPath
     */
    private void shareWeiboByH5(String text, List<Map<String, Object>> pictures, String videoPath) {

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (text != null || !text.equals("")) {
            weiboMessage.textObject = getTextObj(text);
        }
        if (pictures.size() == 1) {
            weiboMessage.imageObject = getImageObj(pictures);
        }
        if(pictures.size() > 1){
            weiboMessage.multiImageObject = getMultiImageObject(pictures);
        }
        if (videoPath != null || !videoPath.equals("")) {
            Log.e("videoPath", "111111111");
            weiboMessage.videoSourceObject = getVideoObject(videoPath);
        }
        shareHandler.shareMessage(weiboMessage, false);
    }
    /**
     * 获取分享的文本模板。
     */
    private String getSharedText(String str) {
        int formatId = R.string.weibosdk_demo_share_text_template;
        String format = getString(formatId);
        String text = format;
        text = str;
        return text;
    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText(text);
        textObject.title = "";
        textObject.actionUrl = "http://www.sina.com";
        return textObject;
    }
    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     * @param pictures
     */
    private ImageObject getImageObj(List<Map<String, Object>> pictures) {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap  = BitmapFactory.decodeFile(pictures.get(0).get("path").toString());
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /***
     * 创建多图
     * @return
     * @param pictures
     */
    private MultiImageObject getMultiImageObject(List<Map<String, Object>> pictures){
        MultiImageObject multiImageObject = new MultiImageObject();
        //pathList设置的是本地本件的路径,并且是当前应用可以访问的路径，
        // 现在不支持网络路径（多图分享依靠微博最新版本的支持，所以当分享到低版本的微博应用时，多图分享失效
        // 可以通过WbSdk.hasSupportMultiImage 方法判断是否支持多图分享,h5分享微博暂时不支持多图）
        // 多图分享接入程序必须有文件读写权限，否则会造成分享失败
        boolean b = WbSdk.supportMultiImage(this);

        if (b) {
            Log.e("yyyyyy", "yyyyyy");
        } else {
            Log.e("nnnnnn", "nnnnnn");
        }

        ArrayList<Uri> pathList = new ArrayList<Uri>();
        for (int i = 0; i < pictures.size(); i++) {
            pathList.add(Uri.fromFile(new File(pictures.get(i).get("path").toString())));
        }
        return multiImageObject;
    }

    private VideoSourceObject getVideoObject(String videoPath) {
        Log.e("videoPath", "2222222222");
        VideoSourceObject videoSourceObject = new VideoSourceObject();
        videoSourceObject.videoPath = Uri.fromFile(new File(videoPath));
        File file = new File(videoPath);
        if (file.exists()) {
            Log.e("videoPath", videoSourceObject.videoPath.toString());
        } else {
            Log.e("videoPath", "NNNNNNNNNNN");
        }
        return videoSourceObject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}