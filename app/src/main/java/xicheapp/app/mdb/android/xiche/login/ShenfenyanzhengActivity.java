package xicheapp.app.mdb.android.xiche.login;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import xicheapp.app.mdb.android.xiche.wode.EditActivity;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * 身份验证起始页
 */
public class ShenfenyanzhengActivity extends BaseActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenfenyanzheng);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(ShenfenyanzhengActivity.this,"xicheqishou");
        status = getIntent().getStringExtra("status");
        setTtitle();
        fanhui();
        setinfo();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("身份验证");
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
//        img.setVisibility(View.INVISIBLE);
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShenfenyanzhengActivity.this.finish();
            }
        });
    }
    /**
     * 填写信息上传
     */
    @BindView(R.id.sfyz_next)
    Button btn_next;
    @BindView(R.id.sfyz_name)
    EditText edt_name;
    @BindView(R.id.sfyz_sfcard)
    EditText edt_card;
    @BindView(R.id.sfyz_phone)
    EditText edt_phone;
    @BindView(R.id.sfyz_weixinhao)
    EditText edt_email;
    @BindView(R.id.sfyz_zhengmian)
    ImageView img_zhengmian;
    @BindView(R.id.sfyz_fanmian)
    ImageView img_fanmian;
    Uri img1,img2;//保存用户上传的身份证正面和背面
    int a=0; //保存用户是否成功上传了身份证
    int b = 0; //用于显示哪个身份证的照片
    String cardimgs=""; //保存身份证上传oss后接受的值
    private void setinfo(){
        //照片点击
        img_zhengmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShenfenyanzhengActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShenfenyanzhengActivity.this);
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
                        b = 1;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b = 1;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        img_fanmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShenfenyanzhengActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShenfenyanzhengActivity.this);
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
                        b = 2;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b = 2;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        //提交按钮
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4秒内只记录一次，以防多次点击多次提交
                if (Utils2.isFastClick()==true){
                    if (TextUtils.isEmpty(edt_name.getText().toString())){
                        ToastUtils.shortToast("请输入姓名");
                    }else {
                        if (TextUtils.isEmpty(edt_card.getText().toString())){
                            ToastUtils.shortToast("请输入身份证号");
                        }else {
                            if (Utils2.isIdCard(edt_card.getText().toString())==false){
                                ToastUtils.shortToast("请输入正确的身份证号码");
                            }else {
                                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                                    ToastUtils.shortToast("请输入联系电话");
                                }else {
                                    if (Utils2.isCellphone(edt_phone.getText().toString())==false){
                                        ToastUtils.shortToast("请输入正确的手机号");
                                    }else {
                                        if (img1==null){
                                            ToastUtils.shortToast("请上传身份证正面");
                                        }else {
                                            if (img2==null){
                                                ToastUtils.shortToast("请上传身份证反面");
                                            }else {
                                                //邮箱可以为空，所以邮箱为空的时候直接上传信息，不需要判断邮箱格式
                                                if (TextUtils.isEmpty(edt_email.getText().toString())){
                                                    Log.e("图片地址",img1+"***"+img2);
                                                    setupinfoapi();
                                                }else {
                                                    if (Utils2.isEmail(edt_email.getText().toString())==false){
                                                        ToastUtils.shortToast("请输入正确的邮箱");
                                                    }else {
                                                        Log.e("图片地址2",img1+"***"+img2);
                                                        setupinfoapi();
                                                    }
                                                }


                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

            }
        });
    }
    /**
     * 请求上传信息接口
     */
    private void setupinfoapi(){

        beginupload(img1);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginupload(img2);
            }
        },500);

        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(ShenfenyanzhengActivity.this)
                .setMessage("信息上传中...")
                .setCancelable(false)
                .setCancelOutside(false);
        final LoadingDailog dialog=loadBuilder.create();
        dialog.show();
        //先做个延迟，目的就是为了先等照片上传后再上传本地服务器信息
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("上传骑手信息","cardImg"+cardimgs);
                OkGo.post(Api.wanshanziliao)
                        .tag(this)
                        .params("name",edt_name.getText().toString())
                        .params("idCard",edt_card.getText().toString())
                        .params("mail",edt_email.getText().toString())
                        .params("contactMobile",edt_phone.getText().toString())
                        .params("cardImg",cardimgs)
                        .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                        .params("status",status)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Gson gson = new Gson();
                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                if (tongyongBean.getState()==1){
                                    dialog.dismiss();
//                                    Intent intent = new Intent(ShenfenyanzhengActivity.this,BankActivity.class);
//                                    startActivity(intent);
//                                    ShenfenyanzhengActivity.this.finish();
                                }else {
                                    dialog.dismiss();
                                    ToastUtils.shortToast(tongyongBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.shortToast(e+"");
                                dialog.dismiss();
                            }
                        });

//                if (a==2){
//                }else {
//                    dialog.dismiss();
//                    ToastUtils.shortToast("网络不稳定，导致无法提交信息，请稍后重试");
//                }

            }
        },3000);

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
                    cropPhoto(takePath);
////                    cropPhoto(getMediaUriFromPath(this,takePath),false);
//                    decodeImage(imageUri,b);
                    break;

                case IMAGE_REQUEST_CODE://选择图片成功返回
                    if (data != null && data.getData() != null) {
                        imageUri = data.getData();

//                        cropPhoto(imageUri,true);
                        decodeImage(imageUri,b);
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
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);

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
                img_zhengmian.setImageBitmap(bitmapFormUri);
                img1 = imageUri;
            }else {
                img_fanmian.setImageBitmap(bitmapFormUri);
                img2 = imageUri;
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

    /**上传图片**/
    public void beginupload(Uri bitmap) {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        final String objectname = df.format(new Date())+ ".png";

        final String url = startpoint +"."+ endpoint+"/"+ objectname;


        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(ShenfenyanzhengActivity.this,bitmap) );
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.e("测试图片1","https://washcarimg.oss-cn-beijing.aliyuncs.com/"+objectname);
                cardimgs+=objectname+",";
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
