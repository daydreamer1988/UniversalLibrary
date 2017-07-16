package austin.com.utils;
import android.app.ProgressDialog;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadManager {

	public static File getFileFromServer(String path, ProgressDialog pd, String targetPath) throws Exception{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL url = new URL(path);
			HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			pd.setMax(conn.getContentLength()/1024/1024);//M
			InputStream is = conn.getInputStream();
			File file1 = new File(targetPath);
			if (!file1.exists()) {
				file1.mkdirs();
			}

			file1.exists();

			File file = new File(file1,"update.apk");
			file.exists();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len ;
			int total=0;
			while((len =bis.read(buffer))!=-1){
				fos.write(buffer, 0, len);
				total+= len;
				pd.setProgress(total/1024/1024);//M
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		}
		else{
			return null;
		}
	}
}