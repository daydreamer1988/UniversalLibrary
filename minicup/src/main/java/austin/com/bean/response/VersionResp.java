package austin.com.bean.response;

/**
 * Created by Austin on 2016/11/29.
 */
public class VersionResp {

    /**
     * versionurl : http://192.168.0.110/apk/app-debug.apk
     * versionDetial : 版本2.0
     * error : 1
     * OBDID : 192016086002
     * authentication : 完善
     * versionCode : 2.0
     * errorMsg :
     * apksize : 18.3M
     */

    private String versionurl;
    private String versionDetial;
    private int error;
    private String OBDID;
    private String authentication;
    private String versionCode;
    private String errorMsg;
    private String apksize;

    public String getVersionurl() {
        return versionurl;
    }

    public void setVersionurl(String versionurl) {
        this.versionurl = versionurl;
    }

    public String getVersionDetial() {
        return versionDetial;
    }

    public void setVersionDetial(String versionDetial) {
        this.versionDetial = versionDetial;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getOBDID() {
        return OBDID;
    }

    public void setOBDID(String OBDID) {
        this.OBDID = OBDID;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getApksize() {
        return apksize;
    }

    public void setApksize(String apksize) {
        this.apksize = apksize;
    }
}
