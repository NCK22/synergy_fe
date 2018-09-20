  package com.nyasa.synergyfieldengineer.Fragment;


import android.Manifest;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Interface.PhotoTabAddInterface;
import com.nyasa.synergyfieldengineer.Interface.PhotoTabGetInterface;
import com.nyasa.synergyfieldengineer.Interface.PropertyTabGetInterface;
import com.nyasa.synergyfieldengineer.Interface.WorkTabAddInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupInterface;
import com.nyasa.synergyfieldengineer.Interface.uploadPhotoInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class TabUploadPhoto extends Fragment implements View.OnClickListener {

    private TextView textViewRegister;
    private TextInputEditText editTextDescription;
    private TextInputLayout textInputLayoutUsername;
  //  private AuthenticationActivity authenticationActivity;
    private Object[] objects;
    public static File file;
    private Uri mCameraFileUri;
    Bitmap bitmap;
    File imageFile;
    ImageView imgPayProof;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    /**
     * FCM_ID
     */
    private String token = "0",FilePathStr;
    private static final int CAMERA_CAPTURE_CODE = 501;
    public  static final int RequestPermissionCode  = 1;
   // private MainActivity mainActivity;
    ProgressDialog progressDialog;
    String flagImg="0";
    Boolean photoExist=false;
    Button btnSubmit;
    String strUploadPath="",strFileName="",strSystemFileName="",strCaption="",strPhotoType="",case_id="";
    ArrayList<ImageView> list_frames=new ArrayList<ImageView>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  mainActivity = (MainActivity) getActivity();

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        //  getActivity().getActionBar().setTitle("Sign in");
    }

    public TabUploadPhoto() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //imgCapture=(ImageView)getActivity().findViewById(R.id.img_profile_pic);

/*
        getActivity().findViewById(R.id.img_profile_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission
                        (getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                        ContextCompat.checkSelfPermission
                                (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)

                {
                    Log.e("permission","denied");

                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, RequestPermissionCode);
                    captureImage();
                    // Printing toast message after enabling runtime permission.
                    //   Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

                } else {



                    // captureImage();
                   // captureImage();

                    chooseImage();

                }

            }
        });*/

   /*     getActivity().findViewById(R.id.img_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission
                        (getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                        ContextCompat.checkSelfPermission
                                (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)

                {
                    Log.e("permission","denied");

                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, RequestPermissionCode);
                    captureImage();
                    // Printing toast message after enabling runtime permission.
                    //   Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

                } else {

                    captureImage();
                    //chooseImage();

                }
            }
        });*/

    }

    private void captureImage() {

        try {
            file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
           mCameraFileUri = FileProvider.getUriForFile(getActivity(),
                    getActivity().getApplicationContext().getPackageName() + ".provider", file);

           /* file = FileStorage.getInstance().getTemporaryFile(System.currentTimeMillis());
            mCameraFileUri = Uri.fromFile(file);*/
            Log.e("mCameraFileUri inside capture", String.valueOf(mCameraFileUri));
            Log.e("filename",file.getName());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraFileUri);
            // intent.putExtra(MediaStore.EXTRA_OUTPUT,mCameraFileUri);
            intent.putExtra("camera", mCameraFileUri);
            intent.putExtra("return-data", false);
            List<ResolveInfo> resolvedIntentActivities = getActivity().getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;
                getActivity().getApplicationContext().grantUriPermission(packageName, mCameraFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            /*intent.addFlags(intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(intent.FLAG_GRANT_READ_URI_PERMISSION);*/
            startActivityForResult(intent, CAMERA_CAPTURE_CODE);

           /* cameraImageFile = FileStorage.getInstance().getTemporaryFile(System.currentTimeMillis());
            mImageCaptureUri = Uri.fromFile(cameraImageFile);
            try {
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, IConstants.REQUEST_CODE_CLICK_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Synergy");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir();
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + getFileName() + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    private String getFileName() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == CAMERA_CAPTURE_CODE){
                try {
                    if(mCameraFileUri==null) {
                        Toast.makeText(getActivity(),"Please free up space to use Camera",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCameraFileUri);
                        if (bitmap != null) {
                            if(flagImg.equalsIgnoreCase("1"))
                                img1.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("2"))
                                img2.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("3"))
                                img3.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("4"))
                                img4.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("5"))
                                img5.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("6"))
                                img6.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("7"))
                                img7.setImageBitmap(bitmap);
                            if(flagImg.equalsIgnoreCase("8"))
                                img8.setImageBitmap(bitmap);
                            imageFile = new File(mCameraFileUri.toString());

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 200) {
                String docPath="";

                Uri selectedImage = data.getData();
                Log.e("getPath",getPath(selectedImage));

                ContentResolver cr = getActivity().getContentResolver();
                String mime = cr.getType(selectedImage);
                Log.e("Data type",mime);
                Log.e("Data type", String.valueOf(mime.contains("image")));
                if(mime.contains("image")) {
                    imageFile = new File(getRealPathFromURI(selectedImage));
                    file = imageFile;
                    if (imageFile == null)
                        Log.e("imagefile", "null");
                    else
                        Log.e("imagefile", String.valueOf(imageFile));
                    Log.e("image before", String.valueOf(imageFile.length()));


                    bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        imgPayProof.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    docPath = String.valueOf(data.getData());
                    Log.e("docPath", docPath);
                }
            }
        }
    }

    private String getPath(Uri uri) {
        String[]  data = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getActivity(), uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void uploadFile() {

        progressDialog.show();
     //   User user = User.getInstance();
   //    RequestBody requestBody = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(mCameraFileUri)),file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("example", imageFile.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("JPEG/PNG"), file.getName());
    //    RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), user.getUserID());
     //   RequestBody description = RequestBody.create(MediaType.parse("text/plain"), editTextDescription.getText().toString());
        uploadPhotoInterface getResponse = APIClient.getClient().create(uploadPhotoInterface.class);
        Call<HashMap<String,String>> call = getResponse.uploadFile(fileToUpload, filename, null);
        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, retrofit2.Response<HashMap<String,String>> response) {

                HashMap<String,String> commonParentPojo = response.body();
              //  Log.e("ServerResponse", commonParentPojo.getMessage());
                if (commonParentPojo != null) {
                    if(commonParentPojo.get("message").contains("successfully")) {
                        strUploadPath = commonParentPojo.get("url");
                        strFileName = commonParentPojo.get("filename");
                        Log.e("strFileName",strFileName);
                        progressDialog.dismiss();
                        addPhotoDetails(case_id);
                    }
                //    Log.e("response", commonParentPojo.getMessage());
              /*      if (commonParentPojo.g().equalsIgnoreCase("1")) {
                        //  strResumePath=serverResponse.getMessage();
                        Log.e("Success Response", commonParentPojo.getMsg());
                        Toast.makeText(getActivity(),  commonParentPojo.getMsg(), Toast.LENGTH_SHORT).show();
                        editTextDescription.setText("");
                        imgPayProof.setImageResource(R.drawable.ic_pay_proof);
                    }*/
                }
             //   progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("throwbale", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {

        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                thePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return thePath;
    }


    private boolean isValidForm() {
       /* if (new Validations().isBlank(editTextDescription)) {
            textInputLayoutUsername.setError(IErrors.CANNOT_BLANK);
            return false;
        }  else {
            return true;
        }*/
       return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.tab_upload_photo, container, false);

        tv1=(TextView)rootView.findViewById(R.id.tv_img1); tv2=(TextView) rootView.findViewById(R.id.tv_img2);
        tv3=(TextView) rootView.findViewById(R.id.tv_img3); tv4=(TextView)rootView.findViewById(R.id.tv_img4);
        tv5=(TextView) rootView.findViewById(R.id.tv_img5); tv6=(TextView) rootView.findViewById(R.id.tv_img6);
        tv7=(TextView) rootView.findViewById(R.id.tv_img7); tv8=(TextView) rootView.findViewById(R.id.tv_img8);

        img1=(ImageView)rootView.findViewById(R.id.img_1); img2=(ImageView)rootView.findViewById(R.id.img_2);
        img3=(ImageView)rootView.findViewById(R.id.img_3); img4=(ImageView)rootView.findViewById(R.id.img_4);
        img5=(ImageView)rootView.findViewById(R.id.img_5); img6=(ImageView)rootView.findViewById(R.id.img_6);
        img7=(ImageView)rootView.findViewById(R.id.img_7); img8=(ImageView)rootView.findViewById(R.id.img_8);
        btnSubmit=(Button)rootView.findViewById(R.id.btn_submit_photo);

        img1.setOnClickListener(this);img2.setOnClickListener(this);
        img3.setOnClickListener(this);img4.setOnClickListener(this);
        img5.setOnClickListener(this);img6.setOnClickListener(this);
        img7.setOnClickListener(this);img8.setOnClickListener(this);


        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");

        EnableRuntimePermissionToAccessCamera();

        rootView.findViewById(R.id.btn_submit_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFile();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


//        getActivity().getIntent().getExtras().getString("txtcust");
//        getActivity().getIntent().getExtras().getString("txtdea");
//        Log.e("Login\ncustomer\ndealer", getActivity().getIntent().getExtras().getString("txtcust")+"\n"
//                +getActivity().getIntent().getExtras().getString("txtdea"));
//        if(HomeAuthenticationFragment.txtdealer.equals("Dealer Login")){
//            authenticationActivity.setTitle("Dealer Login");
//        } else {
//            authenticationActivity.setTitle("Customer Login");
//        }
    }

    public void EnableRuntimePermissionToAccessCamera(){

        // progressDialog.show();
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA) &&
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) &&
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) &&
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED))
        {

            //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

            // Printing toast message after enabling runtime permission.
            //   Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, RequestPermissionCode);

        }
        // progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.img_1:flagImg="1";
            strCaption=tv1.getText().toString();
            strPhotoType="internal";
            break;

            case R.id.img_2:flagImg="2";
            strCaption=tv2.getText().toString();
            strPhotoType="internal";
                break;

            case R.id.img_3:flagImg="3";
            strCaption=tv3.getText().toString();
            strPhotoType="internal";
                break;

            case R.id.img_4:flagImg="4";
            strCaption=tv4.getText().toString();
            strPhotoType="external";
                break;

            case R.id.img_5:flagImg="5";
            strCaption=tv5.getText().toString();
            strPhotoType="internal";
                break;

            case R.id.img_6:flagImg="6";
            strCaption=tv6.getText().toString();
            strPhotoType="external";
                break;

            case R.id.img_7:flagImg="7";
                strCaption=tv7.getText().toString();
                strPhotoType="internal";
                break;

            case R.id.img_8:flagImg="8";
                strCaption=tv8.getText().toString();
                strPhotoType="external";
                break;

        }
        if (ContextCompat.checkSelfPermission
                (getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission
                        (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)

        {
            Log.e("permission","denied");

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, RequestPermissionCode);
            captureImage();
            // Printing toast message after enabling runtime permission.
            //   Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            captureImage();
            //chooseImage();

        }

    }

    public void getBuildingDetails(final String case_id){

        progressDialog.show();
        PhotoTabGetInterface getResponse = APIClient.getClient().create(PhotoTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getPhotos(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    photoExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                    }
                }
                else
                    photoExist=false;
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addPhotoDetails(final String case_id){

        String[] extension=strFileName.split("\\.");
        Log.e("strFileName",strFileName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PhotoTabAddInterface getResponse = APIClient.getClient().create(PhotoTabAddInterface.class);
        Call<HashMap<String,String>> call=null;
        if(photoExist==true) {
            call = getResponse.updatePhoto(strFileName,
                    strFileName,
                    extension[1],
                    strUploadPath,
                    date,
                    SPUserProfile.getSpInstance().getUser_id(),
                    "N",
                    strCaption,
                    String.valueOf(mCameraFileUri),
                    "PhotoImage",
                    strPhotoType,case_id);
        }
        else{
            call = getResponse.addPhoto(strFileName,
                    strFileName,
                    extension[1],
                    strUploadPath,
                    date,
                    SPUserProfile.getSpInstance().getUser_id(),
                    "N",
                    strCaption,
                    String.valueOf(mCameraFileUri),
                    "PhotoImage",
                    strPhotoType,case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {



                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void disableAll(int id)
    {
        for(int i=0;i<list_frames.size();i++)
        {
            if(list_frames.get(i).getId()!=id)
            list_frames.get(i).setEnabled(false);
        }
    }
}