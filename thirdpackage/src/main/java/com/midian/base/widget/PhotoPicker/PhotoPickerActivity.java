package com.midian.base.widget.PhotoPicker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bishilai.thirdpackage.R;
import com.midian.base.widget.PhotoPicker.entity.Photo;
import com.midian.base.widget.PhotoPicker.event.OnItemCheckListener;
import com.midian.base.widget.PhotoPicker.fragment.ImagePagerFragment;
import com.midian.base.widget.PhotoPicker.fragment.PhotoPickerFragment;
import java.util.ArrayList;
import java.util.List;
import static android.widget.Toast.LENGTH_LONG;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.DEFAULT_MAX_COUNT;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_MAX_COUNT;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.EXTRA_SHOW_GIF;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.KEY_SELECTED_PHOTOS;

public class PhotoPickerActivity extends FragmentActivity implements View.OnClickListener {

  private PhotoPickerFragment pickerFragment;
  private ImagePagerFragment imagePagerFragment;
//  private MenuItem menuDoneItem;

  private int maxCount = DEFAULT_MAX_COUNT;

  /** to prevent multiple calls to inflate menu */
  private boolean menuIsInflated = false;

  private boolean showGif = false;
  private int columnNumber = DEFAULT_COLUMN_NUMBER;
  private ArrayList<String> originalPhotos = null;

  private TextView tvBack;
  private Button btnOk;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    boolean showCamera      = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
    boolean showGif         = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
    boolean previewEnabled  = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, true);

    setShowGif(showGif);

    setContentView(R.layout.__picker_activity_photo_picker);
    tvBack = (TextView) findViewById(R.id.tv_Back);
    btnOk = (Button) findViewById(R.id.btn_Ok);
    tvBack.setOnClickListener(this);
    btnOk.setOnClickListener(this);
    if (originalPhotos != null && originalPhotos.size() > 0) {
      btnOk.setClickable(true);
      btnOk.setText(
              getString(R.string.__picker_done_with_count, originalPhotos.size(), maxCount));
    } else {
      btnOk.setClickable(false);
    }
    maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
    columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
    originalPhotos = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_PHOTOS);

    pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
    if (pickerFragment == null) {
      pickerFragment = PhotoPickerFragment
          .newInstance(showCamera, showGif, previewEnabled, columnNumber, maxCount, originalPhotos);
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, pickerFragment, "tag")
          .commit();
      getSupportFragmentManager().executePendingTransactions();
    }

    pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
      @Override public boolean OnItemCheck(int position, Photo photo, final boolean isCheck, int selectedItemCount) {

        int total = selectedItemCount + (isCheck ? -1 : 1);
        btnOk.setClickable(total>0);

        if (maxCount <= 1) {
          List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
          if (!photos.contains(photo.getPath())) {
            photos.clear();
            pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
          }
          return true;
        }

        if (total > maxCount) {
          Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
              LENGTH_LONG).show();
          return false;
        }
        btnOk.setText(getString(R.string.__picker_done_with_count, total, maxCount));
        return true;
      }
    });

  }

  /**
   * Overriding this method allows us to run our exit animation first, then exiting
   * the activity when it complete.
   */
  @Override public void onBackPressed() {
    if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
      imagePagerFragment.runExitAnimation(new Runnable() {
        public void run() {
          if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
          }
        }
      });
    } else {
      super.onBackPressed();
    }
  }


  public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
    this.imagePagerFragment = imagePagerFragment;
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, this.imagePagerFragment)
        .addToBackStack(null)
        .commit();
  }

  public PhotoPickerActivity getActivity() {
    return this;
  }

  public boolean isShowGif() {
    return showGif;
  }

  public void setShowGif(boolean showGif) {
    this.showGif = showGif;
  }

  @Override
  public void onClick(View v) {
    int id=v.getId();
    if(R.id.tv_Back==id){
      finish();
    }else if(R.id.btn_Ok==id){
      Intent intent = new Intent();
      ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
      intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
      setResult(RESULT_OK, intent);
      finish();
    }
  }
}
