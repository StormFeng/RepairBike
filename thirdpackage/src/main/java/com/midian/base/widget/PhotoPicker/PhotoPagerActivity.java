package com.midian.base.widget.PhotoPicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bishilai.thirdpackage.R;
import com.midian.base.widget.PhotoPicker.fragment.ImagePagerFragment;
import java.util.List;
import static com.midian.base.widget.PhotoPicker.PhotoPicker.KEY_SELECTED_PHOTOS;
import static com.midian.base.widget.PhotoPicker.PhotoPreview.EXTRA_CURRENT_ITEM;
import static com.midian.base.widget.PhotoPicker.PhotoPreview.EXTRA_PHOTOS;
import static com.midian.base.widget.PhotoPicker.PhotoPreview.EXTRA_SHOW_DELETE;

/**
 * Created by donglua on 15/6/24.
 */
public class PhotoPagerActivity extends FragmentActivity implements View.OnClickListener {

  private ImagePagerFragment pagerFragment;

//  private ActionBar actionBar;
  private boolean showDelete;
  private TextView tvNumber;
  private ImageView ivDelete;
  private ImageView ivBack;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.__picker_activity_photo_pager);

    int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
    List<String> paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
    showDelete = getIntent().getBooleanExtra(EXTRA_SHOW_DELETE, true);

    if (pagerFragment == null) {
      pagerFragment =
          (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
    }
    pagerFragment.setPhotos(paths, currentItem);
    tvNumber = (TextView) findViewById(R.id.tv_Number);
    ivDelete = (ImageView) findViewById(R.id.iv_Delete);
    ivBack = (ImageView) findViewById(R.id.iv_Back);
    ivDelete.setOnClickListener(this);
    ivBack.setOnClickListener(this);

    if(showDelete){
      ivDelete.setVisibility(View.VISIBLE);
    }else{
      ivDelete.setVisibility(View.GONE);
    }

    pagerFragment.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateActionBarTitle();
      }
    });
  }


  @Override public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
    setResult(RESULT_OK, intent);
    finish();
    overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
    super.onBackPressed();
  }

  public void updateActionBarTitle() {

    tvNumber.setText( getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
            pagerFragment.getPaths().size()));
  }

  @Override
  public void onClick(View v) {
    int id=v.getId();
    if(id==R.id.iv_Back){
      Intent intent = new Intent();
      intent.putExtra(KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
      setResult(RESULT_OK, intent);
      finish();
      overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
    }else if(id==R.id.iv_Delete){
      final int index = pagerFragment.getCurrentItem();
      Toast toast = Toast.makeText(PhotoPagerActivity.this, R.string.__picker_deleted_a_photo, Toast.LENGTH_SHORT);

      if (pagerFragment.getPaths().size() <= 1) {

        // show confirm dialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.__picker_confirm_to_delete)
                .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    pagerFragment.getPaths().remove(index);
                    pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
                    onBackPressed();
                  }
                })
                .setNegativeButton(R.string.__picker_cancel, new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                  }
                })
                .show();

      } else {

        toast.show();

        pagerFragment.getPaths().remove(index);
        pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
      }
    }
  }
}
