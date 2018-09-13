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
  import android.os.Bundle;
  import android.os.Environment;
  import android.provider.MediaStore;
  import android.support.annotation.Nullable;
  import android.support.design.widget.TextInputEditText;
  import android.support.design.widget.TextInputLayout;
  import android.support.v4.app.ActivityCompat;
  import android.support.v4.app.Fragment;
  import android.support.v4.content.ContextCompat;
  import android.support.v4.content.FileProvider;
  import android.util.Log;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.ImageView;
  import android.widget.TextView;
  import android.widget.Toast;

  import com.nyasa.synergyfieldengineer.APIClient;
  import com.nyasa.synergyfieldengineer.Interface.PhotoTabAddInterface;
  import com.nyasa.synergyfieldengineer.Interface.PhotoTabGetInterface;
  import com.nyasa.synergyfieldengineer.R;
  import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

  import org.json.JSONObject;

  import java.io.File;
  import java.io.FileNotFoundException;
  import java.io.IOException;
  import java.text.SimpleDateFormat;
  import java.util.ArrayList;
  import java.util.Date;
  import java.util.HashMap;
  import java.util.List;
  import java.util.Locale;

  import retrofit2.Call;
  import retrofit2.Callback;
  import retrofit2.Response;

  import static android.app.Activity.RESULT_OK;
  import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

  /**
   * A simple {@link Fragment} subclass.
   */
  @SuppressWarnings("ALL")
  public class TabUploadDoc extends Fragment implements View.OnClickListener {

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
      ImageView img1,img2,img3,img4,img5,img6;
      /**
       * FCM_ID
       */
      private String token = "0",FilePathStr;
      private static final int CAMERA_CAPTURE_CODE = 501;
      public  static final int RequestPermissionCode  = 1 ;
     // private MainActivity mainActivity;
      ProgressDialog progressDialog;
      String flagImg="0";
      Boolean photoExist=false;

      @Override
      public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
        //  mainActivity = (MainActivity) getActivity();

          progressDialog=new ProgressDialog(getActivity());
          progressDialog.setMessage("Please wait");
          progressDialog.setCanceledOnTouchOutside(false);
          //  getActivity().getActionBar().setTitle("Sign in");
      }

      public TabUploadDoc() {
          // Required empty public constructor
      }

      @Override
      public void onActivityCreated(@Nullable Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);


          //imgCapture=(ImageView)getActivity().findViewById(R.id.img_profile_pic);
          img1=(ImageView)getActivity().findViewById(R.id.img_1); img2=(ImageView)getActivity().findViewById(R.id.img_2);
          img3=(ImageView)getActivity().findViewById(R.id.img_3); img4=(ImageView)getActivity().findViewById(R.id.img_4);
          img5=(ImageView)getActivity().findViewById(R.id.img_5); img6=(ImageView)getActivity().findViewById(R.id.img_6);

          img1.setOnClickListener(this);img2.setOnClickListener(this);
          img3.setOnClickListener(this);img4.setOnClickListener(this);
          img5.setOnClickListener(this);img6.setOnClickListener(this);


          EnableRuntimePermissionToAccessCamera();

          getActivity().findViewById(R.id.btn_submit_photo).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  uploadFile();
              }
          });


      }

      private void captureImage() {

          try {
              file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

          Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
      /*    progressDialog.show();
          Thread splash= new Thread(){
              public void run(){
                  try{
                      Log.e("Thread","started");
                      sleep(3000);
                      progressDialog.dismiss();


                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  finally {

                  }

              }

          };
          splash.start();*/



  /*
          progressDialog.show();
       //   User user = User.getInstance();
          *//*RequestBody requestBody = RequestBody.create(
                  MediaType.parse(getActivity().getContentResolver().getType(mCameraFileUri)),
                  file);*//*
          RequestBody requestBody = RequestBody.create(
                  MediaType.parse("image/jpeg"),
                  file);
          MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
          RequestBody filename = RequestBody.create(MediaType.parse("JPEG/PNG"), file.getName());
      //    RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), user.getUserID());
          RequestBody description = RequestBody.create(MediaType.parse("text/plain"), editTextDescription.getText().toString());
          uploadPhotoInterface getResponse = APIClient.getClient().create(uploadPhotoInterface.class);
          Call<CommonParentPojo> call = getResponse.uploadFile(fileToUpload, filename, user_id,description);
          call.enqueue(new Callback<CommonParentPojo>() {
              @Override
              public void onResponse(Call<CommonParentPojo> call, retrofit2.Response<CommonParentPojo> response) {

                  CommonParentPojo commonParentPojo = response.body();
                  Log.e("ServerResponse", commonParentPojo.getMsg());
                  if (commonParentPojo != null) {
                      Log.e("response", commonParentPojo.getMsg());
                      if (commonParentPojo.getStatus().equalsIgnoreCase("1")) {
                          //  strResumePath=serverResponse.getMessage();
                          Log.e("Success Response", commonParentPojo.getMsg());
                          Toast.makeText(getActivity(),  commonParentPojo.getMsg(), Toast.LENGTH_SHORT).show();
                          editTextDescription.setText("");
                          imgPayProof.setImageResource(R.drawable.ic_pay_proof);

                      }
                  }
                  progressDialog.dismiss();
              }
              @Override
              public void onFailure(Call<CommonParentPojo> call, Throwable t) {

                  Log.e("throwbale", "" + t);
                  progressDialog.dismiss();
              }
          });*/
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
          return inflater.inflate(R.layout.tab_upload_photo, container, false);
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
              break;

              case R.id.img_2:flagImg="2";
                  break;

              case R.id.img_3:flagImg="3";
                  break;

              case R.id.img_4:flagImg="4";
                  break;

              case R.id.img_5:flagImg="5";
                  break;

              case R.id.img_6:flagImg="6";
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


          progressDialog.show();
          JSONObject jsonObject=new JSONObject();
          PhotoTabAddInterface getResponse = APIClient.getClient().create(PhotoTabAddInterface.class);
          Call<HashMap<String,String>> call=null;
          if(photoExist==true) {
              call = getResponse.updatePhoto(file.getName(),
                      "",
                      "jpg",
                      String.valueOf(mCameraFileUri),
                      "",
                      SPUserProfile.getSpInstance().getUser_id(),
                      "Y",
                      "Image 1",
                      String.valueOf(mCameraFileUri),
                      "",
                      "",case_id);
          }
          else{
          /*    call = getResponse.addPhoto(building_id,
                      spFoundationWork.getSelectedItem().toString(),
                      spPlinth.getSelectedItem().toString(),
                      spRcc.getSelectedItem().toString(),etSlabs.getText().toString(),spBrick.getSelectedItem().toString(),
                      spFlooringWork.getSelectedItem().toString(),
                      spOtherWork.getSelectedItem().toString(),
                      spIntPlaster.getSelectedItem().toString(),
                      spExtPlaster.getSelectedItem().toString(), case_id);*/
          }

          call.enqueue(new Callback<HashMap<String,String>>() {
              @Override
              public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {



                  //    progressDialog.dismiss();



              }

              @Override
              public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                  Log.e("Throwabe ", "" + t);
                  progressDialog.dismiss();
              }
          });
      }

  }