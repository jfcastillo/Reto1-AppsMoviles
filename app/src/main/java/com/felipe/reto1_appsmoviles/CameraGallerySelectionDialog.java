package com.felipe.reto1_appsmoviles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraGallerySelectionDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraGallerySelectionDialog extends DialogFragment implements View.OnClickListener{

   public final static int CAMERA_CALLBACK  = 10;
   public final static int GALLERY_CALLBACK  = 11;

   private Button galleryBtn;
   private Button cameraBtn;
   private onAddImageListener listener;

   private File filePhotoCamera;
   private String pathPhotoGallery;

    public CameraGallerySelectionDialog() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CameraGallerySelectionDialog newInstance() {
        CameraGallerySelectionDialog fragment = new CameraGallerySelectionDialog();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera_gallery_selection_dialog, container, false);
        // Inflate the layout for this fragment

        galleryBtn = root.findViewById(R.id.openGalleryBtn);
        cameraBtn = root.findViewById(R.id.openCameraBtn);
        galleryBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);



        return root;
    }

    public void setListener(onAddImageListener listener) {
        this.listener = listener;
    }

    public interface onAddImageListener{
        void onAddImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openCameraBtn:
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                filePhotoCamera = new File(getActivity().getExternalFilesDir(null)+ "/photo.png");
                Log.e(">>>",""+filePhotoCamera);
                Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), filePhotoCamera);
                openCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);


                startActivityForResult(openCamera, CAMERA_CALLBACK);

                break;

            case R.id.openGalleryBtn:
                Intent openGallery = new Intent(Intent.ACTION_GET_CONTENT);
                openGallery.setType("image/*");
                startActivityForResult(openGallery, GALLERY_CALLBACK);

                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK){
            Log.e(">>>","Hola, tome la foto creo");

        }
        else if(requestCode == GALLERY_CALLBACK && requestCode == RESULT_OK){
            Uri uri = data.getData();
            pathPhotoGallery = UtilDomi.getPath(getContext(), uri);

        }
    }
}