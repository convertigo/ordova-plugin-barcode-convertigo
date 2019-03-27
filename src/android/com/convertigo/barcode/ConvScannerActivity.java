package com.convertigo.barcode;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class ConvScannerActivity extends FragmentActivity {

    private ViewPager 	 _viewPager;
    private ConvScannerPagerAdapter _pagerAdapter;
    public BarecodeOptions barecodeOpts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent(); // gets the previously created intent
        this.barecodeOpts = (BarecodeOptions)i.getSerializableExtra("options");

        super.onCreate(savedInstanceState);
		int scannerActivityLayoutId = getApplication().getResources().getIdentifier("scanner_activity","layout",getApplication().getPackageName());
        setContentView(scannerActivityLayoutId);
        String mode = "both";
        if(this.barecodeOpts.getOnlyKeyboard()){
            mode = "onlyKeyboard";
        }
        else if(this.barecodeOpts.getOnlyScan()){
            mode = "onlyScan";
        }

        _pagerAdapter = new ConvScannerPagerAdapter(getSupportFragmentManager(),mode);
        
		int viewPagerId = getApplication().getResources().getIdentifier("pager","id",getApplication().getPackageName());
        _viewPager =  findViewById(viewPagerId);
        _viewPager.setAdapter(_pagerAdapter);

        if(this.barecodeOpts.getOnlyKeyboard()){
            _viewPager.setCurrentItem(1);
        }
        else if(this.barecodeOpts.getOnlyScan()){
            _viewPager.setCurrentItem(0);
        }
        _viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				
				if(position == 0)
				{
					_pagerAdapter.getCameraPreviewFragment().resumePreview();
				}
				else if(position == 1)
				{
					_pagerAdapter.getManualInputFragment().onFragmentShown();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {				
				if(state != ViewPager.SCROLL_STATE_IDLE)
				{
					_pagerAdapter.getCameraPreviewFragment().pausePreview();
					_pagerAdapter.getManualInputFragment().onFragmentHidden();
				}
				else if(state == ViewPager.SCROLL_STATE_IDLE)
				{
					if(_viewPager.getCurrentItem() == 0) _pagerAdapter.getCameraPreviewFragment().resumePreview();
					if(_viewPager.getCurrentItem() == 1) _pagerAdapter.getManualInputFragment().onFragmentShown();
				}
			}
		});
    }

    public void switchToScanner(View view)
    {
    	_viewPager.setCurrentItem(0, true);
    }
    
    public void switchToManualInput(View view)
    {
    	_viewPager.setCurrentItem(1, true);
    }

    @Override
    public void onResume()
    {
    	super.onResume();
        ForcedKeyboardLinearLayout.setActivity(this);
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
        ForcedKeyboardLinearLayout.setActivity(null);
    }

    @Override
    public void onBackPressed() {
    	if(_viewPager != null)
    	{
    		if(_viewPager.getCurrentItem() == 1)
    		{
				_pagerAdapter.getManualInputFragment().onFragmentHidden();
    		}
    	}
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void finish(String value, String type) {
        Intent dataIntent = new Intent();
        dataIntent.putExtra("SCAN_RESULT", value);
        dataIntent.putExtra("SCAN_RESULT_TYPE", type);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }
    private class ConvScannerPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment _cameraPreviewFragment;
        private Fragment _manualInputFragment;
        private String mode;

        public ConvScannerPagerAdapter(android.support.v4.app.FragmentManager fm, String mode) {
            super(fm);
            this.mode = mode;
            switch (mode){
                case "onlyScan":
                    _cameraPreviewFragment = new CameraPreviewFragment();
                    break;
                case "onlyKeyboard":
                    _manualInputFragment = new ManualInputFragment();
                    break;
                case "both":
                    _cameraPreviewFragment = new CameraPreviewFragment();
                    _manualInputFragment = new ManualInputFragment();
                    break;
            }
        }

        @Override
        public Fragment getItem(int position) {
            if(mode.equals("onlyScan")){
                return _cameraPreviewFragment;
            }
            else if(mode.equals("onlyKeyboard")){
                return _manualInputFragment;
            }
            else{
                if(position == 0) return _cameraPreviewFragment;
                else return  _manualInputFragment;
            }

        }

        @Override
        public int getCount() {
            int val = 0;
            switch (mode){
                case "onlyScan":
                    val = 1;
                    break;
                case "onlyKeyboard":
                    val = 1;
                    break;
                case "both":
                    val = 2;
                    break;
            }
            return val;
        }

        public CameraPreviewFragment getCameraPreviewFragment() { return (CameraPreviewFragment) _cameraPreviewFragment; }
        public ManualInputFragment getManualInputFragment() { return (ManualInputFragment) _manualInputFragment; }
    }

}