package com.example.kk.myconstranitlayouttest;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by kk on 2017/5/16.
 */

public class CameraLiveWallpaper extends WallpaperService {
    // 实现WallpaperService必须实现的抽象方法
    @Override
    public Engine onCreateEngine() {
        // 返回自定义的CameraEngine
        return new CameraEngine();
    }

    private class CameraEngine extends Engine implements Camera.PreviewCallback {
        private Camera camera;
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            // 时间处理:点击拍照,长按拍照
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }
        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                startPreview();
            } else {
                stopPreview();
            }
        }
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            startPreview();
            // 设置处理触摸事件
            setTouchEventsEnabled(true);

        }

        /**
         * 停止预览
         */
        private void stopPreview() {
            if (camera != null) {
                try {
                    camera.stopPreview();
                    camera.setPreviewCallback(null);
                    // camera.lock();
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera = null;
            }
        }

        /**
         * 开始预览
         */
        private void startPreview() {
            camera = Camera.open();
            camera.setDisplayOrientation(90);

            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }

        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {

        }
    }


}
