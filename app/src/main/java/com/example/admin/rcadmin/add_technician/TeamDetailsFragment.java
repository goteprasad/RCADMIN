package com.example.admin.rcadmin.add_technician;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.add_technician.model.Technician;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.file.FileHelper;
import com.example.admin.rcadmin.file.FileType;
import com.example.admin.rcadmin.file.Folders;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamDetailsFragment extends Fragment {

    private static final String TECHNICIAN = "technicial";
   // private static final String TECHNICIAN_LIST = "technician_list";

    private Technician technician;
    //private TechnicianList technicianList;

    private ImageView resultQrImg;
    private boolean isQRCreated = false;
    private SweetAlertDialog sweetAlertDialog;

    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    LinearLayout qrIdFrontLayout, qrIdBackLayout;
    private PrefManager prefManager;
    private TextView newBackCardTechnicianName,
            newCardFrontTechnicianName,newCardTechnicianMobile,newCardTechnicianId,newBackCardTechnicianPost,
            newCardFrontTechnicianPost,newBackCardJoiningDate,newBackCardExpiryDate,newBackCardTechnicianId,
            newBackCardAddress,btnDownload;



    public TeamDetailsFragment() {
        // Required empty public constructor
    }

    public static TeamDetailsFragment newInstance(Technician technician) {
        TeamDetailsFragment fragment = new TeamDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(TECHNICIAN, technician);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            technician = getArguments().getParcelable(TECHNICIAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view;
        // Inflate the layout for this fragment
        prefManager=new PrefManager(getActivity());
        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            view = inflater.inflate(R.layout.fragment_team_details_marathi, container, false);
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_team_details_english, container, false);


        }

        initializations(view);
        setTechnicianData();
        generateQrImg();
        checkQRGeneratedOrNot();
        downloadIdCardClickListener();


        return view;
    }

    private void initializations(View view)
    {
        resultQrImg= (ImageView)view.findViewById(R.id.qr_img);
        qrIdFrontLayout = (LinearLayout) view.findViewById(R.id.scan_id_layout_new);
        qrIdBackLayout = (LinearLayout)view.findViewById(R.id.scan_id_back_layout_new);
        newCardFrontTechnicianName=(TextView)view.findViewById(R.id.new_card_front_technician_name);
        newCardTechnicianMobile=(TextView)view.findViewById(R.id.new_card_technician_mobile);
        newCardFrontTechnicianPost=(TextView)view.findViewById(R.id.new_card_front_technician_post);
        newCardTechnicianId=(TextView)view.findViewById(R.id.new_card_technician_id);
        newBackCardTechnicianName=(TextView)view.findViewById(R.id.new_back_card_technician_name);
        newBackCardTechnicianPost=(TextView)view.findViewById(R.id.new_back_card_technician_post);
        newBackCardJoiningDate=(TextView)view.findViewById(R.id.new_back_card_joining_date);
        newBackCardExpiryDate=(TextView)view.findViewById(R.id.new_back_card_expiry_date);
        newBackCardTechnicianId=(TextView)view.findViewById(R.id.new_back_card_technician_id);
        newBackCardAddress=(TextView)view.findViewById(R.id.new_back_card_address);
        btnDownload=(Button)view.findViewById(R.id.btn_download);
    }

    private void generateQrImg()
    {
        try {
            bitmap = TextToImageEncode(getTeamDataInString());

            resultQrImg.setImageBitmap(bitmap);
            isQRCreated = true;

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private String getTeamDataInString()
    {

        // generate json object string of data
        return  "{\"id\":\""+ technician.getTech_id()+"\",\"name\":\""+technician.getTechName()+
                "\",\"mobile\":\""+technician.getMobile()+"\",\"address\":\""+ technician.getAddress()+
                "\",\"gender\":\""+technician.getGender()+"\"}";

    }

    Bitmap TextToImageEncode(String Value) throws WriterException
    {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorBlack):getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private  void setTechnicianData()
    {

        newBackCardTechnicianName.setText(technician.getTechName());
        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {

            newCardFrontTechnicianName.setText("नाव: "+technician.getTechName());
            newCardTechnicianMobile.setText("मोबाईल: "+technician.getMobile());
            newCardFrontTechnicianPost.setText("पद: कारागीर ");
            newBackCardTechnicianPost.setText("पद: कारागीर ");
            newCardTechnicianId.setText("आयडी : "+technician.getTech_id());
            newBackCardTechnicianId.setText("आयडी : "+technician.getTech_id());
            newBackCardJoiningDate.setText("रुजू झालेली तारीख: १२ डिसेंबर २०१७");
            newBackCardExpiryDate.setText("कालबाह्य तारीख: १२ डिसेंबर २०४२");
            newBackCardAddress.setText("पत्ता: "+technician.getAddress());



        }
        else
        {
            newCardFrontTechnicianName.setText("Name: "+technician.getTechName());
            newCardTechnicianMobile.setText("Mobile: "+technician.getMobile());
            newCardFrontTechnicianPost.setText("Designation: Support Technician");
            newCardTechnicianId.setText("Technician Id : "+technician.getTech_id());
            newBackCardTechnicianId.setText("Technician Id : "+technician.getTech_id());
            newBackCardJoiningDate.setText("Joining Date: 12 Dec 2017");
            newBackCardExpiryDate.setText("Expiry Date: 12 Dec 2042");
            newBackCardAddress.setText("Address: "+technician.getAddress());



        }


    }

    private void checkQRGeneratedOrNot()
    {
       if(!isQRCreated)
       {
           btnDownload.setClickable(false);
           generateQrImg();
       }
       else
       {
           btnDownload.setClickable(true);
       }
    }

    private void downloadIdCardClickListener()
    {
        final File frontImg = FileHelper.createfile(Folders.TECHNICIAN_IDS,"TECHNICIAN_ID_CARD_FRONT_" + technician.getTech_id(),FileType.PNG);
        final File backImg = FileHelper.createfile(Folders.TECHNICIAN_IDS,"TECHNICIAN_ID_CARD_BACK_" + technician.getTech_id(),FileType.PNG);


            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait");;
                    sweetAlertDialog.show();

                    if(frontImg!=null && backImg!=null)
                    {
                        if(!frontImg.exists() && !backImg.exists())
                        {
                            checkQRGeneratedOrNot();
                            saveBitMap("TECHNICIAN_ID_CARD_FRONT_" + technician.getTech_id(), qrIdFrontLayout);
                            saveBitMap("TECHNICIAN_ID_CARD_BACK_" + technician.getTech_id(), qrIdBackLayout);

                            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Images Downloaded");
                            sweetAlertDialog.setConfirmText("Ok");

                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();

                                }
                            });

                        }
                        else
                        {
                            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Already Downloaded");
                            sweetAlertDialog.setConfirmText("Ok");

                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();

                                }
                            });

                        }
                    }
                    else
                    {
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Already Downloaded");
                        sweetAlertDialog.setConfirmText("Ok");

                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismissWithAnimation();

                            }
                        });

                    }


                }
            });


    }



    private File saveBitMap(String imagename , View drawView)
    {
        File pictureFile = FileHelper.savePNGImage(Folders.TECHNICIAN_IDS,getBitmapFromView(drawView),imagename);
        //File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
           // pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }

        return pictureFile;
    }
    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }





}
