package net.devatom.mycamapp;

import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

	private Button btCapture;
	private ImageView imgCapture;
	private static final int CAM_REQUEST_TAKE_PHOTO = 1;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btCapture = (Button) findViewById(R.id.btCapture);
		imgCapture = (ImageView) findViewById(R.id.imgCapture);
		ctx = this;

		btCapture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = new File(getExternalFilesDir(null), "img.jpg");

				Context context = ctx;
				String auth = context.getApplicationContext().getPackageName();
				Uri uriPhoto = FileProvider.getUriForFile(
						context,
						auth + ".provider",
						file
				);

				it.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
				startActivityForResult(it, CAM_REQUEST_TAKE_PHOTO);
			}
		});
	}

	private File getFile(){
		File dir = new File(getExternalFilesDir(null), "images");

		if (!dir.exists()){
			dir.mkdir();
		}

		File img = new File(dir, "img_capture.jpg");
		return img;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == RESULT_OK && requestCode == CAM_REQUEST_TAKE_PHOTO) {
				File file = new File(getExternalFilesDir(null), "img.jpg");
				String fullImagePath = file.getPath();
				imgCapture.setImageDrawable(Drawable.createFromPath(fullImagePath));
			}
		}catch (Exception ex){
			Toast.makeText(this, "Pas de shoot", Toast.LENGTH_SHORT).show();
		}
	}
}
