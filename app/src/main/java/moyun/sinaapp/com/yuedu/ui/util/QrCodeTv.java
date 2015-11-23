package moyun.sinaapp.com.yuedu.ui.util;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;

import java.io.IOException;

/**
 * Created by Moy on 十月09  009.
 */
public class QrCodeTv extends TextureView implements
        TextureView.SurfaceTextureListener {

    Camera camera;

    public QrCodeTv(Context context, Camera camera) {
        super(context);
        this.camera = camera;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
//        mCamera = Camera.open();
        if (camera != null) {
            try {
                camera.setPreviewTexture(surface);
                camera.startPreview();
            } catch (IOException ioe) {
                // Something bad happened
            }
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (camera != null) {
            // first stop preview
//            camera.stopPreview();
            // then cancel its preview callback
//            camera.setPreviewCallback(null);
            // and finally release it
            camera.release();
            // sanitize you Camera object holder
            camera = null;
        }
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
    }
}
