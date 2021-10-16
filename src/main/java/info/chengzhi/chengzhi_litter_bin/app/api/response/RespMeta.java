package info.chengzhi.chengzhi_litter_bin.app.api.response;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.util.Map;

public class RespMeta {
  private int code = -1;
  private String msg;
  private String errorType;
  private String errorMsg;
  private Map<String, Object> errorData;
  private Map<String, Object> showDesc;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public Map<String, Object> getErrorData() {
    if (errorData == null) {
      return Maps.newHashMap();
    }
    return errorData;
  }

  public void setErrorData(Map<String, Object> errorData) {
    this.errorData = errorData;
  }

  public Map<String, Object> getShowDesc() {
    return showDesc;
  }

  public void setShowDesc(Map<String, Object> showDesc) {
    this.showDesc = showDesc;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
