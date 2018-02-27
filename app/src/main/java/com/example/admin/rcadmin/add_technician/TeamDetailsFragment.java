package com.example.admin.rcadmin.add_technician;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamDetailsFragment extends Fragment {

    private static final String TECHNICIAN = "technicial";
   // private static final String TECHNICIAN_LIST = "technician_list";

    private Technician technician;
    //private TechnicianList technicianList;

    private Button generateQrBtn;
    private ImageView resultQrImg, resultQrBackImg;

    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    RelativeLayout qrRelativeLayout, qrIdBackLayout;
    private SweetAlertDialog sweetAlertDialog;
    private PrefManager prefManager;

    private TextView technicianName,technicianMobile,technicianAddress,technicianGender,
            cardTechnicianName,cardTechnicianMobile,cardTechnicianGender,cardTechnicianAddress;

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

        generateQRClickListener();

        checkQRExistsOrNot();


        return view;
    }

    private void initializations(View view)
    {
        resultQrImg= (ImageView)view.findViewById(R.id.qr_img);
        resultQrBackImg = (ImageView)view.findViewById(R.id.qr_back_img);
        generateQrBtn = (Button)view.findViewById(R.id.btn_scan_id);
        qrRelativeLayout = (RelativeLayout) view.findViewById(R.id.scan_id_layout);
        qrIdBackLayout = (RelativeLayout)view.findViewById(R.id.scan_id_backlayout);
        technicianName=(TextView)view.findViewById(R.id.technician_name);
        technicianMobile=(TextView)view.findViewById(R.id.technician_mobile);
        technicianAddress=(TextView)view.findViewById(R.id.technician_address);
        technicianGender=(TextView)view.findViewById(R.id.technician_gender);
        cardTechnicianName=(TextView)view.findViewById(R.id.card_technician_name);
        cardTechnicianMobile=(TextView)view.findViewById(R.id.card_technician_mobile);
        cardTechnicianAddress=(TextView)view.findViewById(R.id.card_technician_address);
        cardTechnicianGender=(TextView)view.findViewById(R.id.card_technician_gender);
    }



    private void generateQRClickListener()
    {
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait");;
                sweetAlertDialog.show();

                generateQrImg();
            }
        });

    }

    private void generateQrImg()
    {
        try {
            bitmap = TextToImageEncode(getTeamDataInString());

            resultQrImg.setImageBitmap(bitmap);
            resultQrBackImg.setImageBitmap(bitmap);
            qrRelativeLayout.setVisibility(View.VISIBLE);
            qrIdBackLayout.setVisibility(View.VISIBLE);
            sweetAlertDialog.dismissWithAnimation();

            FileHelper.savePNGImage(Folders.TECHNICANQRCODE,bitmap,"TECH_QR_CODE"+technician.getTech_id());

          /*if(qrRelativeLayout.getVisibility()==View.VISIBLE) {
                generateFrontBackViewID(qrRelativeLayout, "TECH_ID_FRONT_VIEW" + technician.getTech_id());
            }
            if(qrIdBackLayout.getVisibility()==View.VISIBLE) {
                generateFrontBackViewID(qrIdBackLayout, "TECH_ID_BACK_VIEW" + technician.getTech_id());
            }*/
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

    private void generateFrontBackViewID(RelativeLayout relativeLayout, String nameFrontBack)
    {
        //relativeLayout.setDrawingCacheEnabled(true);
        //Bitmap bitmap = relativeLayout.getDrawingCache();


        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        relativeLayout.draw(canvas);
        FileHelper.savePNGImage(Folders.TECHNICANQRCODE,bitmap,nameFrontBack);

    }


    private void checkQRExistsOrNot()
    {
        File image =   FileHelper.createfile(Folders.TECHNICANQRCODE,"TECH_QR_"+technician.getTech_id(), FileType.PNG);

        if(image!=null) {
            if (image.exists()) {
                qrRelativeLayout.setVisibility(View.VISIBLE);
                qrIdBackLayout.setVisibility(View.VISIBLE);
                generateQrBtn.setVisibility(View.GONE);
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
                resultQrImg.setImageBitmap(myBitmap);
                resultQrBackImg.setImageBitmap(myBitmap);

            } else {
                qrRelativeLayout.setVisibility(View.GONE);
                qrIdBackLayout.setVisibility(View.GONE);
                generateQrBtn.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            qrRelativeLayout.setVisibility(View.GONE);
            qrIdBackLayout.setVisibility(View.GONE);
            generateQrBtn.setVisibility(View.VISIBLE);
        }
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
        technicianName.setText(technician.getTechName());
        technicianAddress.setText(technician.getAddress());
        technicianMobile.setText(technician.getMobile());
        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            if (technician.getGender().equalsIgnoreCase("M")) {
                technicianGender.setText("लिंग : पुरूष");

            } else {
                technicianGender.setText("लिंग : महिला");

            }

            if (technician.getGender().equalsIgnoreCase("M")) {
                cardTechnicianGender.setText("लिंग : पुरूष");

            } else {
                cardTechnicianGender.setText("लिंग : महिला");

            }
            cardTechnicianName.setText("नाव: "+technician.getTechName());
            cardTechnicianMobile.setText("मोबाईल: "+technician.getMobile());
            cardTechnicianAddress.setText("पत्ता: "+technician.getAddress());

        }
        else
        {
            if (technician.getGender().equalsIgnoreCase("M")) {
                technicianGender.setText("Gender: Male");

            } else {
                technicianGender.setText("Gender: Female");

            }


            if(technician.getGender().equalsIgnoreCase("M")) {
                cardTechnicianGender.setText("Gender: Male");

            }
            else
            {
                cardTechnicianGender.setText("Gender: Female");

            }

            cardTechnicianName.setText("Name: "+technician.getTechName());
            cardTechnicianMobile.setText("Mobile: "+technician.getMobile());
            cardTechnicianAddress.setText("Address: "+technician.getAddress());

        }


    }

}
