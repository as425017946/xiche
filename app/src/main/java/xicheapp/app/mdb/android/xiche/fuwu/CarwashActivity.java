package xicheapp.app.mdb.android.xiche.fuwu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.BuildConfig;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.TimeUtil;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.Utils.UiUtils;
import xicheapp.app.mdb.android.xiche.Utils.Utils2;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;
import xicheapp.app.mdb.android.xiche.login.ShenfenyanzhengActivity;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static xicheapp.app.mdb.android.xiche.fuwu.FuwuFragment.zhi;

/**
 * 洗车中
 */
public class CarwashActivity extends BaseActivity {
    Uri uri1,uri2,uri3,uri4,uri5,uri6,uri7,uri8;
    ArrayList<Uri> arrayList = new ArrayList<>();
    ArrayList<Uri> arrayList2 = new ArrayList<>();
    SharedPreferencesHelper sharehelper;
    private LoadingDailog loadBuilder;
    int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        sharehelper = new SharedPreferencesHelper(CarwashActivity.this,"xicheqishou");
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri1,1);
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri2,2);
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri3,3);
            }
        });
        img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri4,4);
            }
        });
        img_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri5,5);
            }
        });
        img_6.setVisibility(View.GONE);
        img_7.setVisibility(View.GONE);
        img_8.setVisibility(View.GONE);
        img_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri6,6);
            }
        });
        img_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri7,7);
            }
        });
        img_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upimg(uri8,8);
            }
        });


    }
    int b=0;
    private void upimg(Uri uri, final int zhizhi) {


        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CarwashActivity.this);
        LayoutInflater inflater = LayoutInflater.from(CarwashActivity.this);
        final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
        TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
        TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
        TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
        builder.setView(DialogView);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        //点击取消
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //拍照
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = zhizhi;
                onClickTakePhoto(view);//拍照
                dialog.dismiss();
            }
        });
        //图库
        tuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = zhizhi;
                onClickOpenGallery(view);//图库
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setinfo() {
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    @BindView(R.id.xiche_img1)
    ImageView img_1;
    @BindView(R.id.xiche_img2)
    ImageView img_2;
    @BindView(R.id.xiche_img3)
    ImageView img_3;
    @BindView(R.id.xiche_img4)
    ImageView img_4;
    @BindView(R.id.xiche_img5)
    ImageView img_5;
    @BindView(R.id.xiche_img6)
    ImageView img_6;
    @BindView(R.id.xiche_img7)
    ImageView img_7;
    @BindView(R.id.xiche_img8)
    ImageView img_8;
    private void setTtitle(){
        ttitle.setText("车况确认服务");
        ttitle2.setText("完成");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("长度1",zhi+"");
                if (Utils2.isFastClick()==true){
                    if (zhi==0){
                        if (uri1==null || uri2==null || uri3==null || uri4==null){
                            ToastUtils.shortToast("请上传车辆基本信息");
                        }else {
                            showMyDialog();
                            show1 = 1;
                            update1();
//                            beginupload(uri1,0);
                        }
                    }else if(zhi==1){
                        if (uri1==null || uri2==null || uri3==null || uri4==null){
                            ToastUtils.shortToast("请上传车辆基本信息");
                        }else {
                            showMyDialog();
                            update1();
//                            beginupload(uri1,0);
                        }
                    }
                }else {
                    ToastUtils.shortToast("请勿重复提交");
                }

            }
        });
    }

    int show1=1;
    int show2=0;
    private void showMyDialog(){
        if (loadBuilder==null){
            loadBuilder=new LoadingDailog.Builder(CarwashActivity.this)
                    .setMessage("信息上传中...")
                    .setCancelable(false)
                    .setCancelOutside(false).create();
            loadBuilder.show();
        }

    }
    private void dissDialog(){
        if (loadBuilder!=null){
            loadBuilder.dismiss();
        }
    }
    private void update1(){
        if (uri1!=null && show1 ==1){
            beginupload(uri1, 1);
        }else if (uri2!=null && show1 ==2){
            beginupload(uri1, 2);
        }else if (uri3!=null && show1 ==3){
            beginupload(uri1, 3);
        }else if (uri4!=null && show1 ==4){
            beginupload(uri1, 4);
        }else if (uri5!=null && show1 ==5){
            beginupload2(uri1, 5);
        }else if (uri6!=null && show1 ==6){
            beginupload2(uri1, 6);
        }else if (uri7!=null && show1 ==7){
            beginupload2(uri1, 7);
        }else if (uri8!=null && show1 ==8){
            beginupload2(uri1, 8);
        }else {
            if (zhi==0){
                update2();
            }else if (zhi==1){
                update3();
            }
        }
    }


    private void update2(){

        Log.e("上传图",cardimgs+"**"+cardendimgs+"----"+getIntent().getStringExtra("orderid"));
        OkGo.post(Api.starcar)
                .tag(this)
                .params("orderUUID",getIntent().getStringExtra("orderid"))
                .params("startImg",cardimgs)
                .params("startBadImg",cardendimgs)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("上传",s);
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==0){

                            dissDialog();
                            ToastUtils.shortToast(tongyongBean.getMessage());
                        }else {

                            dissDialog();
                            zhi = 1;
                            CarwashActivity.this.finish();
                            ToastUtils.shortToast("上传成功");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        dissDialog();
                        ToastUtils.shortToast(e+"");
                    }
                });
    }
    private void update3(){

        Log.e("上传图2",cardimgs+"**"+cardendimgs+"uuid"+getIntent().getStringExtra("orderid")
        +"manLng"+getIntent().getStringExtra("lon")+"manLat"+getIntent().getStringExtra("lat")
        +"serviceManId"+sharehelper.getSharedPreference("id","").toString());


        OkGo.post(Api.endcar)
                .tag(this)
                .params("orderUUID",getIntent().getStringExtra("orderid"))
                .params("endImg",cardimgs)
                .params("endBadImg",cardendimgs)
                .params("manLng",getIntent().getStringExtra("lon"))
                .params("manLat",getIntent().getStringExtra("lat"))
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("上传",s);
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==0){
                            dissDialog();
                            ToastUtils.shortToast(tongyongBean.getMessage());
                        }else {
                            zhi = 2;
                            Log.e("展示信息","22");
                            dissDialog();
                            CarwashActivity.this.finish();
                            ToastUtils.shortToast("上传成功");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        dissDialog();
                        ToastUtils.shortToast(e+"");
                    }
                });

    }


    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarwashActivity.this.finish();
            }
        });
    }
    /**
     * 授权照相机代码
     */

    //打开相机的返回码
    private static final int CAMERA_REQUEST_CODE = 1;
    //选择图片的返回码
    private static final int IMAGE_REQUEST_CODE = 2;
    //剪切图片的返回码
    public static final int CROP_REREQUEST_CODE = 3;
//    private ImageView iv;

    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 100;

    public static final int REQUEST_CODE_PERMISSION_GALLERY = 101;

    //照片图片名
    private String photo_image;
    //截图图片名
    private String crop_image;

    //拍摄的图片的真实路径
    private String takePath;
    //拍摄的图片的虚拟路径
    private Uri imageUri;
    private Uri cropUri;
    //    private File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    /**
     * 拍照
     *
     * @param view
     */
    public void onClickTakePhoto(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(REQUEST_CODE_PERMISSION_CAMERA);
            return;
        }
        openCamera();
    }

    private void openCamera() {
        if (isSdCardExist()) {
            Intent cameraIntent = new Intent(
                    "android.media.action.IMAGE_CAPTURE");

            photo_image = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date()) + ".jpg";
            imageUri = getImageUri(photo_image);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT
                    , imageUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 打开图库
     * 不需要用FileProvider
     *
     * @param view
     */
    public void onClickOpenGallery(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(REQUEST_CODE_PERMISSION_GALLERY);
            return;
        }
        openGallery();
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    /**
     * @param path 原始图片的路径
     */
    public void cropPhoto(String path) {
        crop_image = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date()) + "_crop" +
                ".jpg";
        File cropFile = createFile(crop_image);
        File file = new File(path);


        Intent intent = new Intent("com.android.camera.action.CROP");
        //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider", file);
            cropUri = Uri.fromFile(cropFile);
            //TODO:cropUri 是裁剪以后的图片保存的地方。也就是我们要写入此Uri.故不需要用FileProvider
            //cropUri = FileProvider.getUriForFile(getApplicationContext(),
            //    BuildConfig.APPLICATION_ID + ".provider", cropFile);
        } else {
            imageUri = Uri.fromFile(file);
            cropUri = Uri.fromFile(cropFile);
        }

        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
//        //设置宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        intent.putExtra("scale", true);
        //裁剪成功以后保存的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REREQUEST_CODE);


    }


    /**
     * 获得一个uri。该uri就是将要拍摄的照片的uri
     *
     * @return
     */
    private Uri getImageUri(String name) {
        if (isSdCardExist()) {
            File file = createFile(name);
            if (file != null) {
                takePath = file.getAbsolutePath();
                Log.e("zmm", "图片的路径---》" + takePath);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return FileProvider.getUriForFile(getApplicationContext(),
                            BuildConfig.APPLICATION_ID + ".provider", file);
                } else {
                    return Uri.fromFile(file);
                }

            }
        }
        return Uri.EMPTY;
    }

    public File createFile(String name) {
        if (isSdCardExist()) {
            File[] dirs = ContextCompat.getExternalFilesDirs(this, null);
            if (dirs != null && dirs.length > 0) {
                File dir = dirs[0];
                return new File(dir, name);
            }
        }

        return null;
    }

    Uri shangchaunImg;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE://拍照成功并且返回
                    Log.e("zmm", "选择的图片的虚拟地址是------------>" +takePath);
                    cropPhoto(takePath);  //注视后不裁剪图，释放就裁剪
////                    cropPhoto(getMediaUriFromPath(this,takePath),false);
//                    decodeImage(imageUri,b);  //需要裁剪把此行注释
                    break;

                case IMAGE_REQUEST_CODE://选择图片成功返回
                    if (data != null && data.getData() != null) {
                        imageUri = data.getData();
//
//                        cropPhoto(imageUri,true); //注视后不裁剪图，释放就裁剪
                        decodeImage(imageUri,b); //需要裁剪把此行注释
                    }
                    break;
                case CROP_REREQUEST_CODE:
                    Log.e("zmm", "裁剪以后的地址是------------>" + cropUri);
                    shangchaunImg  = cropUri;
                    decodeImage(cropUri,b);
                    break;
            }
        }
    }
    // 图片裁剪
    private void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);

//        // 设置裁剪区域的宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);


        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        if (fromCapture) {
            // 如果是使用拍照，那么原先的uri和最终目标的uri一致
            cropUri = uri;
        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo", fileName + ".jpeg");
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            cropUri = getUriForFile(this, mCutFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        Toast.makeText(this, "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);

        startActivityForResult(intent, CROP_REREQUEST_CODE); //设置裁剪参数显示图片至ImageVie
    }
    // 从file中获取uri
    // 7.0及以上使用的uri是contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0使用的uri为file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 根据uri拿到bitmap
     *
     * @param imageUri 这个Uri是
     */
    private void decodeImage(final Uri imageUri,int c) {
        try {
            Bitmap bitmapFormUri = getBitmapFormUri(this, imageUri);
            if (c==1){
                arrayList.add(imageUri);
                img_1.setImageBitmap(bitmapFormUri);
                uri1 = imageUri;
            }else  if (c==2){
                arrayList.add(imageUri);
                img_2.setImageBitmap(bitmapFormUri);
                uri2 = imageUri;
            }else  if (c==3){
                arrayList.add(imageUri);
                img_3.setImageBitmap(bitmapFormUri);
                uri3 = imageUri;
            }else  if (c==4){
                arrayList.add(imageUri);
                img_4.setImageBitmap(bitmapFormUri);
                uri4 = imageUri;
            }else  if (c==5){
                arrayList2.add(imageUri);
                img_5.setImageBitmap(bitmapFormUri);
                uri5 = imageUri;
                img_6.setVisibility(View.VISIBLE);
            }else  if (c==6){
                arrayList2.add(imageUri);
                img_6.setImageBitmap(bitmapFormUri);
                uri6 = imageUri;
                img_7.setVisibility(View.VISIBLE);
            }else  if (c==7){
                arrayList2.add(imageUri);
                img_7.setImageBitmap(bitmapFormUri);
                uri7 = imageUri;
                img_8.setVisibility(View.VISIBLE);
            }else  if (c==8){
                arrayList2.add(imageUri);
                img_8.setImageBitmap(bitmapFormUri);
                uri8 = imageUri;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /**
     * 检查权限
     *
     * @param requestCode
     */
    private void checkPermission(int requestCode) {

        boolean granted = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission_group.CAMERA);
        if (granted) {//有权限
            if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
                openCamera();//打开相机
            } else {
                openGallery();//打开图库
            }
            return;
        }
        //没有权限的要去申请权限
        //注意：如果是在Fragment中申请权限，不要使用ActivityCompat.requestPermissions,
        // 直接使用Fragment的requestPermissions方法，否则会回调到Activity的onRequestPermissionsResult
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest
                        .permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean flag = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PERMISSION_GRANTED) {
                    flag = false;
                    break;
                }
            }
            //权限通过以后。自动回调拍照
            if (flag) {
                if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
                    openCamera();//打开相机
                } else {
                    openGallery();//打开图库
                }
            } else {
                Toast.makeText(this, "请开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 检查SD卡是否存在
     */
    public boolean isSdCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    String cardimgs="",cardendimgs="";
    /**上传图片**/
    public void beginupload(Uri bitmap, final int i) {

        Log.e("传递值",bitmap+"");
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        final String objectname =df.format(new Date())+i+ ".png";

        final String url = startpoint +"."+ endpoint+"/"+ objectname;

        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(CarwashActivity.this,bitmap) );
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

//                arrayList.remove(i);
                Log.e("测试图片"+i,"https://washcarimg.oss-cn-beijing.aliyuncs.com/"+objectname);
                cardimgs+=objectname+",";
                show1++;
                update1();

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                ToastUtils.shortToast("图片上传失败导致信息无法发布");

                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    ToastUtils.shortToast("图片上传失败导致信息无法发布");
                }
                if (serviceException != null) {
                }
            }
        });

    }
    public void beginupload2(Uri bitmap, final int i) {

        Log.e("传递值",bitmap+"");
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        final String objectname =df.format(new Date())+i+".png";


        final String url = startpoint +"."+ endpoint+"/"+ objectname;

        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(CarwashActivity.this,bitmap) );
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

//                arrayList2.remove(i);
                Log.e("测试图片2","https://washcarimg.oss-cn-beijing.aliyuncs.com/"+objectname);
                cardendimgs+=objectname+",";
                show1++;
                update1();
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                ToastUtils.shortToast("图片上传失败导致信息无法发布");

                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    ToastUtils.shortToast("图片上传失败导致信息无法发布");
                }
                if (serviceException != null) {
                }
            }
        });

    }

}
